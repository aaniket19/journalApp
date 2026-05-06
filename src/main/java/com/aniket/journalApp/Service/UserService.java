package com.aniket.journalApp.Service;

import com.aniket.journalApp.Entity.User;
import com.aniket.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveEntry(User user){
        userRepository.save(user);
    }
    // this method creates a user of secured version of application
    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user. setRoles (Arrays. asList("USER")) ;
        userRepository.save(user);
    }
    // this method to call/save ADMIN user role who can access all users
    public void saveAdminUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById( id);
    }

    public Optional<User> deleteById (ObjectId id){
        userRepository.deleteById(id);
        return Optional.empty();
    }

    public User findByUserName(String userName){
        return userRepository.findByUsername(userName);
    }

}
