package com.demorest.controller;

import com.demorest.entity.User;
import com.demorest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Integer id){

        if(!userService.getUserById(id).isPresent()){
            String message = "Aucun utilisateur avec cet id";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(userService.getUserById(id).get());
    }


    @PostMapping("created")
    public ResponseEntity<String> post(@RequestBody User user) {

        if (userService.getUserUserName(user.getUsername())) {
            String errorMessage = "Le nom existe déjà";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        if (user == null || user.getUsername() == null) {
            String message = "Requête invalide username requis";
            return ResponseEntity.badRequest().body(message);
        }

        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("utilisateur créé avec succès");
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        String message = "La liste est vide";
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
        } else {
            return ResponseEntity.ok(users);
        }
    }


    @PutMapping("put/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Integer id, @RequestBody User user){

        if(!userService.getUserById(id).isPresent()){
            String message = "Utilisateur n'existe pas";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if(user == null || user.getUsername() == null){
            String message = "Requete invalide : username requis";
            return ResponseEntity.badRequest().body(message);

        }
        userService.updateUser(id, user);
        String message = "L'utilisateur avec l'id : " + id + "est mis à jour";
        return ResponseEntity.ok(message);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        if(userService.getUserById(id).isPresent()){
            String message = "Aucun utilisateur avec cet id";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/api/v1/all")).build();
    }

}
