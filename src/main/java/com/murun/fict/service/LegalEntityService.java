package com.murun.fict.service;

import com.murun.fict.model.LegalEntity;
import com.murun.fict.model.LegalEntityRedis;
import com.murun.fict.repository.LegalEntityRedisRepository;
import com.murun.fict.repository.LegalEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class LegalEntityService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    LegalEntityRepository legalEntityRepository;

    @Resource
    LegalEntityRedisRepository legalEntityRedisRepository;

    @Resource
    private LegalEntityTypeService legalEntityTypeService;



    public Page<LegalEntity> getAllLegalEntities(Pageable pageRequest) {
        logger.info("getAllLegalEntities (pageRequest) " + pageRequest.getPageNumber() + ':' + pageRequest.getPageSize() );
        return legalEntityRepository.findAll(pageRequest);
    }

    public Page<LegalEntityRedis> getAllEntitiesFilterByEntityType(String onlyLegalEntityTypeText, Pageable pageRequest) {
        logger.info("getAllEntitiesFilterByEntityType(R): " + pageRequest.getPageNumber() + ":" + pageRequest.getPageSize());
        Integer legalEntityTypeId = legalEntityTypeService.getLegalEntityTypeId(onlyLegalEntityTypeText);

        /*Page<LegalEntityRedis> retVal = legalEntityRedisRepository.getEntitiesFilterByLegalEntityTypeIdOrderByOrdering(legalEntityTypeId, pageRequest);
        System.out.println("retVal 1---------------------------------------------------------------------------" + legalEntityTypeId);*/

        Page<LegalEntityRedis> retVal = legalEntityRedisRepository.findAll(pageRequest);
        System.out.println("retVal 2---------------------------------------------------------------------------" + legalEntityTypeId);

      /* for( LegalEntityRedis red: retVal) {
           System.out.println( red.getLegalEntityId()+ ":" + red.getLegalEntityTypeId());
       }*/

        return retVal;
    }

   /* public Page<LegalEntity> getAllEntitiesFilterByEntityType(String onlyLegalEntityTypeText, Pageable pageRequest) {
        logger.info("getAllEntitiesFilterByEntityType: " + pageRequest.getPageNumber() + ":" + pageRequest.getPageSize());
        Integer legalEntityTypeId = legalEntityTypeService.getLegalEntityTypeId(onlyLegalEntityTypeText);

        Page<LegalEntity> retVal = legalEntityRepository.getEntitiesFilterByLegalEntityTypeIdOrderByOrdering(legalEntityTypeId, pageRequest);
        System.out.println("retVal---------------------------------------------------------------------------" + legalEntityTypeId);
        retVal.map(x->x.getEntityNames().iterator().next()).forEach(System.out::println);


        return retVal;
    }*/

    public LegalEntity getEntityById(Integer legalEntityId) {
        logger.info("getEntityById");
        return legalEntityRepository.findById(legalEntityId).get();
    }

    // TODO reimplement
/*
    public Page<LegalEntity> getEntitiesWithAddressesInState(String state, Pageable pageRequest) {
        logger.info("getEntitiesWithAddressesInState");
        return new PageImpl( new ArrayList<LegalEntity>());
    }


    public Page<LegalEntity> getEntitiesWithAddressesInCity(String city, Pageable pageRequest) {
        logger.info("getEntitiesWithAddressesInCity");
        return new PageImpl( new ArrayList<LegalEntity>());
    }


    public Page<LegalEntity> getAllEntitiesWithAddressesWithAddressType(String onlyAddressTypeText, Pageable pageRequest) {
        logger.info("getAllEntitiesWithAddressesWithAddressType");

        Integer addressTypeId = addressTypeService.getAddressTypeId(onlyAddressTypeText);
        return new PageImpl( new ArrayList<LegalEntity>());

    }*/


}
