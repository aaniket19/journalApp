package com.aniket.journalApp.Controller;

import com.aniket.journalApp.Entity.User;
import com.aniket.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }

    @PostMapping("/create-user")
    public void createUser (@RequestBody User user){
        userService.saveNewUser(user);
    }

}
