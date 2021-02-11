package com.insea.SpringJwt.Controllers;

import com.insea.SpringJwt.Repositories.TokenRepository;
import com.insea.SpringJwt.Repositories.UserRepository;
import com.insea.SpringJwt.models.Credentials;
import com.insea.SpringJwt.models.Role;
import com.insea.SpringJwt.models.Token;
import com.insea.SpringJwt.models.User;
import com.oracle.javafx.jmx.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/Users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/test")
    public Map<String, String> test(){
       return  Collections.singletonMap("response", "Hello World");
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
        return userRepository.findById(id).get();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user) {
         Map<String, String> response=new HashMap<>();


        if( userRepository.findByEmail(user.getEmail())!=null){
            response.put("msg", "User with that Email already exists");
            response.put("code", HttpStatus.CONFLICT.value()+"");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        if(userRepository.findByUsername(user.getUsername())!=null){
            response.put("msg", "User with that Username already exists");
            response.put("code", HttpStatus.CONFLICT.value()+"");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        String passwordHashed = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHashed);

        return new ResponseEntity(userRepository.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Credentials credentials){

        Map<String, String> response=new HashMap<>();

        User user=userRepository.findByEmail(credentials.getEmail());
        if(user!=null){
           // String passwordHashed = passwordEncoder.encode(credentials.getPassword());
            if(passwordEncoder.matches(credentials.getPassword(),user.getPassword())){
                response.put("msg", "user successfully authenticated");
                response.put("code", HttpStatus.OK.value()+"");
                 Token oldToekn= tokenRepository.findByUserId(user.getId());
                String newToken;
                do{
                    newToken = Token.generateToken();
                }while(tokenRepository.existsById(newToken));
                response.put("token", newToken);

                 if(oldToekn==null){
                     tokenRepository.save(new Token(newToken,user.getId()));
                 }
                else{
                     tokenRepository.deleteById(oldToekn.getToken());
                     tokenRepository.save(new Token(newToken,user.getId()));
                 }
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                response.put("msg", "Wrong password");
                response.put("code", HttpStatus.FORBIDDEN.value()+"");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
        }else{
            response.put("msg", "User with that Email not exists");
            response.put("code", HttpStatus.FORBIDDEN.value()+"");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @GetMapping("/getUserByToken")
    public User getUserByToken(@RequestParam String t){
        Token token;
        try {
             token=tokenRepository.findById(t).get();
        }catch (Exception e){
            token=null;
        }
            if(token!=null){
                return  userRepository.findById(token.getUserId()).get();
            }
            return null;
    }

    @GetMapping("/logout")
    public ResponseEntity logout(@RequestParam String t){
        tokenRepository.deleteById(t);
        Map<String, String> response=new HashMap<>();
        response.put("msg", "User successfully logout");
        response.put("code", HttpStatus.OK.value()+"");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
