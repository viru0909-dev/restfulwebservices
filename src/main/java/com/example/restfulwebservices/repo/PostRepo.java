package com.example.restfulwebservices.repo;

import com.example.restfulwebservices.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {

}
