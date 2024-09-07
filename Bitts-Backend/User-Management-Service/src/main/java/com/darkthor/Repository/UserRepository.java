package com.darkthor.Repository;

import com.darkthor.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByEmail(final String email);
}
