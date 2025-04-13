package com.blogwebsite.user.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.blogwebsite.user.authConfig.JwtService;
import com.blogwebsite.user.domain.LoginRequest;
import com.blogwebsite.user.domain.LoginResponse;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogwebsite.user.FeignClient.BlogClient;
import com.blogwebsite.user.FeignClient.CategoryClient;
import com.blogwebsite.user.domain.UserEntity;
import com.blogwebsite.user.proxy.BlogProxy;
import com.blogwebsite.user.proxy.CommentProxy;
import com.blogwebsite.user.proxy.UserProxy;
import com.blogwebsite.user.repository.UserRepo;
import com.blogwebsite.user.service.UserService;
import com.blogwebsite.user.utils.Helper;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private Helper helper;
	
	@Autowired
	private CategoryClient categoryClient;
	
	@Autowired
	private BlogClient blogClient;

	@Autowired
	private AuthenticationManager authmanager;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private JavaMailSender mailSender;

//	@Autowired
//	private EmailService emailService;


	//private final String blogUrl="http://localhost:8088/blog/";

	@Value("${file.upload-dir}")
	private String uploadDir;




	//generate and send otp methods

	@Override
	public String generateAndSendOtp(String email) {

		UserEntity userEntity= userRepo.findByEmail(email);
		System.out.println("email from user entity to send otp : "+email+"========================================");
		if(userEntity != null){
			String otp = String.format("%06d", new Random().nextInt(999999));
			System.out.println("Otp is : "+otp);

			UserEntity user=userRepo.findByEmail(email);
			System.out.println("USer form email is : "+user);

			user.setOtp(otp);
			user.setOtpRequestedTime(LocalDateTime.now());
			userRepo.save(user);
			System.out.println("USer after save : "+user);

			sendOtpEmail(email,otp);
			return  otp;
		}else{
			throw new RuntimeException("User not found with email: " + email);
		}
	}

	private void sendOtpEmail(String email,String otp){
		try{
			MimeMessage mimeMailMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper= new MimeMessageHelper(mimeMailMessage,"utf-8");

			String subject= "Password Reset OTP";
			String content= "Dear User,<br><br>Your OTP for password reset is: <b>" + otp + "</b><br><br>Regards,<br>Radhika Yadav";

			helper.setText(content,true);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setFrom("quillist001@gmail.com");
			mailSender.send(mimeMailMessage);


        }catch (Exception ex){
			throw new RuntimeException("Failed to send OTP email", ex);

		}
	}


	//varify OTP

	public boolean verifyOtp(String email, String otp) {
		UserEntity user = userRepo.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("User not found");
		}

		if (user.getOtp() == null) {
			throw new RuntimeException("OTP not generated yet");
		}

		if (user.getOtp().equals(otp)) {
			// Optional: You can check for expiry here if you want
			user.setOtp(null);
			user.setOtpRequestedTime(null);
			userRepo.save(user);
			return true;
		} else {
			return false;
		}
	}

	//chagne password

	public void changePassword(String email, String newPassword) {
		UserEntity user = userRepo.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("User not found");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		// Clear OTP after password reset
		user.setOtp(null);
		user.setOtpRequestedTime(null);
		userRepo.save(user);
	}
























	public String saveImage(MultipartFile file) throws IOException {

		Path uploadpath= Paths.get(uploadDir);
		if(!Files.exists(uploadpath)){
			Files.createDirectories(uploadpath);
		}
		String fileName=file.getOriginalFilename();
        assert fileName != null;
        Path filepath=uploadpath.resolve(fileName);
		System.err.println("FIle path is : "+filepath);
		Files.copy(file.getInputStream(),filepath, StandardCopyOption.REPLACE_EXISTING);
		return  filepath.toString();
	}


	public ResponseEntity<Resource> getImage(String filename) throws IOException {

		Path filePath = Paths.get(uploadDir).resolve(filename);
		Resource resource = new UrlResource(filePath.toUri());
		if (resource.exists()) {
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}

	}



	
	@Override
	public String registerUser(UserProxy user) {
		UserEntity existingUser = userRepo.findByEmail(user.getEmail());  // Check email instead of username

		if(existingUser !=null && existingUser.getEmail().equals(user.getEmail())){
			return  "User with email already exists ....";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(helper.convert(user, UserEntity.class));
		return "register successfully";
	}

	public String registerUserwithImage(UserProxy user, MultipartFile file) throws IOException {
		// Save the image and get its path
		String imagePath = saveImage(file);

		// Convert UserProxy to UserEntity
		UserEntity userEntity = helper.convert(user, UserEntity.class);

		// Store image as a byte array (if storing in DB)

		//enable this after adding photos
//		userEntity.setProfilePhoto(Files.readAllBytes(Paths.get(imagePath)));

		// Save user to database
		userRepo.save(userEntity);

		return "User registered successfully with profile photo";
	}



	@Override
	public String deleteUser(Integer id) {
			userRepo.deleteById(id);
		return "user deleted successfully";
	}

	@Override
	public String createBlog(BlogProxy blog,Integer id) {
		blogClient.createBlog(blog, id);
		return "saved";
	}

	@Override
	public String deleteBlog(Integer id) {
		blogClient.deleteBlog(id);
		return null;
	}

	@Override
	public String updateBlog(BlogProxy blog,Integer id) {
		blogClient.updateBlog(blog, id);
		return "updated...";
	}

	@Override
	public List<UserProxy> getAllUser() {
		return helper.convertList(userRepo.findAll(), UserProxy.class);
	}

	@Override
	public UserProxy getUserByUserName(String userName) {
		return helper.convert(userRepo.findByUserName(userName), UserProxy.class);
	}

	@Override //search blog by user
	public List<BlogProxy> searchBlogByTitle(String title) {
		
		return blogClient.searchBlogByTitle(title);
	}

	@Override
	public UserProxy updateUserById(Integer id, UserProxy user) {
		
		Optional<UserEntity> byId = userRepo.findById(id);
		
		if(byId.isPresent())
		{
			UserEntity userEntity = byId.get();
			userEntity.setUserName(user.getUserName());
			userEntity.setEmail(user.getEmail());
			userEntity.setPassword(user.getPassword());
			userRepo.save(userEntity);
		}
		return user;
	}

	@Override
	public UserProxy getUserByUserId(Integer id) //working
	{
		return helper.convert(userRepo.findById(id), UserProxy.class);
	}

	@Override
	public List<BlogProxy> getAllBlogs() //working
	{
		return 	blogClient.getAllBlogs();
	}

	@Override
	public List<BlogProxy> searchBlogByCategory(String category) //working
	{
		return categoryClient.searchBlogByCategory(category);
	}

	@Override
	public String addComment(Integer id, CommentProxy commentProxy) {
		blogClient.addComment(id, commentProxy);
		return "comment added successfully";
	}

	@Override
	public List<BlogProxy> searchByBlogTitleAndCategoryName(BlogProxy blogProxy) {
		return blogClient.searchByBlogTitleAndCategoryName(blogProxy);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		System.out.println("login response from emp service called..");
//		Authentication authentication=new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
		Authentication authentication=new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
		System.err.println("Authentication : "+authentication);

		Authentication verified = authmanager	.authenticate(authentication);

		System.err.println("is varified : "+verified);
		System.err.println(verified.isAuthenticated());
		if(!verified.isAuthenticated())
		{
			//System.out.println("user not found");
			//System.err.println("user not found");
			//throw new BadCredicialException(null, null);
			//throw new BadCredentialsException("Bad credentials...");
			System.out.println("bad credials..");
			//throw new ErrorResponse("bad credentials",404);
		}
		System.out.println("generated token is : "+jwtService.genearteTocken(loginRequest.getEmail()));

		return new LoginResponse(loginRequest.getEmail(),jwtService.genearteTocken(loginRequest.getEmail()),(List<SimpleGrantedAuthority>) verified.getAuthorities());

	}

	@Override
	public UserProxy getUserByEmail(String email) {
		UserEntity userbyEmail = userRepo.findByEmail(email);
		UserProxy user=helper.convert(userbyEmail,UserProxy.class);
		System.out.println("Email in getuserby email id method : "+email);
		return user;
	}

	@Override
	public Integer getUserIdByEmail(String email) {
		UserEntity user =  userRepo.findByEmail(email);
		System.out.println("User is : "+user);
		System.err.println("User id is :"+user.getId());
		if(user!=null){
			return user.getId();

		}
		else{
			return null;
		}
	}



	@Override
	public String generateTocken(UserEntity user) {
		UserEntity findByUserName = userRepo.findByUserName(user.getUserName());
		UserEntity findbyemail = userRepo.findByEmail(user.getEmail());

//		System.out.println(findByUserName);
		System.out.println("user email is : "+findbyemail);
		System.out.println("user email is : "+findbyemail.getPassword());
//		System.out.println(findByUserName.getPassword());
		//System.out.println(emp.getPassword());
		boolean matches = passwordEncoder.matches(user.getPassword(),findbyemail.getPassword());

		System.out.println("Matches Password:"+matches);
		if(!matches)
		{
			return "user not found";
		}
//		return jwtService.genearteTocken(user.getUserName());
		return jwtService.genearteTocken(user.getEmail());
	}


}
