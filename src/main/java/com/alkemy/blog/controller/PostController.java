package com.alkemy.blog.controller;

import com.alkemy.blog.entity.Post;
import com.alkemy.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<?> viewAll(){
        try {
              return ResponseEntity.status(HttpStatus.OK).body(postService.viewAll(
                      PageRequest.of(
                              0,
                              5,
                              Sort.by("id").descending()
                      )
              ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, Por favor intente mas tarde\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        try {
             return ResponseEntity.status(HttpStatus.OK).body(postService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, no Encontrado!\"}");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Post nuevoPost){
        try {
             return ResponseEntity.status(HttpStatus.OK).body(postService.save(nuevoPost));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, fallo al guardar!\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Post post){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.update(id,post));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, fallo al guardar!\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postService.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, fallo al eliminar!\"}");
        }
    }
}
