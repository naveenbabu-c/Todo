package com.firstSpring.First.Spring.Service;

import com.firstSpring.First.Spring.Configuration.JwtUtils;
import com.firstSpring.First.Spring.Models.User;
import com.firstSpring.First.Spring.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;



    public User create(User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if(user.getPassword().length()<8 ||user.getPassword().length()>20){
            throw new RuntimeException("Password should be greater than or eqauls to 8 and less than or equals to 20");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String login(User user){
        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
            return "invalid credentials";
        }

            var existUser =userRepository.findByEmail(user.getEmail()).get();
            if(!passwordEncoder.matches(user.getPassword(), existUser.getPassword())){
               return "invalid credentials" ;
            }

        String token = jwtUtils.generateToken(user.getEmail());
        return token;
    }

}
