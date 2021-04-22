package com.alkemy.blog.service;

import com.alkemy.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IPostService {

    public Page<Post> viewAll(Pageable pageable) throws Exception;
    public Post findById(Long id) throws Exception;
    public Post save(Post post) throws Exception;
    public Post update(Long id, Post post) throws Exception;
    public boolean delete(Long id) throws Exception;
}
