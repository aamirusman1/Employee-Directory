package com.example.Employee_Directory.service.service_impl;

import com.example.Employee_Directory.exception.custom.PostNotFoundException;
import com.example.Employee_Directory.model.Post;
import com.example.Employee_Directory.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private RestClient restClient;

    @Override
    public List<Post> getAllPost() {
         Post[] allPosts = restClient.get()
                .uri("/posts")
                .retrieve()
                .body(Post[].class);
        return Arrays.asList(allPosts) ;
    }

    @Override
    public Optional<Post> getPostById(int id) {
        Post post = restClient.get()
                .uri("/posts/"+id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new PostNotFoundException("Post not found with ID: " + id);
                    }
                    throw new RuntimeException("Client error: " + response.getStatusCode());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new RuntimeException("Server error: " + response.getStatusCode());
                })
                .body(Post.class);
        if (post == null || post.getId()==0) {
            return Optional.empty();
        }

        return Optional.of(post);
    }

    @Override
    public Post addPost(Post post) {
        return restClient.post()
                .uri("/posts")
                .body(post)
                .retrieve()
                .body(Post.class);

    }
}
