package org.aldousdev.teas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aldousdev.teas.models.tea.Sugar;
import org.aldousdev.teas.models.tea.Temperature;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartInputDto {
    private Long menuitemId;
    private Long milkId;
    private Temperature temperature;
    private Sugar sugar;
    private Long sizeId;
    private Integer quantity;

}
