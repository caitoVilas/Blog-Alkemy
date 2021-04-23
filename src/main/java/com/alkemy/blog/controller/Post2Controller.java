package com.alkemy.blog.controller;

import com.alkemy.blog.entity.Post;
import com.alkemy.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/post")
public class Post2Controller {

    @Autowired
    private PostService postService;

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
    public String guardar(@ModelAttribute Post post, @RequestParam("file") MultipartFile image,
                          BindingResult result, Model model){
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
                System.out.println("el archivo no tiene contenido");

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

    @GetMapping("/edit/{id}")
    public String nuevoPost(@PathVariable("id") Long id, Model model){
        try {
            Post post = postService.findById(id);
            model.addAttribute("titulo", "Editar Post");
            model.addAttribute("post", post);
            return "formPost";
        }catch (Exception e){

            return "formPost";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        try {
            postService.delete(id);
            return "redirect:/";
        }catch (Exception e){
            return "redirect:/";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable("id") Long id, Map<String, Object> model){
        try {
             Post post = postService.findById(id);
             model.put("titulo", "Detalle Post");
             model.put("post", post);
             return "detallePost";
        }catch (Exception e){
            return "redirect:/";
        }
    }
}
