package com.murun.fict.control;


import com.murun.fict.model.LegalEntity;
import com.murun.fict.model.LegalEntityRedis;
import com.murun.fict.service.AddressTypeService;
import com.murun.fict.service.LegalEntityService;
import com.murun.fict.service.LegalEntityTypeService;
import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController()
@RequestMapping(value = "/entities", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "LegalEntityController", description = "LegalEntityController")
public class LegalEntityController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LegalEntityService legalEntityService;

    @Resource
    private LegalEntityTypeService legalEntityTypeService;

    @Resource
    private AddressTypeService addressTypeService;

    @GetMapping(value = "")
    public ResponseEntity<Page<LegalEntityRedis>> getEntities(@RequestParam(value = "entity_type", required = true) String legalEntityTypeText,
                                                              Pageable pageRequest) {

        if (!legalEntityTypeService.isValidLegalEntityType(legalEntityTypeText)) {
            throw new IllegalArgumentException(legalEntityTypeText + " is not a valid entity type.");
        }

        Page<LegalEntityRedis> legalEntities = legalEntityService.getAllEntitiesFilterByEntityType(legalEntityTypeText, pageRequest);
        return new ResponseEntity<Page<LegalEntityRedis>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping(value = "/id/{id}")
    public ResponseEntity<LegalEntity> getEntityById(@PathVariable("id") Integer id) {

        if (id <= 0) {
            throw new IllegalArgumentException(id + " is not a valid entity id.");
        }
        return new ResponseEntity<LegalEntity>(legalEntityService.getEntityById(id), new HttpHeaders(), HttpStatus.OK);
    }

// TODO fix
   /* @RequestMapping(method = RequestMethod.GET, value = "/state/{state}")
    public ResponseEntity<Page<LegalEntity>> getEntitiesWithAddressesInState(@PathVariable("state") String state,
                                                                             @RequestParam(value = "entity_type", required = false) String legalEntityTypeText,
                                                                             Pageable pageRequest ) {
        // TODO fix
        Page<LegalEntity> legalEntities;
        if (legalEntityTypeText != null) {
            if (!legalEntityTypeService.isValidLegalEntityType(legalEntityTypeText)) {
                throw new IllegalArgumentException(legalEntityTypeText + " is not a valid entity type.");
            }

        }

        legalEntities = legalEntityService.getEntitiesWithAddressesInState(state, pageRequest);

        return new ResponseEntity<Page<LegalEntity>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }

*/
 /*   @RequestMapping(method = RequestMethod.GET, value = "/city/{city}")
    public ResponseEntity<Page<LegalEntity>> getEntitiesWithAddressesInCity(@PathVariable("city") String city,
                                                                            @RequestParam(value = "entity_type", required = false) String legalEntityTypeText,
                                                                            Pageable pageRequest) {
        // TODO fix
        Page<LegalEntity> legalEntities;
        if (legalEntityTypeText != null) {
            if (!legalEntityTypeService.isValidLegalEntityType(legalEntityTypeText)) {
                throw new IllegalArgumentException(legalEntityTypeText + " is not a valid entity type.");
            }
        }
        legalEntities = legalEntityService.getEntitiesWithAddressesInCity(city, pageRequest);
        return new ResponseEntity<Page<LegalEntity>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/address-type/{addressTypeText}")
    public ResponseEntity<Page<LegalEntity>> getEntitiesWithAddressesWithAddressType(@PathVariable("addressTypeText") String addressTypeText,
                                                                                     Pageable pageRequest) {

        Page<LegalEntity> legalEntities;

        if (!addressTypeService.isValidAddressType(addressTypeText)) {
            throw new IllegalArgumentException(addressTypeText + " is not a valid address type.");
        }

        legalEntities = legalEntityService.getAllEntitiesWithAddressesWithAddressType(addressTypeText, pageRequest);


        return new ResponseEntity<Page<LegalEntity>>(legalEntities, new HttpHeaders(), HttpStatus.OK);
    }
*/

}