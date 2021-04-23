package com.alkemy.blog.controller;

import com.alkemy.blog.entity.Post;
import com.alkemy.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String index(Model model){

        try{
            Page<Post> posts = postService.viewAll(PageRequest.of(
                    0,
                    5,
                    Sort.by("id").descending()
            ));
            model.addAttribute("posts", posts);
            return "index";
        }catch (Exception e){
            model.addAttribute("error", "Por favor intente mas tarde!");
            return "index";
        }

    }

    @GetMapping("/blog/{page}")
    public String blog(@PathVariable("page") Integer page, Model model){
        try{
            Page<Post> posts = postService.viewAll(PageRequest.of(
                    page,
                    5,
                    Sort.by("id").descending()
            ));
            model.addAttribute("posts", posts);
            if (posts.getTotalPages() > 0){
                List<Integer> pages = IntStream.rangeClosed(1, posts.getTotalPages())
                        .boxed().collect(Collectors.toList());
                model.addAttribute("t_pages", pages);
                model.addAttribute("current", page);
                model.addAttribute("next", page + 2);
                model.addAttribute("prev", page );
                model.addAttribute("last", posts.getTotalPages());
            }

            return "blog";
        }catch (Exception e){
            model.addAttribute("error", "Por favor intente mas tarde!");
            return "blog";
        }
    }

    @GetMapping("/form")
    public String nuevoPost(Model model){
        try {
            Post post = new Post();
            model.addAttribute("titulo", "Nuevo Post");
            model.addAttribute("post", post);
            return "formPost";
        }catch (Exception e){

            return "";
        }
    }

    @PostMapping("/save")
    public String guardar(@Valid @ModelAttribute Post post, BindingResult result ,
                          @RequestParam("file") MultipartFile image, Model model){

        if (result.hasErrors()){
            model.addAttribute("titulo", "Nuevo Post");
            model.addAttribute("post", post);
            return "formPost";
        }

        if (!image.isEmpty()){
            String uniqueFilename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path rootPath = Paths.get("upload").resolve(uniqueFilename);
            Path rootAbsolutePath = rootPath.toAbsolutePath();
            try {

                  Files.copy(image.getInputStream(), rootAbsolutePath);
                post.setImage(uniqueFilename);
            }catch (Exception e){
                e.printStackTrace();

            }
        }else {
            post.setImage("");
        }

        try {
              post.setCreateAt(LocalDate.now());
              postService.save(post);
              return "redirect:/";
        }catch (Exception e){
            return "";
        }
    }
}
