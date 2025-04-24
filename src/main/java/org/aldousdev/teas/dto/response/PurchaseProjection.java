package org.aldousdev.teas.dto.response;

import org.aldousdev.teas.models.tea.Sugar;
import org.aldousdev.teas.models.tea.Temperature;

import java.util.Date;
import java.util.List;

public interface PurchaseProjection {
    Long getId();
    AccountProjection getAccount();

    Date getPurchaseDate();
    Double getTip();
    Double getTax();
    Double getTotal();

    List<PurchaseLineitemProjection> getPurchaseLineitemList();

    interface PurchaseLineitemProjection {
        String getId();
        MenuitemProjection getMenuitem();

        MilkProjection getMilk();
        SizeProjection getSize();

        Temperature getTemperature();
        Sugar getSugar();

        int getQuantity();
        Double getPrice();
    }

    interface AccountProjection {
        Long getId();
        String getEmail();
    }
    interface MenuitemProjection {
        Long getId();
        String getTitle();
        Double getPrice();

    }
    interface MilkProjection {
        Long getId();
        String getTitle();
        Double getPrice();
    }
    interface SizeProjection {
        Long getId();
        String getTitle();
        Double getPrice();
    }
}
