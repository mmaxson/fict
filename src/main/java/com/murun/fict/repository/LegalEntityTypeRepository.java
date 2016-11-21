package com.murun.fict.repository;

import com.murun.fict.model.LegalEntity;
import com.murun.fict.model.LegalEntityType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LegalEntityTypeRepository extends JpaRepository<LegalEntityType, Integer>, LegalEntityRepositoryCustom {

}




