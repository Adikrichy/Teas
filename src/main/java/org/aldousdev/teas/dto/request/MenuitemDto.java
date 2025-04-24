package org.aldousdev.teas.dto.request;

import lombok.*;
import org.aldousdev.teas.models.tea.Sugar;
import org.aldousdev.teas.models.tea.Temperature;


@Data //includes getter setter tostring etc
@AllArgsConstructor
@NoArgsConstructor

public class MenuitemDto
{
    private String title;
    private String imageUrl;
    private String description;
    private Long categoryId;
    private Long milkId;
    private Double price;
    private Temperature temperature;
    private Sugar sugar;

}
