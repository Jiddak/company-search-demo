
package com.risk.companysearchdemo.model.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "company", uniqueConstraints={@UniqueConstraint(columnNames={"companyNumber"})})
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
    @SequenceGenerator(name = "company_generator", sequenceName = "company_seq", allocationSize = 1)
    private Long companyId;
    private String companyNumber;
    private String companyType;
    private String title;
    private String companyStatus;
    private String dateOfCreation;
    @OneToOne(cascade=CascadeType.PERSIST)
    private AddressEntity address;
    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    private List<OfficerEntity> officers;

}
