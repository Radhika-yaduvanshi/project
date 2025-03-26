package com.blogwebsite.user.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
	
	//private final String blogUrl="http://localhost:8088/blog/";


	//commenting for trying another
	@Override
	public String registerUser(UserProxy user) {
		userRepo.save(helper.convert(user, UserEntity.class));
		return "register successfully";
	}

	public String registerUserWithProfile(String name, String email, String password, MultipartFile profilePhoto) {
		// Here, create a new UserEntity and save the data, along with the profile photo
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(name);
		userEntity.setEmail(email);
		userEntity.setPassword(password);

		// Handle file upload (save profilePhoto if available)
		if (profilePhoto != null && !profilePhoto.isEmpty()) {
			try {
				// Instead of saving the path, you now save the image as a byte array
				byte[] imageBytes = uploadProfileImage(profilePhoto); // Now returns byte[]
				userEntity.setProfilePhoto(imageBytes); // Assuming profilePhoto is byte[] in UserEntity
			} catch (IOException e) {
				e.printStackTrace();
				return "Error uploading profile photo.";
			}
		}

		// Save the user to the database
		userRepo.save(userEntity);
		return "User registered successfully!";
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
//		return helper.convertList(userRepo.findAll(), UserProxy.class);
		List<UserEntity> users = userRepo.findAll();

		return users.stream().map(userEntity -> {
			UserProxy userProxy = new UserProxy();
			userProxy.setId(userEntity.getId());
			userProxy.setUserName(userEntity.getUserName());
			userProxy.setPassword(userEntity.getPassword());
			userProxy.setEmail(userEntity.getEmail());
			userProxy.setCreatedAt(userEntity.getCreatedAt());
			userProxy.setUpdateAt(userEntity.getUpdateAt());
//			userProxy.setProfilePhoto(Arrays.toString(userEntity.getProfilePhoto()));

//			 Convert profile photo to Base64 string if it exists
			if (userEntity.getProfilePhoto() != null) {
				String base64Image = Base64.getEncoder().encodeToString(userEntity.getProfilePhoto());
				userProxy.setProfilePhoto("data:image/jpeg;base64," + base64Image);  // Assuming JPEG image type
			}

			return userProxy;
		}).collect(Collectors.toList());

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
	public String uploadProfileImage(Integer id, MultipartFile image) {
		String originalFileName=null;
		UserEntity userprofile=null;
		try {
			Optional<UserEntity> optionalUser = userRepo.findById(id);
			System.err.println(optionalUser);
			String urlpath=new ClassPathResource("").getFile().getAbsolutePath()+File.separator+"static"+File.separator+"documents";
			System.err.println("This is uerl path====>"+urlpath);
			if (optionalUser.isPresent()) {
				UserEntity userEntity = optionalUser.get();
				// Create the directory if it doesn't exist
				File directory = new File(urlpath);

				if (!directory.exists()) {
					directory.mkdirs();
				}
				System.err.println("if directory exists : ====>"+directory.exists());
				 originalFileName = image.getOriginalFilename();

				System.err.println("original file name : ======>"+originalFileName);

				String absolutePath = urlpath+File.separator +originalFileName;

				System.err.println("absolute path :=====>"+absolutePath);
				// Copy the file to the destination directory
				Files.copy(image.getInputStream(), Paths.get(absolutePath), StandardCopyOption.REPLACE_EXISTING);

//				UserEntity user = new UserEntity();
//				System.out.println("usr is here ================>"+user);

//				byte[] imageBytes = Files.readAllBytes(Paths.get(absolutePath));
//				UserEntity user=new UserEntity();

//				System.out.println("Image byte array length: " + imageBytes.length);
				// Save the relative path to the image in the database
//				userEntity.setProfilePhotoPath("/images/" + newFileName); // Store the relative path in DB
//				System.out.println("user is :===>"+user);
				userprofile= userRepo.save(userEntity);

				System.err.println(userprofile);

//				return "Image uploaded successfully.";
				return "Image uploaded successfully! File saved at: " + absolutePath;

			}
//			return "User not found.";
		} catch (IOException e) {
			e.printStackTrace();
//			return "Failed to upload image.";
		}
		return "File is saved "+"\n"+" File id is : "+(Objects.isNull(userprofile) ? "Image not found":userprofile.getId());
	}

@Override
public byte[] uploadProfileImage(MultipartFile image) throws IOException {
    String originalFileName = null;
	byte[] imageBytes = null;
    UserEntity userprofile = null;
    String absolutePath = null;
    try {
//		Optional<UserEntity> optionalUser = userRepo.findById(id);
//		System.err.println(optionalUser);
        String urlpath = new ClassPathResource("").getFile().getAbsolutePath() + File.separator + "static" + File.separator + "documents";
        System.err.println("This is uerl path====>" + urlpath);
//		if (optionalUser.isPresent()) {
//			UserEntity userEntity = optionalUser.get();
        // Create the directory if it doesn't exist
        File directory = new File(urlpath);

        if (!directory.exists()) {
            directory.mkdirs();
        }
        System.err.println("if directory exists : ====>" + directory.exists());
        originalFileName = image.getOriginalFilename();

        System.err.println("original file name : ======>" + originalFileName);

        absolutePath = urlpath + File.separator + originalFileName;

        System.err.println("absolute path :=====>" + absolutePath);
        // Copy the file to the destination directory
        Files.copy(image.getInputStream(), Paths.get(absolutePath), StandardCopyOption.REPLACE_EXISTING);

//				UserEntity user = new UserEntity();
//				System.out.println("usr is here ================>"+user);

//				byte[] imageBytes = Files.readAllBytes(Paths.get(absolutePath));
//				UserEntity user=new UserEntity();

//			userEntity.setProfilePhoto(absolutePath);

        // Save the relative path to the image in the database
//				userEntity.setProfilePhotoPath("/images/" + newFileName); // Store the relative path in DB
//				System.out.println("user is :===>"+user);
//			userprofile= userRepo.save(userEntity);

		imageBytes=Files.readAllBytes(Paths.get(absolutePath));

        System.err.println(userprofile);

//				return "Image uploaded successfully.";

//			return "User not found.";
    } catch (IOException e) {
        e.printStackTrace();
//			return "Failed to upload image.";
    }
//	return "File is saved "+"\n"+" File id is : "+(Objects.isNull(userprofile) ? "Image not found":userprofile.getId());
    return imageBytes;
}



}













