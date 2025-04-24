package org.aldousdev.teas.repo.user;

import org.aldousdev.teas.models.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Boolean existsByEmail(String email);

}
