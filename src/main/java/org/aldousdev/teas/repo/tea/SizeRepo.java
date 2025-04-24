package org.aldousdev.teas.repo.tea;

import org.aldousdev.teas.models.tea.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SizeRepo extends JpaRepository<Size, Long> {
    Optional<Size> findByTitle(String title);
}
