package com.murun.fict.control;


import com.murun.fict.model.LegalEntityTypeNameType;
import com.murun.fict.repository.LegalEntityTypeNameTypeRepository;
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



@RestController()
@RequestMapping(value="/entityTypeNameTypes",  produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "LegalEntityTypeNameTypeController", description = "LegalEntityTypeNameTypeController")
public class LegalEntityTypeNameTypeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LegalEntityTypeNameTypeRepository legalEntityTypeNameTypeRepository;


    @GetMapping
    public ResponseEntity<List<LegalEntityTypeNameType>> getAllLegalEntityTypeNameTypes() {
        logger.info("getAllLegalEntityTypeNameTypes: ");
        List<LegalEntityTypeNameType> legalEntityTypeNameType = legalEntityTypeNameTypeRepository.findAll();
        return new ResponseEntity<List<LegalEntityTypeNameType>>(legalEntityTypeNameType, new HttpHeaders(), HttpStatus.OK);
    }

}