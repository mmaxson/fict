package com.murun.fict.service;

import com.murun.fict.model.LegalEntity;
import com.murun.fict.model.LegalEntityType;
import com.murun.fict.repository.LegalEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Service
@Transactional
public class LegalEntityService {

    private static final Logger logger = LoggerFactory.getLogger(LegalEntityService.class);

    @Resource
    LegalEntityRepository legalEntityRepository;

    @Resource
    private LegalEntityTypeService legalEntityTypeService;

    @Resource
    private AddressTypeService addressTypeService;

    public List<LegalEntity> getAllLegalEntities() {
        logger.info("getAllLegalEntities");
        return legalEntityRepository.findAll();
    }


    public List<LegalEntity> getAllEntitiesFilterByEntityType(String onlyLegalEntityTypeText) {
        logger.info("getAllEntitiesFilterByEntityType");

        LegalEntity legalEntity = new LegalEntity();
        LegalEntityType legalEntityType = new LegalEntityType();
        legalEntityType.setLegalEntityTypeId(legalEntityTypeService.getLegalEntityTypeId(onlyLegalEntityTypeText));
        legalEntityType.setLegalEntityTypeText(onlyLegalEntityTypeText);
        legalEntity.setLegalEntityType(legalEntityType);

        Example<LegalEntity> example = Example.of(legalEntity);
        return legalEntityRepository.findAll(example);
    }


    public LegalEntity getEntityById(int legalEntityId) {
        logger.info("getEntityById");

        return legalEntityRepository.findOne(legalEntityId);
    }

    public List<LegalEntity> getEntitiesWithAddressesInState(String state) {
        logger.info("getEntitiesWithAddressesInState");

        return legalEntityRepository.getEntitiesWithAddressesInState(state);
    }


    public List<LegalEntity> getEntitiesWithAddressesInCity(String city) {
        logger.info("getEntitiesWithAddressesInCity");

        return legalEntityRepository.getEntitiesWithAddressesInCity(city);
    }


    public List<LegalEntity> getAllEntitiesWithAddressesWithAddressType(String onlyAddressTypeText) {
        logger.info("getAllEntitiesWithAddressesWithAddressType");

        Integer addressTypeId = addressTypeService.getAddressTypeId(onlyAddressTypeText);
        return legalEntityRepository.getEntitiesWithAddressTypeId(addressTypeId);
    }

/*
    public Optional<Address> getById(int id){
        return Optional.of(addressRepository.findOne(id));
	}

	public int updateAddress( Address address ){
        Address ret = addressRepository.save( address );
		return ret.getLegalEntityId();
	}

    public int createAddress( Address address ){
        Address ret = addressRepository.save( address );
        return ret.getLegalEntityId();
    }*/


}
