package org.aldousdev.teas.repo.tea;

import org.aldousdev.teas.models.tea.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
}
