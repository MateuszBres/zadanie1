package com.crud.crud;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    private String generatePassword(){
        int length = 10;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        String characters ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";

        for(int i = 0; i<length; i++){
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }
        return password.toString();

    }

    private void sendPasswordEmail(String email, String password){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your password to account");
        message.setText("Your password is: " + password);
        mailSender.send(message);
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

    public boolean existByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User addUser(User user) {
        String generatePassword = generatePassword();
        String hashedPassword = BCrypt.hashpw(generatePassword, BCrypt.gensalt());
        user.setPassword(hashedPassword);
        if (user.getStatus() == null) {
            user.setStatus(Status.ACTIVE);
        }
        user.setRegistrationDate(LocalDateTime.now());
        //sendPasswordEmail(user.getEmail(),generatePassword);
        userRepository.save(user);

        return user;
    }

    public Optional<User> updateUser(long id, String newName, String newLastName, String newUsername,
                                     String newPassword, String newEmail, String newStatus){
        return userRepository.findById(id).map(user->{
        user.setName(newName);
        user.setLastname(newLastName);
        user.setUsername(newUsername);
        user.setPassword(newPassword);
        user.setEmail(newEmail);
        user.setStatus(Status.valueOf(newStatus));

            return userRepository.save(user);
        });
    }

    public Optional<User> partialUpdate(long id, Map<String, Object>updates ){
        return userRepository.findById(id).map(user -> {
            updates.forEach((key,value) ->{
                if (value instanceof String){
                    switch (key){
                        case "name": user.setName((String)value);
                        break;
                        case "lastname": user.setLastname((String)value);
                        break;
                        case "username": user.setUsername((String)value);
                        break;
                        case "password": user.setPassword((String)value);
                        break;
                        case "email": user.setEmail((String)value);
                        break;
                        case "status": user.setStatus(Status.valueOf((String) value));
                        break;
                    }
                }
            });
            return userRepository.save(user);
        });
    }



    public boolean deleteUser(long id){
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    private boolean isSameUsername(Long userId, String username) {
        return getUserById(userId)
                .map(user -> user.getUsername().equals(username))
                .orElse(false);
    }

    private boolean isSameEmail(Long userId, String email) {
        return getUserById(userId)
                .map(user -> user.getEmail().equals(email))
                .orElse(false);
    }

    public void validateUniqueFields(String username, String email, Long currentUserId) {
        if (username != null && existByUsername(username)) {
            if (currentUserId == null || !isSameUsername(currentUserId, username)) {
                throw new ConflictException("Username already exists");
            }
        }

        if (email != null && existByEmail(email)) {
            if (currentUserId == null || !isSameEmail(currentUserId, email)) {
                throw new ConflictException("Email already exists");
            }
        }
    }
    public void changePassword(String username, String newPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(newPassword);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }



    public void deleteall(){
        userRepository.deleteAll();
    }
}

