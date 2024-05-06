package com.risk.companysearchdemo.repository;

import com.risk.companysearchdemo.model.dao.OfficerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficerRepository extends JpaRepository<OfficerEntity,Long> {

    List<OfficerEntity> findByCompany_CompanyNumber(String companyNumber);

}
