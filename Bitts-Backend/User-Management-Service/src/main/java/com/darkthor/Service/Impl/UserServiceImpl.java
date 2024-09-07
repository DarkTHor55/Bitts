package com.darkthor.Service.Impl;

import com.darkthor.Configuration.JwtUtils;
import com.darkthor.Exceptions.EmailNotFoundException;
import com.darkthor.Exceptions.UserNotFoundException;
import com.darkthor.Model.User;
import com.darkthor.Repository.UserRepository;
import com.darkthor.Request.UserRequest;
import com.darkthor.Response.LoginRequest;
import com.darkthor.Service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    public static final int otp = randomNumber();
    public boolean emailValidation = false;

    @Override
    public User createUser(@Valid final UserRequest userRequest) {

        if(!isExits(userRequest.getEmail())){
            User user= User.builder()
                    .userId(generateUniqueId())
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .email(userRequest.getEmail())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .build();
            if(emailValidation){
                emailValidation=false;
                return userRepository.save(user);
            }
            else{
                throw new RuntimeException("Email is not validated");
            }
        }
        throw new RuntimeException("Email Already Exists");
    }
    public boolean isEmailValidated(final String email,int newotp){
        if(otp==newotp){
            emailValidation=true;
            return true;
        }
        return false;
    }


    @Override
    public User getUserByEmail(final String email)  {
        if(isExits(email)) {
            return userRepository.findByEmail(email);
        }else{
            throw new RuntimeException("Email Not Found");
        }
    }

    @Override
    public User updateUser(final Long userId,final UserRequest userRequest) {
        return null;
    }

    @Override
    public boolean deleteUser(final String email)throws UserNotFoundException {
        if (isExits(email)) {
            User user = userRepository.findByEmail(email);
            userRepository.delete(user);
            return true;
        }else{
            throw new UserNotFoundException("User Not Found");
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public String loginUser(LoginRequest request ){
        User user = userRepository.findByEmail(request.getEmail());
        if(user!=null && passwordEncoder.matches(request.getPassword(),user.getPassword())){
            return jwtUtils.generateToken(user);
        }
        return null;
    }
    public static int randomNumber() {
        Random
                random = new Random();
        int min = 1000;
        int max = 9999;
        return random.nextInt((max - min) + 1) + min;
    }
    private boolean isExits(final String eamil){
        User user= userRepository.findByEmail(eamil);
        return user!=null;
    }
    private  static long generateUniqueId() {
        long currentTimeMillis = System.currentTimeMillis();
        UUID uuid = UUID.randomUUID();
        long uuidLeastSigBits = uuid.getLeastSignificantBits();
        return currentTimeMillis ^ uuidLeastSigBits;
    }


}
