package com.crud.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;






    @GetMapping("/users")
    public List<User> getUser(){
        return userService.getUser();
    }

    @GetMapping("{id}")
    public Optional<User> getUser (@PathVariable long id){
        return userService.getUserById(id);
    }

    @GetMapping("/u/{username}")
        public Optional<User> asd(@PathVariable String username){
        return   userService.findByUsername(username);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> userOpt = userService.findByUsername(user.getUsername());

        if (userOpt.isPresent()) {
            User userFromDb = userOpt.get();

            if (user.getPassword().equals(userFromDb.getPassword())) {
                String token = jwtService.generateToken(user.getUsername());
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("username", userFromDb.getUsername());
                return ResponseEntity.ok(response);
            }
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }


    @PostMapping("/register")
    public ResponseEntity<?> add(@RequestBody User user) {
        userService.validateUniqueFields(user.getUsername(),user.getEmail(),user.getId());
        System.out.println("user name" + user.getName());
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody User user) {
        userService.validateUniqueFields(user.getUsername(),user.getEmail(),user.getId());
        userService.updateUser(id, user.getName(), user.getLastname(), user.getUsername(),
                user.getPassword(), user.getEmail(),user.getStatus().name());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User updated");
    }
    @PatchMapping("{id}")
    public ResponseEntity<String> partialUpdate(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        String username = (String) updates.get("username");
        String email = (String) updates.get("email");
        userService.validateUniqueFields(username, email, id);
        userService.partialUpdate(id, updates);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User updated");
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody User user){
        Optional<User> userOpt = userService.findByUsername(user.getUsername());
        if(userOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("users don't exist");
        }
       userService.changePassword(user.getUsername(),user.getPassword());
        HashMap<String, String> response = new HashMap<>();
        response.put("messege","password change");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public boolean deleteUser(@PathVariable long id){
       return userService.deleteUser(id);
    }






    @DeleteMapping("/all")
    public void deleteAll(){
        userService.deleteall();
    }

}
