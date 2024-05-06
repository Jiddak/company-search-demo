package com.risk.companysearchdemo.repository;

import com.risk.companysearchdemo.model.dao.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {

    Optional<CompanyEntity> findByCompanyNumber(String companyNumber);

}
