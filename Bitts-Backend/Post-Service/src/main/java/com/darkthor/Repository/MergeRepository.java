package com.darkthor.Repository;

import com.darkthor.Model.MergeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MergeRepository extends JpaRepository<MergeRequest,Long> {
}
