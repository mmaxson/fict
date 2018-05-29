package com.murun.fict.control;


import com.murun.commonrest.model.SuccessResource;
import com.murun.fict.model.EntityAddress;
import com.murun.fict.dto.EntityAddressDTO;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController()
@RequestMapping(value="/addresses",  produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "EntityAddressController", description = "EntityAddressController")
public class EntityAddressController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EntityAddressService entityAddressService;


    @GetMapping( value = "/id/{id}")
    public ResponseEntity<Page<EntityAddress>> getAddressesByEntityId(@PathVariable("id") Integer id, Pageable pageRequest) {
        if (id <= 0) {
            throw new IllegalArgumentException(id + " is not a valid entity id.");
        }

        Page<EntityAddress> legalEntities = entityAddressService.getEntityAddressesByLegalEntityId(id, pageRequest);
        return new ResponseEntity<Page<EntityAddress>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<SuccessResource> updateEntityAddress(@Valid @NotNull @RequestBody EntityAddressDTO dto) {
        entityAddressService.saveEntityAddress(dto);
        return new ResponseEntity<SuccessResource>(new SuccessResource("0", "Successfully saved."), new HttpHeaders(), HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<SuccessResource> addEntityAddress(@Valid @NotNull @RequestBody EntityAddressDTO dto) {
        entityAddressService.saveEntityAddress(dto);
        return new ResponseEntity<SuccessResource>(new SuccessResource("0", "Successfully created."), new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/id/{id}")
    public ResponseEntity<SuccessResource> deleteEntityAddress(@PathVariable("id") Integer id) {
        Integer retVal = entityAddressService.deleteEntityAddressesByEntityAddressId(id);
        return new ResponseEntity<SuccessResource>(new SuccessResource( retVal.toString(), "Successfully deleted."), new HttpHeaders(), HttpStatus.OK );
    }
}