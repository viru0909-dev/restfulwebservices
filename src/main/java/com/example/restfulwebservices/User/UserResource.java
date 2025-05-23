package com.example.restfulwebservices.User;

import com.example.restfulwebservices.ErrorHandling.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    public UserDaoService service;

    public UserResource(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUser(){

        return service.findAll();
    }

    @GetMapping("/users/{id}")
        public User retrieveUser(@PathVariable int id){

        User user = service.findOne(id);

        if(user == null)
            throw new UserNotFoundException("id"+id);

        return user;
        }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){

       service.DeleteById(id);
    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
