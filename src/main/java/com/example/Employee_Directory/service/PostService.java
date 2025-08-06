package com.example.Employee_Directory.service;

import com.example.Employee_Directory.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getAllPost();
    Optional<Post> getPostById(int id);
    Post  addPost(Post post);
}
