package com.example.restfulwebservices;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static int userCount = 0;
    private static List<User> users = new ArrayList<>();
    static {
        users.add(new User(++userCount, "Ram", LocalDate.now().minusYears(30)));
        users.add(new User(++userCount, "Laxman", LocalDate.now().minusYears(25)));
        users.add(new User(++userCount, "Sita", LocalDate.now().minusYears(23)));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user){
        user.setId(++userCount);
        users.add(user);
        return user;
    }
    public User findOne(int id){
        return  users.stream()
                .filter(user -> user.getId()==id)
                .findFirst()
                .orElse(null);
    }
    public void DeleteById(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }
}
