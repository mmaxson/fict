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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
@Transactional
public class LegalEntityService {

    private static final Logger logger = LoggerFactory.getLogger(LegalEntityService.class);

    @Resource
    LegalEntityRepository legalEntityRepository;

    @Resource
    LegalEntityTypeRepository legalEntityTypeRepository;


    private Map<String, Integer> legalEntityTypeReverseLookup = new HashMap<>();

    @PostConstruct
    private void initialize(){

        for(LegalEntityType legalEntityType:legalEntityTypeRepository.findAll()){
            legalEntityTypeReverseLookup.put(legalEntityType.getLegalEntityTypeText(),legalEntityType.getLegalEntityTypeId());
        }
    }



    public List<LegalEntity> getAllLegalEntities(){
        logger.info("getAllEntities");
        List<LegalEntity> legalEntities = new ArrayList<LegalEntity>();

        for(LegalEntity le:legalEntityRepository.findAll()){
            logger.info(le.toString());
            legalEntities.add(le);
        }
        return legalEntities;
    }


    public List<LegalEntity> getAllEntitiesFilterByEntityType( String onlyLegalEntityTypeText){
        logger.info("getAllEntitiesFilterByEntityType");
        LegalEntity legalEntity = new LegalEntity();
        LegalEntityType legalEntityType =  new LegalEntityType();
        legalEntityType.setLegalEntityTypeId(legalEntityTypeReverseLookup.get(onlyLegalEntityTypeText));
        legalEntityType.setLegalEntityTypeText(onlyLegalEntityTypeText);
        legalEntity.setLegalEntityType(legalEntityType);

        Example<LegalEntity> example = Example.of(legalEntity);

        List<LegalEntity> legalEntities = new ArrayList<LegalEntity>();
        for(LegalEntity le:legalEntityRepository.findAll(example)){
            logger.info(le.toString());
            legalEntities.add(le);
        }
        return legalEntities;
    }

   /* public AddressList getByState(String state) {

        AddressList retVal = new AddressList();


        retVal.setAll( ();
        if (retVal.size() != 0) {
            logger.info("Return from cache.");

        } else {

            retVal.setAll(addressRepository.getAddressByState(state));
            logger.info("Return from db.");
            Consumer<String> c = (x) -> jedis.lpush(state, SerializationUtils.serialize(x));
            retVal.getAll().forEach( address -> c.accept(address) );
            logger.info("Add to cache.");

        }

        return retVal;
    }



	public Optional<Address> getById(int id){
        return Optional.of(addressRepository.findOne(id));
	}

	public int updateAddress( Address address ){
        Address ret = addressRepository.save( address );
		return ret.getLegalEntityId();
	}

    public int createAddress( Address address ){
        Address ret = addressRepository.save( address );
        return ret.getLegalEntityId();
    }*/


}
