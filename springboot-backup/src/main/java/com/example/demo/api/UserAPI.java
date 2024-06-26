package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.IUserService;

@CrossOrigin
@RestController // RestController = ReponseBody + Controller; declare @RestController to create
                // API
public class UserAPI {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/user") // PostMapping = method POST + RequestMapping
    public UserDTO createUser(@RequestBody UserDTO model) {
        return userService.save(model);
    }

    @PutMapping(value = "/user/{id}")
    public UserDTO updateUser(@RequestBody UserDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return userService.save(model);
    }

    // @DeleteMapping(value = "/user/{id}")
    // public void deleteUser(@PathVariable("id") long id) {
    // userService.delete(id);

    // }

    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestBody List<Long> ids) {
        userService.delete(ids);

    }

}
