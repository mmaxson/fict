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

    public Page<EntityAddress> getEntityAddressesByEntityId(Integer legalEntityId, Pageable pageRequest) {
        logger.info("getEntityAddressesByEntityId: " + pageRequest.getPageNumber() + ' ' + pageRequest.getPageSize());
        return entityAddressRepository.getAddressesByEntityId(legalEntityId, pageRequest);
    }

    public void saveEntityAddress(EntityAddressDTO entityAddressDTO){
        EntityAddress entityAddress = EntityAddress.createEntityAddress(entityAddressDTO, addressTypeService.createAddressTypeById(entityAddressDTO.getAddressTypeId()));
        logger.info("saveEntityAddress: " +  entityAddress);
        entityAddressRepository.saveAndFlush(entityAddress);
    }

    public Integer deleteEntityAddressById(Integer id){
        logger.info("deleteEntityAddressById: " +  id);

        return entityAddressRepository.deleteEntityAddressesByEntityAddressId(id);
    }

}
