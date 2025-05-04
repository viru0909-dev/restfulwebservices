package com.example.restfulwebservices.repo;

import com.example.restfulwebservices.Post.Post;
import com.example.restfulwebservices.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {

}
