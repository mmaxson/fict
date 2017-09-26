package com.murun.fict.control;


import com.murun.fict.model.LegalEntityType;
import com.murun.fict.repository.LegalEntityTypeRepository;
import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController()
@RequestMapping(value="/entity-types",  produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "LegalEntityTypeController", description = "LegalEntityTypeController")
public class LegalEntityTypeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LegalEntityTypeRepository legalEntityTypeRepository;



    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<LegalEntityType>> getAllEntityTypes() {

        List<LegalEntityType> legalEntityTypes = legalEntityTypeRepository.findAll();
        return new ResponseEntity<List<LegalEntityType>>(legalEntityTypes, new HttpHeaders(), HttpStatus.OK);
    }



}