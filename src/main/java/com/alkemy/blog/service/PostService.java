package com.alkemy.blog.service;

import com.alkemy.blog.entity.Post;
import com.alkemy.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService{

    @Autowired
    private PostRepository postRepository;


    @Override
    public Page<Post> viewAll(Pageable pageable) throws Exception {
        try {
            return postRepository.findAll(pageable);
        }catch (Exception e){ throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Post findById(Long id) throws Exception {
        try {
              return postRepository.findById(id).get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Post save(Post post) throws Exception {
               /*if (!file.isEmpty()){
                String ruta = "c://images";
                byte[] bytes = file.getBytes();
                Path rutaAbsoluta = Paths.get(ruta + file.getOriginalFilename());
                Files.write(rutaAbsoluta, bytes);
                post.setImage(file.getOriginalFilename());*/
        try {
              return postRepository.save(post);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Post update(Long id, Post post) throws Exception {
        try {
            Optional<Post> postOptional = postRepository.findById(id);
            Post nuevoPost = postOptional.get();
            return nuevoPost = postRepository.save(post);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
              if (postRepository.existsById(id)){
                  postRepository.deleteById(id);
                  return true;
              }else {
                  throw new Exception();
              }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
