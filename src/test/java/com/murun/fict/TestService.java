package com.murun.fict;

import com.murun.fict.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestService {


    public enum LegalEntityTypeTestEnum {
        INDIVIDUAL (1,"Individual"),
        CORPORATION (2,"Corporation"),
        LIVING_TRUST (3,"Living Trust");

        private final Integer entityTypeId;
        private final String entityTypeText;

        LegalEntityTypeTestEnum(Integer entityTypeId, String entityTypeText) {
            this.entityTypeId=entityTypeId;
            this.entityTypeText=entityTypeText;
        }

        public Integer entityTypeId(){
            return entityTypeId;
        }

        public String entityTypeText(){
            return entityTypeText;
        }
    }

    public enum AddressTypeTestEnum {
        RESIDENCE (1,"Residence"),
        WORK (2,"Work"),
        MAIL (3,"Mail");

        private final Integer addressTypeId;
        private final String addressTypeText;

        AddressTypeTestEnum(int addressTypeId, String addressTypeText) {
            this.addressTypeId=addressTypeId;
            this.addressTypeText=addressTypeText;
        }

        public Integer addressTypeId(){
            return addressTypeId;
        }

        public String addressTypeText(){
            return addressTypeText;
        }
    }



    public enum NameTypeTestEnum {
        NAME_FIRST (1,"First"),
        NAME_LAST(2,"Last"),
        NAME_MIDDLE (3,"Middle"),
        NAME_ORG (4,"Organization Name"),
        NAME_TITLE (5,"Title");

        private final Integer nameTypeId;
        private final String nameTypeText;

        NameTypeTestEnum(int nameTypeId, String nameTypeText) {
            this.nameTypeId=nameTypeId;
            this.nameTypeText=nameTypeText;
        }

        public Integer nameTypeId(){
            return nameTypeId;
        }

        public String nameTypeText(){
            return nameTypeText;
        }
    }

    public static LegalEntityType createLegalEntityType(LegalEntityTypeTestEnum legalEntityTypeTestEnum){
        LegalEntityType retVal = new LegalEntityType();
        retVal.setLegalEntityTypeId(legalEntityTypeTestEnum.entityTypeId());
        retVal.setLegalEntityTypeText(legalEntityTypeTestEnum.entityTypeText());
        return retVal;
    }

    public static EntityName createEntityName(Integer id, NameType nameType, String name, LegalEntity legalEntity){
        EntityName retVal = new EntityName();
        retVal.setEntityNameId(id);
        retVal.setNameType(nameType);
        retVal.setName(name);
        retVal.setLegalEntity(legalEntity);
        return retVal;
    }

    public static NameType createNameType(NameTypeTestEnum nameTypeTestEnum){
        NameType retVal = new NameType();
        retVal.setNameTypeId(nameTypeTestEnum.nameTypeId());
        retVal.setNameTypeText(nameTypeTestEnum.nameTypeText());
        return retVal;
    }

    public static AddressType createAddressType(AddressTypeTestEnum addressType){
        AddressType retVal = new AddressType();
        retVal.setAddressTypeId(addressType.addressTypeId());
        retVal.setAddressTypeText(addressType.addressTypeText());
        return retVal;
    }

    public static Address createAddress(String city, String state, String zipCode){
        Address retVal = new Address();
        retVal.setStreet( city + " " + state + " " + zipCode );
        retVal.setCity(city);
        retVal.setState(state);
        retVal.setZipCode(zipCode);
        return retVal;
    }

    public static EntityAddress createEntityAddress(LegalEntity legalEntity, Address address, AddressType addressType ){

        EntityAddress retVal = new EntityAddress();
        retVal.setAddressType(addressType);
        retVal.setLegalEntity(legalEntity);
        retVal.setAddress(address);

        return retVal;
    }


    public static EntityAddress createResidenceAddrForLegalEntity(LegalEntity legalEntity, String street, String state, String zipCode ){
        return createEntityAddress(legalEntity, createAddress(street, state, zipCode), createAddressType(AddressTypeTestEnum.RESIDENCE));
    }

    public static EntityAddress createMailingAddrForLegalEntity(LegalEntity legalEntity, String street, String state, String zipCode ){
        return createEntityAddress(legalEntity, createAddress(street, state, zipCode), createAddressType(AddressTypeTestEnum.MAIL));
    }

    public static EntityAddress createWorkAddrForLegalEntity(LegalEntity legalEntity, String street, String state, String zipCode ){
        return createEntityAddress(legalEntity, createAddress(street, state, zipCode), createAddressType(AddressTypeTestEnum.WORK));
    }

    public static Set<EntityName> createEntityNamesForCorporateLegalEntity(LegalEntity legalEntity, String nameLabel){
        Set<EntityName> retVal = new HashSet<>();
        retVal.add(createEntityName(1, createNameType(NameTypeTestEnum.NAME_ORG),"Organization Name"+nameLabel,legalEntity));

        return retVal;
    }

    public static Set<EntityName> createEntityNamesForLivingTrustLegalEntity(LegalEntity legalEntity, String nameLabel){
        Set<EntityName> retVal = new HashSet<>();
        retVal.add(createEntityName(1, createNameType(NameTypeTestEnum.NAME_TITLE),"Living Trust Name"+nameLabel,legalEntity));

        return retVal;
    }

    public static LegalEntity createIndividualLegalEntity(){
        LegalEntity retVal = new LegalEntity();
        retVal.setLegalEntityType(createLegalEntityType(LegalEntityTypeTestEnum.INDIVIDUAL));
        return retVal;
    }

    public static LegalEntity createCorporateLegalEntity(){
        LegalEntity retVal = new LegalEntity();
        retVal.setLegalEntityType(createLegalEntityType(LegalEntityTypeTestEnum.CORPORATION));
        return retVal;
    }

    public static LegalEntity createLivingTrustLegalEntity(){
        LegalEntity retVal = new LegalEntity();
        retVal.setLegalEntityType(createLegalEntityType(LegalEntityTypeTestEnum.LIVING_TRUST));
        return retVal;
    }

}


