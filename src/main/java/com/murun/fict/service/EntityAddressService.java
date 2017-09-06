package com.murun.fict.service;

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

    public Page<EntityAddress> getAddressesByEntityId(Integer legalEntityId, Pageable pageRequest) {
        logger.info("getAddressesByEntityId: " + pageRequest.getPageNumber() + ' ' + pageRequest.getPageSize());
        return entityAddressRepository.getAddressesByEntityId(legalEntityId, pageRequest);
    }


}
