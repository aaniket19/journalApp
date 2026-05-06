package com.aniket.journalApp.Controller;

import com.aniket.journalApp.Entity.User;
import com.aniket.journalApp.Repository.UserRepository;
import com.aniket.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // this controller creating a normal user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.saveNewUser(user);   // IMPORTANT
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    // this controller creating a admin user
    @PostMapping("/admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user){
        userService.saveAdminUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // to fetch all user's details for ADMIN user role
    @GetMapping
    public List<User> getAllUsers (){
        return userService.getAll();
    }

    // to update USER and ADMIN
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userInDb  = userService.findByUserName(username);

        if (userInDb == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // update username only if provided
        if (user.getUsername() != null && !user.getUsername().isEmpty()){
            userInDb.setUsername(user.getUsername());
        }

        // encode password before saving
        if (user.getPassword() != null && !user.getPassword().isEmpty()){
            userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userService.saveEntry(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
