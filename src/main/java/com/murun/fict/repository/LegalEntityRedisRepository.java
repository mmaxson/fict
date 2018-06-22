package com.murun.fict.repository;

import com.murun.fict.model.LegalEntityRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.SortedSet;

@Repository
public interface LegalEntityRedisRepository extends JpaRepository<LegalEntityRedis, Integer> {

    Page<LegalEntityRedis> getEntitiesFilterByLegalEntityTypeIdOrderByOrdering(Integer legalEntityTypeId, Pageable page);
  //  Page<LegalEntityRedis> getEntities(Pageable page);

    //List<LegalEntityRedis> getEntitiesFilterByLegalEntityTypeIdOrderByOrdering(Integer legalEntityTypeId);

}


