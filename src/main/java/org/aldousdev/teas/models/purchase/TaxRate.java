package org.aldousdev.teas.models.purchase;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String state;

    @Column(nullable = false, columnDefinition = "double default 0.0" )
    private Double rate=0.0;

    public TaxRate(String state, Double rate) {
        this.state = state;
        this.rate = rate;
    }
}
