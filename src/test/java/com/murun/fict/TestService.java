package com.murun.fict;

import com.murun.fict.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.aspectj.bridge.Version.text;

public class TestService {


    public enum LegalEntityTypeTestEnum {
        INDIVIDUAL (1,"Individual"),
        CORPORATION (2,"Corporation"),
        LIVING_TRUST (3,"Living Trust");

        private final Integer entityTypeId;
        private final String entityTypeText;

        LegalEntityTypeTestEnum(int entityTypeId, String entityTypeText) {
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

    public static LegalEntityType createLegalEntityType( LegalEntityTypeTestEnum legalEntityTypeTestEnum){
        LegalEntityType retVal = new LegalEntityType();
        retVal.setLegalEntityTypeId(legalEntityTypeTestEnum.entityTypeId());
        retVal.setLegalEntityTypeText(legalEntityTypeTestEnum.entityTypeText());
        return retVal;
    }

    public static EntityName createEntityName(NameType nameType, String name, LegalEntity legalEntity){
        EntityName retVal = new EntityName();
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

    public static Address createAddress(String label){
        Address retVal = new Address();
        retVal.setStreet("street " + label);
        retVal.setCity("city " + label);
        retVal.setState("CA");
        retVal.setZipCode("zip " + label);
        return retVal;
    }

    public static EntityAddress createEntityAddress(LegalEntity legalEntity, String label, AddressType addrreessType ){
        EntityAddress retVal = new EntityAddress();
        retVal.setLegalEntity(legalEntity);
        retVal.setAddress(createAddress(label));
        retVal.setAddressType(addrreessType);

        return retVal;
    }


    public static Set<EntityAddress> createEntityAddressesForLegalEntity(LegalEntity legalEntity ){
        Set<EntityAddress> retVal = new HashSet<>();
        retVal.add(createEntityAddress(legalEntity,legalEntity.getLegalEntityId()+"A", createAddressType(AddressTypeTestEnum.RESIDENCE)));
        retVal.add(createEntityAddress(legalEntity,legalEntity.getLegalEntityId()+"B", createAddressType(AddressTypeTestEnum.RESIDENCE)));
        retVal.add(createEntityAddress(legalEntity,legalEntity.getLegalEntityId()+"C", createAddressType(AddressTypeTestEnum.WORK)));
        retVal.add(createEntityAddress(legalEntity,legalEntity.getLegalEntityId()+"D", createAddressType(AddressTypeTestEnum.MAIL)));

        return retVal;
    }

    public static Set<EntityName> createEntityNamesForIndividualLegalEntity(LegalEntity legalEntity, String nameLabel ){

        Set<EntityName> retVal = new HashSet<>();

        retVal.add(createEntityName(createNameType(NameTypeTestEnum.NAME_FIRST),"First Name"+legalEntity.getLegalEntityId(),legalEntity));
        retVal.add(createEntityName(createNameType(NameTypeTestEnum.NAME_LAST),"Last Name"+nameLabel,legalEntity));
        retVal.add(createEntityName(createNameType(NameTypeTestEnum.NAME_MIDDLE),"Middle Name"+legalEntity.getLegalEntityId(),legalEntity));

        return retVal;
    }

    public static Set<EntityName> createEntityNamesForCorporateLegalEntity(LegalEntity legalEntity, String nameLabel){
        Set<EntityName> retVal = new HashSet<>();
        retVal.add(createEntityName(createNameType(NameTypeTestEnum.NAME_ORG),"Organization Name"+nameLabel,legalEntity));

        return retVal;
    }

    public static Set<EntityName> createEntityNamesForLivingTrustLegalEntity(LegalEntity legalEntity, String nameLabel){
        Set<EntityName> retVal = new HashSet<>();
        retVal.add(createEntityName(createNameType(NameTypeTestEnum.NAME_TITLE),"Living Trust Name"+nameLabel,legalEntity));

        return retVal;
    }

    public static LegalEntity createIndividualLegalEntity(){
        LegalEntity retVal = new LegalEntity();
        retVal.setLegalEntityType(createLegalEntityType(LegalEntityTypeTestEnum.INDIVIDUAL));
        retVal.setEntityNames(createEntityNamesForIndividualLegalEntity(retVal, "A"));
        retVal.setEntityAddresses(createEntityAddressesForLegalEntity(retVal));

        return retVal;
    }

    public static LegalEntity createCorporateLegalEntity(){
        LegalEntity retVal = new LegalEntity();
        retVal.setLegalEntityType(createLegalEntityType(LegalEntityTypeTestEnum.CORPORATION));
        retVal.setEntityNames(createEntityNamesForCorporateLegalEntity(retVal, "B" ));
        retVal.setEntityAddresses(createEntityAddressesForLegalEntity(retVal));

        return retVal;
    }

    public static LegalEntity createLivingTrustLegalEntity(){
        LegalEntity retVal = new LegalEntity();
        retVal.setLegalEntityType(createLegalEntityType(LegalEntityTypeTestEnum.LIVING_TRUST));
        retVal.setEntityNames(createEntityNamesForLivingTrustLegalEntity(retVal, "C"));
        retVal.setEntityAddresses(createEntityAddressesForLegalEntity(retVal));

        return retVal;
    }

    public static List<LegalEntity> createLegalEntityList(){

        List<LegalEntity> retVal = new ArrayList<>();
        retVal.add(createIndividualLegalEntity());
        retVal.add(createCorporateLegalEntity());
        retVal.add(createLivingTrustLegalEntity());

        return retVal;
    }
}


