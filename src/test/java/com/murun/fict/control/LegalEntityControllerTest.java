package com.murun.fict.control;


import com.murun.fict.TestService;
import com.murun.fict.main.ApplicationConfig;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.service.AddressTypeService;
import com.murun.fict.service.LegalEntityService;
import com.murun.fict.service.LegalEntityTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.murun.fict.TestService.createMailingAddrForLegalEntity;
import static com.murun.fict.TestService.createResidenceAddrForLegalEntity;
import static com.murun.fict.TestService.createWorkAddrForLegalEntity;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfig.class)
//@WebMvcTest(LegalEntityControllerTest.class)  // cannot be used with SpringBootTest

@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class LegalEntityControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(LegalEntityControllerTest.class);


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    LegalEntityService legalEntityService;

    @MockBean
    LegalEntityTypeService legalEntityTypeService;

    @MockBean
    AddressTypeService addressTypeService;


    @Test
    public void testGetAllEntities() throws Exception {

        List<LegalEntity> legalEntities = new ArrayList<>();
        legalEntities.add(TestService.createIndividualLegalEntity());
        legalEntities.add(TestService.createCorporateLegalEntity());
        legalEntities.add(TestService.createLivingTrustLegalEntity());


        given(legalEntityService.getAllLegalEntities()).willReturn(legalEntities);

        mockMvc.perform( get("/entities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].legalEntityType.legalEntityTypeText", is("Individual")) )
                .andExpect(jsonPath("$[1].legalEntityType.legalEntityTypeText", is("Corporation")) )
                .andExpect(jsonPath("$[2].legalEntityType.legalEntityTypeText", is("Living Trust")) );
    }


    @Test
    public void shouldReturnBadRequestForBadEntityType() throws Exception {

        List<LegalEntity> legalEntities = new ArrayList<>();
        legalEntities.add(TestService.createIndividualLegalEntity());
        legalEntities.add(TestService.createCorporateLegalEntity());
        legalEntities.add(TestService.createLivingTrustLegalEntity());


        given(legalEntityTypeService.isValidLegalEntityType("ZZZ")).willReturn(false);
        given(legalEntityService.getAllLegalEntities()).willReturn(legalEntities);

        mockMvc.perform( get("/entities?entity_type='ZZZ'"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCorporationsOnly() throws Exception {

        List<LegalEntity> legalEntities = new ArrayList<>();
        legalEntities.add(TestService.createCorporateLegalEntity());


        given(legalEntityTypeService.isValidLegalEntityType("Corporation")).willReturn(true);
        given(legalEntityService.getAllEntitiesFilterByEntityType("Corporation")).willReturn(legalEntities);

        mockMvc.perform( get("/entities?entity_type=Corporation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].legalEntityType.legalEntityTypeText", is("Corporation")) );
    }


    @Test
    public void shouldGetById() throws Exception {

        LegalEntity legalEntity = TestService.createCorporateLegalEntity();
        legalEntity.setLegalEntityId(1);

        given(legalEntityService.getEntityById(1)).willReturn(legalEntity);

        mockMvc.perform( get("/entities/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legalEntityId", is(1)) );
    }


    @Test
    public void shouldGetByCity() throws Exception {

        List<LegalEntity> legalEntityList = new ArrayList<>();
        legalEntityList.add(TestService.createCorporateLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());

        legalEntityList.get(0).setEntityAddresses(new HashSet<>());
        legalEntityList.get(0).getEntityAddresses().add((createResidenceAddrForLegalEntity(legalEntityList.get(0), "Santa Monica", "CA", "90402")));
        legalEntityList.get(0).getEntityAddresses().add((createMailingAddrForLegalEntity(legalEntityList.get(0), "Westwood", "AZ", "90402")));


        given(legalEntityService.getEntitiesWithAddressesInCity("Westwood")).willReturn(legalEntityList);

        mockMvc.perform( get("/entities/city/Westwood"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldGetByState() throws Exception {

        List<LegalEntity> legalEntityList = new ArrayList<>();
        legalEntityList.add(TestService.createCorporateLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());

        legalEntityList.get(0).setEntityAddresses(new HashSet<>());
        legalEntityList.get(0).getEntityAddresses().add((createResidenceAddrForLegalEntity(legalEntityList.get(0), "Santa Monica", "CA", "90402")));
        legalEntityList.get(0).getEntityAddresses().add((createMailingAddrForLegalEntity(legalEntityList.get(0), "Westwood", "AZ", "90402")));


        given(legalEntityService.getEntitiesWithAddressesInState("CA")).willReturn(legalEntityList);

        mockMvc.perform( get("/entities/state/CA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldGetByEntityType() throws Exception {

        List<LegalEntity> legalEntityList = new ArrayList<>();
        legalEntityList.add(TestService.createCorporateLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());

        legalEntityList.get(0).setEntityAddresses(new HashSet<>());
        legalEntityList.get(0).getEntityAddresses().add((createResidenceAddrForLegalEntity(legalEntityList.get(0), "Santa Monica", "CA", "90402")));
        legalEntityList.get(0).getEntityAddresses().add((createMailingAddrForLegalEntity(legalEntityList.get(0), "Westwood", "AZ", "90402")));

        given(addressTypeService.isValidAddressType(TestService.AddressTypeTestEnum.RESIDENCE.addressTypeText())).willReturn(true);
        given(legalEntityService.getAllEntitiesWithAddressesWithAddressType(TestService.AddressTypeTestEnum.RESIDENCE.addressTypeText())).willReturn(legalEntityList);

        mockMvc.perform( get("/entities/address-type/Residence"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldReturnBadRequestForBadAddressType() throws Exception {
        List<LegalEntity> legalEntityList = new ArrayList<>();
        legalEntityList.add(TestService.createCorporateLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());

        legalEntityList.get(0).setEntityAddresses(new HashSet<>());
        legalEntityList.get(0).getEntityAddresses().add((createResidenceAddrForLegalEntity(legalEntityList.get(0), "Santa Monica", "CA", "90402")));
        legalEntityList.get(0).getEntityAddresses().add((createMailingAddrForLegalEntity(legalEntityList.get(0), "Westwood", "AZ", "90402")));

        given(addressTypeService.isValidAddressType(TestService.AddressTypeTestEnum.RESIDENCE.addressTypeText())).willReturn(false);
        mockMvc.perform( get("/entities/address-type/Residence"))
                .andExpect(status().isBadRequest());

    }


}

