package com.murun.fict.repository;

import com.murun.fict.model.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;


public interface LegalEntityRepository extends JpaRepository<LegalEntity, Integer>, com.murun.fict.repository.LegalEntityRepositoryCustom {

}




