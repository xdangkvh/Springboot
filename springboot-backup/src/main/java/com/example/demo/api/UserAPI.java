package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.dto.RoleDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.IUserService;

@CrossOrigin
@RestController // RestController = ReponseBody + Controller; declare @RestController to create
                // API
public class UserAPI {
    @Autowired
    private IUserService userService;

    // get user forward id
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // get many users
    @PostMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsersByIds(@RequestBody List<Long> ids) {
        try {
            List<UserDTO> userDTOs = userService.getUsersByIds(ids);
            return new ResponseEntity<>(userDTOs, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // get all users
    @GetMapping(value = "/allUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    // inser user
    @PostMapping(value = "/user") // PostMapping = method POST + RequestMapping
    public UserDTO createUser(@RequestBody UserDTO model) {
        // RoleDTO roleDTO = new RoleDTO();
        // userService.addRoleToUser(model.getId(), roleDTO.getId());
        return userService.save(model);
    }

    // insert role user
    @PostMapping(value = "/users/{idUser}/roles/{idRole}")
    public ResponseEntity<?> addRoleToUser(@PathVariable String idUser, @PathVariable String idRole) {
        userService.addRoleToUser(idUser, idRole);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/user/{id}")
    public UserDTO updateUser(@RequestBody UserDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return userService.save(model);
    }

    @DeleteMapping(value = "/user/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.delete(id);

    }

    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestBody List<Long> ids) {
        userService.delete(ids);

    }

}
