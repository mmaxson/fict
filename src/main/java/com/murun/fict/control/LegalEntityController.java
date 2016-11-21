package com.murun.fict.control;


import com.murun.fict.model.EntityName;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.service.LegalEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController()
@RequestMapping("/entities")
public class LegalEntityController {

    private static final Logger logger = LoggerFactory.getLogger(LegalEntityController.class);

    @Resource
    private LegalEntityService legalEntityService;



    @RequestMapping(method = RequestMethod.GET, value = "", produces = "application/json")
    public ResponseEntity<List<LegalEntity>> getAllEntities() {
        logger.info("getAllEntities================================ ");

        List<LegalEntity> legalEntities = legalEntityService.getAllLegalEntities();
        return new ResponseEntity<List<LegalEntity>>(legalEntities, new HttpHeaders(), HttpStatus.OK);

    }


    /*@RequestMapping(method = RequestMethod.GET, value = "", produces = "application/json")
    public ResponseEntity<List<LegalEntity>> getAllEntities() {
        logger.info("getAllEntities================================ ");

        List<LegalEntity> legalEntities = legalEntityService.getAllLegalEntities();
        return new ResponseEntity<List<LegalEntity>>(legalEntities, new HttpHeaders(), HttpStatus.OK);

    }*/
}