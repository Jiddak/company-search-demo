
package com.risk.companysearchdemo.model.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_generator")
    @SequenceGenerator(name = "address_generator", sequenceName = "address_seq", allocationSize = 1)
    private Long addressId;
    private String locality;
    private String postalCode;
    private String premises;
    private String addressLine1;
    private String country;

}
