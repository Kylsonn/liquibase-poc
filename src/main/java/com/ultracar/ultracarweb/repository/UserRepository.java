package com.ultracar.ultracarweb.repository;

import com.ultracar.ultracarweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
