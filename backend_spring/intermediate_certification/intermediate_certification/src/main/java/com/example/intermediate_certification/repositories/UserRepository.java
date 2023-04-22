package com.example.intermediate_certification.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import com.example.intermediate_certification.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(@NonNull String name);
}
