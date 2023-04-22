package com.example.intermediate_certification.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import com.example.intermediate_certification.models.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
  UserRole findByName(@NonNull String name);
}
