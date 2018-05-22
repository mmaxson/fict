package com.murun.fict;

import com.murun.fict.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static com.github.tomakehurst.wiremock.client.WireMock.*;


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

    public static AddressType createAddressType(AddressTypeTestEnum addressTypeTestEnum){
        AddressType retVal = new AddressType();
        retVal.setAddressTypeId(addressTypeTestEnum.addressTypeId());
        retVal.setAddressTypeText(addressTypeTestEnum.addressTypeText());
        return retVal;
    }

    public static AddressType createWorkAddressType(){
        return createAddressType(AddressTypeTestEnum.WORK);
    }

    public static AddressType createMailAddressType(){
        return createAddressType(AddressTypeTestEnum.MAIL);
    }

    public static AddressType createResidenceAddressType(){
        return createAddressType(AddressTypeTestEnum.RESIDENCE);
    }



    public static Address createAddress(Integer addressId, String city, String state, String zip){
        Address retVal = createAddress(city, state, zip);
        retVal.setAddressId(addressId);
        return retVal;
    }

    public static Address createAddress(String city, String state, String zip){
        Address retVal = new Address();
        retVal.setStreet( city + " " + state + " " + zip );
        retVal.setCity(city);
        retVal.setState(state);
        retVal.setZipCode(zip);
        return retVal;
    }

    public static EntityAddress createEntityAddress(int entityAddressId, LegalEntity legalEntity, Address address, AddressType addressType ){
        EntityAddress retVal = createEntityAddress(legalEntity, address, addressType);
        retVal.setEntityAddressId(entityAddressId);
        return retVal;
    }

    public static EntityAddress createEntityAddress(LegalEntity legalEntity, Address address, AddressType addressType ){
        EntityAddress retVal = new EntityAddress();
        retVal.setAddressType(addressType);
        retVal.setLegalEntity(legalEntity);
        retVal.setAddress(address);

        if ( legalEntity.getEntityAddresses() == null ) {
            legalEntity.setEntityAddresses(new HashSet<EntityAddress>());
        }
        legalEntity.getEntityAddresses().add(retVal);
        return retVal;
    }


    public static EntityAddress createResidenceEntityAddr(int entityAddressId, LegalEntity legalEntity, int addressId, String street, String state, String zipCode ){
        return createEntityAddress(entityAddressId, legalEntity, createAddress(addressId, street, state, zipCode), createAddressType(AddressTypeTestEnum.RESIDENCE));
    }

    public static EntityAddress createResidenceEntityAddr(LegalEntity legalEntity, int addressId, String street, String state, String zipCode ){
        return createEntityAddress(legalEntity, createAddress(addressId, street, state, zipCode), createAddressType(AddressTypeTestEnum.RESIDENCE));
    }

    public static EntityAddress createMailingEntityAddr(LegalEntity legalEntity, int addressId, String street, String state, String zipCode ){
        return createEntityAddress(legalEntity, createAddress(addressId, street, state, zipCode), createAddressType(AddressTypeTestEnum.MAIL));
    }

    public static EntityAddress createMailingEntityAddr(int entityAddressId, LegalEntity legalEntity, int addressId, String street, String state, String zipCode ){
        return createEntityAddress(entityAddressId, legalEntity, createAddress(addressId, street, state, zipCode), createAddressType(AddressTypeTestEnum.MAIL));
    }

    public static EntityAddress createWorkEntityAddr(LegalEntity legalEntity, int addressId, String street, String state, String zipCode ){
        return createEntityAddress(legalEntity, createAddress(addressId, street, state, zipCode), createAddressType(AddressTypeTestEnum.WORK));
    }

    public static EntityAddress createWorkEntityAddr(int entityAddressId, LegalEntity legalEntity, int addressId, String street, String state, String zipCode ){
        return createEntityAddress(entityAddressId, legalEntity, createAddress(addressId, street, state, zipCode), createAddressType(AddressTypeTestEnum.WORK));
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
        retVal.setEntityNames( new HashSet<EntityName>());
        return retVal;
    }

    public static LegalEntity createCorporateLegalEntity(){
        LegalEntity retVal = new LegalEntity();
        retVal.setLegalEntityType(createLegalEntityType(LegalEntityTypeTestEnum.CORPORATION));
        retVal.setEntityNames( new HashSet<EntityName>());
        return retVal;
    }

    public static LegalEntity createLivingTrustLegalEntity(){
        LegalEntity retVal = new LegalEntity();
        retVal.setLegalEntityType(createLegalEntityType(LegalEntityTypeTestEnum.LIVING_TRUST));
        retVal.setEntityNames( new HashSet<EntityName>());
        return retVal;
    }

    public static Page<LegalEntity> createLegalEntitiesPageMixedEntityType(){

        List<LegalEntity> legalEntities = new ArrayList<>();
        legalEntities.add(TestService.createIndividualLegalEntity());
        legalEntities.add(TestService.createCorporateLegalEntity());
        legalEntities.add(TestService.createLivingTrustLegalEntity());
        return  new PageImpl(legalEntities);
    }

    public static Page<LegalEntity> createLegalEntitiesPageCorporationsOnly(){

        List<LegalEntity> legalEntities = new ArrayList<>();
        legalEntities.add(TestService.createCorporateLegalEntity());
        legalEntities.add(TestService.createCorporateLegalEntity());
        legalEntities.add(TestService.createCorporateLegalEntity());
        return new PageImpl(legalEntities);
    }


    public static void checkMockAuthToken() {

        long unixTimestamp = Instant.now().plusSeconds(10).getEpochSecond();
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(urlPathEqualTo("/murun/auth/oauth/check_token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"aud\":[\"oauth-resource\"],\"exp\":" + unixTimestamp + ",\"user_name\":\"marku\",\"authorities\":[\"ROLE_ADMIN\",\"ROLE_USER\"],\"client_id\":\"trusted-client\",\"scope\":[\"read\",\" write\",\" trust\"]}")));

    }
}


