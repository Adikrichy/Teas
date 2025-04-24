package org.aldousdev.teas.repo.cart;

import org.aldousdev.teas.models.cart.Cart;
import org.aldousdev.teas.models.tea.Sugar;
import org.aldousdev.teas.models.tea.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {


    //Look up duplicated cart
    Optional<Cart> findByAccountIdAndMenuitemIdAndMilkIdAndSizeIdAndSugarAndTemperature(

            Long account_id,
            Long menuitem_id,
            Long milk_id,
            Long size_id,
            Sugar sugar,
            Temperature temperature
    );


    <T> List<T> findByAccountIdOrderByIdDesc(Long accountId, Class<T> type);

    <T> Optional<T> findByIdAndAccountId(Long id, Long accountId, Class<T> type);

    <T> Optional<T> findById(Long id, Class<T> type);

    void deleteAllByAccountId(Long accountId);

    boolean existsByIdAndAccountId(Long id, Long accountId);
}
