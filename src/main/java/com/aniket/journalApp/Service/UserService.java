package com.aniket.journalApp.Service;

import com.aniket.journalApp.Entity.User;
import com.aniket.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){
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
