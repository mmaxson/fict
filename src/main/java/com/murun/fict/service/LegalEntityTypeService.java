package com.murun.fict.service;

import com.murun.fict.repository.LegalEntityTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class LegalEntityTypeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    LegalEntityTypeRepository legalEntityTypeRepository;


    private Map<String, Integer> legalEntityTypeReverseLookup = new HashMap<>();

    @PostConstruct
    protected void initialize(){
        legalEntityTypeRepository.findAll().forEach( x -> legalEntityTypeReverseLookup.put(x.getLegalEntityTypeText().toLowerCase(), x.getLegalEntityTypeId()));
    }



    public boolean isValidLegalEntityType(String legalEntityTypeText){
        return legalEntityTypeReverseLookup.get(legalEntityTypeText.toLowerCase()) != null;
    }

    public Integer getLegalEntityTypeId(String legalEntityTypeText){
        return legalEntityTypeReverseLookup.get(legalEntityTypeText.toLowerCase());
    }

}
