package com.blogwebsite.blog.paging;

import com.blogwebsite.blog.proxy.BlogProxy;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BlogResponse {

    private List<BlogProxy> content;
    private Long pageNumber;
    private Long pageSize;
    private Long totalElements;
    private Long totalPages;
    private Boolean lastPage;
}
