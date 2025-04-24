package org.aldousdev.teas.dto.request;

import lombok.Data;

@Data
public class PurchaseRequest {
    private Double tip;
    private Double tax;
}
