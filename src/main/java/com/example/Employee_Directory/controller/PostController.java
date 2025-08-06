package com.example.Employee_Directory.controller;

import com.example.Employee_Directory.exception.custom.PostNotFoundException;
import com.example.Employee_Directory.model.Post;
import com.example.Employee_Directory.service.PostService;
import com.example.Employee_Directory.service.service_impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @GetMapping
    ResponseEntity<List<Post>> getAllPosts() {
        List<Post> allPosts = postService.getAllPost();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        Post post = postService.getPostById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + id));
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post createdPost = postService.addPost(post); // This will call your restClient POST method
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
}
