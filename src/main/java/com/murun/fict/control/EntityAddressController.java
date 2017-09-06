package com.murun.fict.control;


import com.murun.fict.model.EntityAddress;
import com.murun.fict.service.EntityAddressService;
import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController()
@RequestMapping(value="/addresses",  produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "EntityAddressController", description = "EntityAddressController")
@CrossOrigin(origins = "http://localhost:4200")
public class EntityAddressController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EntityAddressService entityAddressService;


    @RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
    public ResponseEntity<Page<EntityAddress>> getAddressesByEntityId(@PathVariable("id") Integer id, Pageable pageRequest) {
        if (id <= 0) {
            throw new IllegalArgumentException(id + " is not a valid entity id.");
        }

        Page<EntityAddress> legalEntities = entityAddressService.getAddressesByEntityId(id, pageRequest);
        return new ResponseEntity<Page<EntityAddress>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }





}