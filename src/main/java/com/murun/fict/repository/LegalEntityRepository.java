package com.murun.fict.repository;

import com.murun.fict.model.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface LegalEntityRepository extends JpaRepository<LegalEntity, Integer> {

    @Query("select distinct legalEntity from LegalEntity as legalEntity  "
            + "join legalEntity.entityAddresses as addresses "
            + "join addresses.address as address "
            + "where upper(address.state) = upper(:state)")
    public List<LegalEntity> getEntitiesWithAddressesInState(@Param("state") String state);


    @Query("select distinct legalEntity from LegalEntity as legalEntity  "
            + "join legalEntity.entityAddresses as addresses "
            + "join addresses.address as address "
            + "where lower(address.city) = lower(:city)")
    public List<LegalEntity> getEntitiesWithAddressesInCity(@Param("city") String city);



}


