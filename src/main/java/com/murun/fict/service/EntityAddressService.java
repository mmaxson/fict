package com.murun.fict.service;

import com.murun.fict.dto.EntityAddressDTO;
import com.murun.fict.model.EntityAddress;
import com.murun.fict.repository.EntityAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class EntityAddressService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    EntityAddressRepository entityAddressRepository;

    @Resource
    AddressTypeService addressTypeService;

    public Page<EntityAddress> getEntityAddressesByLegalEntityId(Integer legalEntityId, Pageable pageRequest) {
        logger.info("getEntityAddressesByLegalEntityId: " + pageRequest.getPageNumber() + ' ' + pageRequest.getPageSize());
        return entityAddressRepository.getEntityAddressesByLegalEntityId(legalEntityId, pageRequest);
    }

    public EntityAddress saveEntityAddress(EntityAddressDTO entityAddressDTO){
        EntityAddress entityAddress = EntityAddress.createEntityAddress(entityAddressDTO, addressTypeService.createAddressTypeById(entityAddressDTO.getAddressTypeId()));
        logger.info("saveEntityAddress: " +  entityAddress);
        return entityAddressRepository.saveAndFlush(entityAddress);
    }

    public Integer deleteEntityAddressesByEntityAddressId(Integer id){
        logger.info("deleteEntityAddressesByEntityAddressId: " +  id);

        return entityAddressRepository.deleteEntityAddressesByEntityAddressId(id);
    }

}
