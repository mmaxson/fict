package com.murun.fict.service;

import com.murun.fict.model.EntityAddress;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.repository.LegalEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Cacheable(value = "getAllLegalEntities")
    public List<LegalEntity> getAllLegalEntities() {
        logger.info("getAllLegalEntities");
        return legalEntityRepository.findAll();
    }

    public List<LegalEntity> getAllEntitiesFilterByEntityType(String onlyLegalEntityTypeText) {
        logger.info("getAllEntitiesFilterByEntityType");

        Integer legalEntityTypeId = legalEntityTypeService.getLegalEntityTypeId(onlyLegalEntityTypeText);
        return getAllLegalEntities().stream().filter(x -> x.getLegalEntityType().getLegalEntityTypeId().equals(legalEntityTypeId)).collect(toList());
    }

    public Optional<LegalEntity> getEntityById(int legalEntityId) {
        logger.info("getEntityById");

        return getAllLegalEntities().stream().filter(x->x.getLegalEntityId().equals(legalEntityId)).findFirst();
    }

    public List<LegalEntity> getEntitiesWithAddressesInState(String state) {
        logger.info("getEntitiesWithAddressesInState");

        List<LegalEntity> retVal = new ArrayList<>();
        for (LegalEntity legalEntity : getAllLegalEntities()) {

            Optional<EntityAddress> entityAddress = legalEntity.getEntityAddresses().stream().filter(y -> y.getAddress().getState().equals(state)).findFirst();
            if (entityAddress.isPresent()) {
                retVal.add(legalEntity);
            }
        }

        return retVal;
    }


    public List<LegalEntity> getEntitiesWithAddressesInCity(String city) {
        logger.info("getEntitiesWithAddressesInCity");

        List<LegalEntity> retVal = new ArrayList<>();
        for (LegalEntity legalEntity : getAllLegalEntities()) {

            Optional<EntityAddress> entityAddress = legalEntity.getEntityAddresses().stream().filter(y -> y.getAddress().getCity().equals(city)).findFirst();
            if (entityAddress.isPresent()) {
                retVal.add(legalEntity);
            }
        }

        return retVal;
    }


    public List<LegalEntity> getAllEntitiesWithAddressesWithAddressType(String onlyAddressTypeText) {
        logger.info("getAllEntitiesWithAddressesWithAddressType");

        Integer addressTypeId = addressTypeService.getAddressTypeId(onlyAddressTypeText);

        List<LegalEntity> retVal = new ArrayList<>();
        for (LegalEntity legalEntity : getAllLegalEntities()) {
            Optional<EntityAddress> entityAddress = legalEntity.getEntityAddresses().stream().filter(y -> y.getAddressType().getAddressTypeId().equals(addressTypeId)).findFirst();
            if (entityAddress.isPresent()) {
                retVal.add(legalEntity);
            }
        }

        return retVal;
    }


}
