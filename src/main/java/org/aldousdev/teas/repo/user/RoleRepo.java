package org.aldousdev.teas.repo.user;

import org.aldousdev.teas.models.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
