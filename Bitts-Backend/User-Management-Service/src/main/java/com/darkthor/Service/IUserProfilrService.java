package com.darkthor.Service;

import com.darkthor.Exceptions.EmailNotFoundException;
import com.darkthor.Model.UserProfile;
import com.darkthor.Request.UserProfileRequest;

public interface IUserProfilrService {
    UserProfile upadteProfile(UserProfileRequest request,String email) throws EmailNotFoundException;
    UserProfile getUserProfile(String email);
}
