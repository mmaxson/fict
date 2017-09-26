package com.murun.fict.service;

import com.murun.fict.repository.AddressTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import com.murun.fict.model.AddressType;


@Service
@Transactional
public class AddressTypeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    AddressTypeRepository addressTypeRepository;


    private final Map<String, Integer> addressTypeReverseLookup = new HashMap<>();
    private final Map<Integer, String> addressTypes = new HashMap<>();

    @PostConstruct
    protected void initialize(){
        addressTypeRepository.findAll().forEach( x -> addressTypeReverseLookup.put(x.getAddressTypeText().toLowerCase(), x.getAddressTypeId()));
        addressTypeRepository.findAll().forEach( x -> addressTypes.put(x.getAddressTypeId(),x.getAddressTypeText()));
    }



    public boolean isValidAddressType(String addressTypeText){
        return addressTypeReverseLookup.get(addressTypeText.toLowerCase()) != null;
    }

    public Integer getAddressTypeId(String addressTypeText){
        return addressTypeReverseLookup.get(addressTypeText.toLowerCase());
    }

    public String getAddressTypeText(Integer addressTypeId){
        return addressTypes.get(addressTypeId);
    }

    public AddressType createAddressTypeById ( Integer addressTypeId ){
        return new AddressType( addressTypeId, getAddressTypeText(addressTypeId));
    }
}
