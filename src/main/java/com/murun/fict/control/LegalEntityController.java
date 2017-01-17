package com.murun.fict.control;


import com.murun.fict.model.LegalEntity;
import com.murun.fict.service.AddressTypeService;
import com.murun.fict.service.LegalEntityService;
import com.murun.fict.service.LegalEntityTypeService;
import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping(value="/entities",  produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "LegalEntityController", description = "LegalEntityController")
public class LegalEntityController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LegalEntityService legalEntityService;

    @Resource
    private LegalEntityTypeService legalEntityTypeService;

    @Resource
    private AddressTypeService addressTypeService;


    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<LegalEntity>> getAllEntities(@RequestParam(value = "entity_type", required = false) String legalEntityTypeText) {

        List<LegalEntity> legalEntities;
        if (legalEntityTypeText != null) {
            if (!legalEntityTypeService.isValidLegalEntityType(legalEntityTypeText)) {
                throw new IllegalArgumentException(legalEntityTypeText + " is not a valid entity type.");
            }

            legalEntities = legalEntityService.getAllEntitiesFilterByEntityType(legalEntityTypeText);

        } else {
            legalEntities = legalEntityService.getAllLegalEntities();
        }
        return new ResponseEntity<List<LegalEntity>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
    public ResponseEntity<LegalEntity> getEntityById(@PathVariable("id") Integer id) {

        if (id <= 0) {
            throw new IllegalArgumentException(id + " is not a valid entity id.");
        }
        LegalEntity legalEntity =  new LegalEntity();
        Optional<LegalEntity> result = legalEntityService.getEntityById(id);
        if ( result.isPresent() ) {
            legalEntity = result.get();
        }

        return new ResponseEntity<LegalEntity>(legalEntity, new HttpHeaders(), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/state/{state}")
    public ResponseEntity<List<LegalEntity>> getEntitiesWithAddressesInState(@PathVariable("state") String state,
                                                                             @RequestParam(value = "entity_type", required = false) String legalEntityTypeText) {

        List<LegalEntity> legalEntities;
        if (legalEntityTypeText != null) {
            if (!legalEntityTypeService.isValidLegalEntityType(legalEntityTypeText)) {
                throw new IllegalArgumentException(legalEntityTypeText + " is not a valid entity type.");
            }

        }

        legalEntities = legalEntityService.getEntitiesWithAddressesInState(state);

        return new ResponseEntity<List<LegalEntity>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/city/{city}")
    public ResponseEntity<List<LegalEntity>> getEntitiesWithAddressesInCity(@PathVariable("city") String city,
                                                                            @RequestParam(value = "entity_type", required = false) String legalEntityTypeText) {

        List<LegalEntity> legalEntities;
        if (legalEntityTypeText != null) {
            if (!legalEntityTypeService.isValidLegalEntityType(legalEntityTypeText)) {
                throw new IllegalArgumentException(legalEntityTypeText + " is not a valid entity type.");
            }
        }
        legalEntities = legalEntityService.getEntitiesWithAddressesInCity(city);
        return new ResponseEntity<List<LegalEntity>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/address-type/{addressTypeText}")
    public ResponseEntity<List<LegalEntity>> getEntitiesWithAddressesWithAddressType(@PathVariable("addressTypeText") String addressTypeText) {

        List<LegalEntity> legalEntities;

        if (!addressTypeService.isValidAddressType(addressTypeText)) {
            throw new IllegalArgumentException(addressTypeText + " is not a valid address type.");
        }

        legalEntities = legalEntityService.getAllEntitiesWithAddressesWithAddressType(addressTypeText);


        return new ResponseEntity<List<LegalEntity>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }
}