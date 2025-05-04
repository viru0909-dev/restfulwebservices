package com.example.restfulwebservices.repo;

import com.example.restfulwebservices.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

}
