package com.darkthor.Controller;

import com.darkthor.Configuration.JwtUtils;
import com.darkthor.Exceptions.EmailNotFoundException;
import com.darkthor.Model.Education;
import com.darkthor.Model.User;
import com.darkthor.Model.UserProfile;
import com.darkthor.Request.UserProfileRequest;
import com.darkthor.Response.UserProfileResponse;
import com.darkthor.Service.IUserProfilrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.POST;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user/profile")
@RestController
public class UserProfileController {
    private final IUserProfilrService userProfileService;
    private final JwtUtils jwtUtils;
    @PostMapping("/update")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @RequestParam("codingProfile") String codingProfile,
            @RequestParam("projectProfile") String projectProfile,
            @RequestParam("linkedinProfile") String linkedinProfile,
            @RequestPart("education") String educationJson, // education details as JSON
            @RequestPart("skills") String skillsJson, // skills details as JSON
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("resume") MultipartFile resume,
            @RequestHeader("Authorization") String authorizationHeader) throws EmailNotFoundException, IOException {

        String email = jwtUtils.extractEmail(authorizationHeader.substring(7));

        // Parse the education JSON into a list of Education objects
        ObjectMapper objectMapper = new ObjectMapper();
        List<Education> educations = Arrays.asList(objectMapper.readValue(educationJson, Education[].class));

        // Parse the skills JSON into a Set<String>
        Set<String> skills = objectMapper.readValue(skillsJson, Set.class);

        // Convert the photo and resume to byte arrays
        byte[] photoBytes = photo.getBytes();
        byte[] resumeBytes = resume.getBytes();

        // Create the UserProfileRequest object
        UserProfileRequest request = UserProfileRequest.builder()
                .education(educations)
                .skills(skills)
                .codingProfile(codingProfile)
                .projectProfile(projectProfile)
                .linkedinProfile(linkedinProfile)
                .photo(photoBytes)
                .resume(resumeBytes)
                .build();

        // Call the service to update the profile
        UserProfile userProfile = userProfileService.upadteProfile(request, email);

        if (userProfile != null) {
            return new ResponseEntity<>(UserProfileResponse.builder().staues("Updated").email(email).build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
