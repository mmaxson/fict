package com.murun.fict.repository;

import com.murun.fict.model.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;



public interface LegalEntityRepository extends JpaRepository<LegalEntity, Integer> {

    @Query("select legalEntity from LegalEntity as legalEntity where legalEntity.legalEntityType.legalEntityTypeId = :legalEntityTypeId")
    Page<LegalEntity> getAllEntitiesFilterByEntityType(@Param("legalEntityTypeId") Integer legalEntityTypeId, Pageable page);

   /* // TODO fix
    @Query("select legalEntity from LegalEntity as legalEntity where upper(legalEntity.entityAddresses.address.state) = upper(:state)")
    Page<LegalEntity> getEntitiesWithAddressesInState(@Param("state") String state, Pageable page);

    // TODO fix
    @Query("select legalEntity from LegalEntity as legalEntity where lower(legalEntity.entityAddresses.address.city) = lower(:city)")
    Page<LegalEntity> getEntitiesWithAddressesInCity(@Param("city") String city, Pageable page);

    @Query("select legalEntity from LegalEntity as legalEntity where legalEntity.addressType.addressTypeId = :addressTypeId")
    Page<LegalEntity> getEntitiesWithAddressTypeId(@Param("addressTypeId") Integer addressTypeId, Pageable page );
*/
}


