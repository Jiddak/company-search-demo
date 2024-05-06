
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
@Table(name = "officer")
public class OfficerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "officer_generator")
    @SequenceGenerator(name = "officer_generator", sequenceName = "officer_seq", allocationSize = 1)
    private Long officerId;
    private String name;
    private String officerRole;
    private String appointedOn;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private CompanyEntity company;

    @OneToOne(cascade=CascadeType.PERSIST)
    private AddressEntity address;

}
