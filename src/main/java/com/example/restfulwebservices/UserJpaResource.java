package com.example.restfulwebservices;

import com.example.restfulwebservices.Post.Post;
import com.example.restfulwebservices.repo.PostRepo;
import com.example.restfulwebservices.repo.UserRepo;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    private UserRepo userRepo;

    private PostRepo postRepo;

    public UserJpaResource(  UserRepo userRepo, PostRepo postRepo) {

        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUser(){

        return userRepo.findAll();
    }

    @GetMapping("/jpa/users/{id}")
        public EntityModel<User> retrieveUser(@PathVariable int id){

        Optional<User> user = userRepo.findById(id);

        if(user.isEmpty())
            throw new  UserNotFoundException("id"+id);

        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).retrieveAllUser());
        entityModel.add(linkBuilder.withRel("all-users"));
        return entityModel;
        }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){

       userRepo.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id){

        Optional<User> user = userRepo.findById(id);

        if(user.isEmpty())
            throw new  UserNotFoundException("id"+id);

         return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post post){

        Optional<User> user = userRepo.findById(id);

        if(user.isEmpty())
            throw new  UserNotFoundException("id"+id);

        post.setUser(user.get());

        Post savedPost = postRepo.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        User savedUser = userRepo.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
