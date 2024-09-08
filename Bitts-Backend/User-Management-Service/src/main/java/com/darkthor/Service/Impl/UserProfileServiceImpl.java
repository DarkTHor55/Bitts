package com.darkthor.Service.Impl;

import com.darkthor.Exceptions.EmailNotFoundException;
import com.darkthor.Model.Education;
import com.darkthor.Model.User;
import com.darkthor.Model.UserProfile;
import com.darkthor.Repository.UserProfileRepository;
import com.darkthor.Request.UserProfileRequest;
import com.darkthor.Service.IUserProfilrService;
import com.darkthor.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements IUserProfilrService {
    private final IUserService userService;
    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfile upadteProfile(UserProfileRequest request, String email) throws EmailNotFoundException {
        User user = getUser(email);
        UserProfile profile = user.getUserProfile();

        if (profile != null) {
            // Add skills - prev + new
            Set<String> skills = profile.getSkills();
            if (skills == null) {
                skills = new HashSet<>();
            }
            if (request.getSkills() != null) {
                skills.addAll(request.getSkills());
            }
            profile.setSkills(skills);

            // Add education - prev + new
            List<Education> educations = profile.getEducation();
            if (educations == null) {
                educations = new ArrayList<>();
            }
            if (request.getEducation() != null) {
                educations.addAll(request.getEducation());
            }
            profile.setEducation(educations);

            // Update the rest of the fields
            if (request.getCodingProfile() != null) {
                profile.setCodingProfile(request.getCodingProfile());
            }
            if (request.getProjectProfile() != null) {
                profile.setProjectProfile(request.getProjectProfile());
            }
            if (request.getLinkedinProfile() != null) {
                profile.setLinkedinProfile(request.getLinkedinProfile());
            }
            if (request.getPhoto() != null) {
                profile.setPhoto(request.getPhoto());
            }
            if (request.getResume() != null) {
                profile.setResume(request.getResume());
            }

            // Save and return the updated profile
            return userProfileRepository.save(profile);
        } else {
            return null;  // or throw an exception if profile should always exist
        }
    }

    @Override
    public UserProfile getUserProfile(String email) {
        return null; // Implement this as needed
    }

    // Helper method to get user by email
    private User getUser(String email) throws EmailNotFoundException {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return user;
        }
        throw new EmailNotFoundException("User with email " + email + " not found");
    }
}
