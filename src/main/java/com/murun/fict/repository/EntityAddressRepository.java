package com.murun.fict.repository;

import com.murun.fict.model.EntityAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EntityAddressRepository extends JpaRepository<EntityAddress, Integer> {

    @Query("select entityAddress from EntityAddress as entityAddress where entityAddress.legalEntity.legalEntityId = :legalEntityId")
    Page<EntityAddress> getAddressesByEntityId(@Param("legalEntityId") Integer legalEntityId, Pageable page);

}