/// image store===>
//@Override
//public String uploadProfileImage(Integer id, MultipartFile image) {
//	String originalFileName=null;
//	UserEntity userprofile=null;
//	try {
//		Optional<UserEntity> optionalUser = userRepo.findById(id);
//		System.err.println(optionalUser);
//		String urlpath=new ClassPathResource("").getFile().getAbsolutePath()+File.separator+"static"+File.separator+"documents";
//		System.err.println("This is uerl path====>"+urlpath);
//		if (optionalUser.isPresent()) {
//			UserEntity userEntity = optionalUser.get();
//			// Create the directory if it doesn't exist
//			File directory = new File(urlpath);
//
//			if (!directory.exists()) {
//				directory.mkdirs();
//			}
//			System.err.println("if directory exists : ====>"+directory.exists());
//			originalFileName = image.getOriginalFilename();
//
//			System.err.println("original file name : ======>"+originalFileName);
//
//			String absolutePath = urlpath+File.separator +originalFileName;
//
//			System.err.println("absolute path :=====>"+absolutePath);
//			// Copy the file to the destination directory
//			Files.copy(image.getInputStream(), Paths.get(absolutePath), StandardCopyOption.REPLACE_EXISTING);
//
////				UserEntity user = new UserEntity();
////				System.out.println("usr is here ================>"+user);
//
////				byte[] imageBytes = Files.readAllBytes(Paths.get(absolutePath));
////				UserEntity user=new UserEntity();
//
//			userEntity.setProfilePhoto(absolutePath);
//
//			// Save the relative path to the image in the database
////				userEntity.setProfilePhotoPath("/images/" + newFileName); // Store the relative path in DB
////				System.out.println("user is :===>"+user);
//			userprofile= userRepo.save(userEntity);
//
//			System.err.println(userprofile);
//
////				return "Image uploaded successfully.";
//		}
////			return "User not found.";
//	} catch (IOException e) {
//		e.printStackTrace();
////			return "Failed to upload image.";
//	}
//	return "File is saved "+"\n"+" File id is : "+(Objects.isNull(userprofile) ? "Image not found":userprofile.getId());
//}
