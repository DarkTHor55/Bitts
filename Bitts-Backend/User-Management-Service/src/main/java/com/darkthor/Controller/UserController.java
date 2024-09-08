package com.darkthor.Controller;

import com.darkthor.Model.User;
import com.darkthor.Request.OtpCreationRequest;
import com.darkthor.Request.OtpValidationRequest;
import com.darkthor.Request.UserRequest;
import com.darkthor.Response.JwtResponse;
import com.darkthor.Response.LoginRequest;
import com.darkthor.Service.IUserService;
import com.darkthor.Service.Impl.MailService;
import com.darkthor.Service.Impl.UserServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final MailService mailService;

//    Sign Up
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody final UserRequest request){
        User user=userService.createUser(request);
        if (Objects.isNull(user)){
            return ResponseEntity.badRequest().body("User creation failed");
        }
        return ResponseEntity.ok("User created successfully");
    }
//    otp creation
    @PostMapping("/otp-create")
    public ResponseEntity<String> sendOtp(@RequestBody final OtpCreationRequest request){
        mailService.sendEmail(request.getEmail());
        return ResponseEntity.ok("OTP sent successfully");
    }
    @GetMapping("/test")
    public String test(){
        return "test successfull..";
    }

//    opt validation
    @PostMapping("/otp-validate")
    public ResponseEntity<String> isEmailValidated(@RequestBody final OtpValidationRequest request){
       boolean res= userService.isEmailValidated(request.getEmail(),request.getOtp());
       if(res){
           return ResponseEntity.ok("Email validated successfully");
       }
       return ResponseEntity.badRequest().body("Email validation failed");
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody final LoginRequest request) {
        String token = userService.loginUser(request);

        if (token != null && !token.isEmpty()) {
            JwtResponse jwtResponse = new JwtResponse(token);
            return ResponseEntity.ok(jwtResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // Use UNAUTHORIZED for invalid credentials
        }
    }


}
