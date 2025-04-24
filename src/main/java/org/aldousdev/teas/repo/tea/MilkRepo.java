package org.aldousdev.teas.repo.tea;

import org.aldousdev.teas.models.tea.Milk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MilkRepo extends JpaRepository<Milk, Long> {
    Optional<Milk> findMilkByTitle(String title);


}
