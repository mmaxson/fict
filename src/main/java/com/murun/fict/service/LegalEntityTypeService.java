package com.murun.fict.service;

import com.murun.fict.model.LegalEntity;
import com.murun.fict.model.LegalEntityType;
import com.murun.fict.repository.LegalEntityRepository;
import com.murun.fict.repository.LegalEntityTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;


@Service
@Transactional
public class LegalEntityTypeService {

    private static final Logger logger = LoggerFactory.getLogger(LegalEntityTypeService.class);

    private static LegalEntityTypeService instance = new LegalEntityTypeService();
    
    public static LegalEntityTypeService getInstance(){
        return instance;
    }

    @Resource
    LegalEntityTypeRepository legalEntityTypeRepository;


    private Map<String, Integer> legalEntityTypeReverseLookup = new HashMap<>();

    @PostConstruct
    private void initialize(){

        for(LegalEntityType legalEntityType:legalEntityTypeRepository.findAll()){
            legalEntityTypeReverseLookup.put(legalEntityType.getLegalEntityTypeText(),legalEntityType.getLegalEntityTypeId());
        }
    }



    public boolean isValidLegalEntityType(String legalEntityTypeText){
        return legalEntityTypeReverseLookup.get(legalEntityTypeText) != null;
    }

    public Integer getLegalEntityTypeId(String legalEntityTypeText){
        return legalEntityTypeReverseLookup.get(legalEntityTypeText);
    }








}
