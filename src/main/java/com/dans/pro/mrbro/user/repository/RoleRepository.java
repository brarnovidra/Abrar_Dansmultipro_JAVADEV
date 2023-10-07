package com.dans.pro.mrbro.user.repository;

import java.util.Optional;

import com.dans.pro.mrbro.user.models.ERole;
import com.dans.pro.mrbro.user.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
