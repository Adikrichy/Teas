package org.aldousdev.teas.repo.tea;

import org.aldousdev.teas.models.tea.Menuitem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuitemRepo extends JpaRepository<Menuitem, Long> {

    @Query("SELECT m FROM Menuitem m Where m.category.id=:categoryId")
    List<Menuitem> findByCategoryIdQuery(Long categoryId);

//    @Query("SELECT pl.menuitem.id, m.title, count(*) " +
//            "FROM PurchaseLineitem pl " +
//            "inner join Menuitem m on m.id=pl.menuitem.id " +
//            "group by pl.menuitem.id " +
//            "order by count(*) desc ")

    @Query ("SELECT pl.menuitem.id, m.title, sum(pl.quantity) " +
            "FROM PurchaseLineitem pl " +
            "inner join Menuitem m on m.id = pl.menuitem.id " +
            "group by pl.menuitem.id " +
            "order by 3 desc"
    )
    List<Object[]> findBestSellers(Pageable pageable);


}
