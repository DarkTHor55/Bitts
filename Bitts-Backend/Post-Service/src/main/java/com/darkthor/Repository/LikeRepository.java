package com.darkthor.Repository;

import com.darkthor.Model.Like;
import com.darkthor.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

}
