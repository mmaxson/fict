package com.murun.fict.service;

import com.murun.fict.model.EntityAddress;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.repository.LegalEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import static java.util.stream.Collectors.toList;


@Service
@Transactional
public class LegalEntityService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    LegalEntityRepository legalEntityRepository;

    @Resource
    private LegalEntityTypeService legalEntityTypeService;

    @Resource
    private AddressTypeService addressTypeService;

  //  @Cacheable(value = "getAllLegalEntities")
    public Page<LegalEntity> getAllLegalEntities(Pageable pageRequest) {
        logger.info("getAllLegalEntities  pageable");
        return legalEntityRepository.findAll(pageRequest);
    }

    public Page<LegalEntity> getAllEntitiesFilterByEntityType(String onlyLegalEntityTypeText, Pageable pageRequest) {
        logger.info("getAllEntitiesFilterByEntityType");
        Integer legalEntityTypeId = legalEntityTypeService.getLegalEntityTypeId(onlyLegalEntityTypeText);
        return legalEntityRepository.getAllEntitiesFilterByEntityType(legalEntityTypeId, pageRequest);
    }

    public LegalEntity getEntityById(int legalEntityId) {
        logger.info("getEntityById");
        return legalEntityRepository.getOne(legalEntityId);
    }

    public Page<LegalEntity> getEntitiesWithAddressesInState(String state, Pageable pageRequest) {
        logger.info("getEntitiesWithAddressesInState");
        return legalEntityRepository.getEntitiesWithAddressesInState(state, pageRequest);
    }


    public Page<LegalEntity> getEntitiesWithAddressesInCity(String city, Pageable pageRequest) {
        logger.info("getEntitiesWithAddressesInCity");
        return legalEntityRepository.getEntitiesWithAddressesInCity(city, pageRequest);
    }


    public Page<LegalEntity> getAllEntitiesWithAddressesWithAddressType(String onlyAddressTypeText, Pageable pageRequest) {
        logger.info("getAllEntitiesWithAddressesWithAddressType");

        Integer addressTypeId = addressTypeService.getAddressTypeId(onlyAddressTypeText);
        return legalEntityRepository.getEntitiesWithAddressTypeId(addressTypeId, pageRequest);

    }


}
