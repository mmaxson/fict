package com.murun.fict.control;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.murun.fict.TestService;
import com.murun.fict.main.ApplicationConfiguration;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.service.AddressTypeService;
import com.murun.fict.service.LegalEntityService;
import com.murun.fict.service.LegalEntityTypeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.murun.fict.TestService.createMailingAddrForLegalEntity;
import static com.murun.fict.TestService.createResidenceAddrForLegalEntity;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= {ApplicationConfiguration.class})
//@WebMvcTest(LegalEntityControllerTest.class)  // cannot be used with SpringBootTest

@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class LegalEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    LegalEntityService legalEntityService;

    @MockBean
    LegalEntityTypeService legalEntityTypeService;

    @MockBean
    AddressTypeService addressTypeService;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9001);


    @Before
    public void preSetup() throws Exception {
        long unixTimestamp = Instant.now().plusSeconds(10).getEpochSecond();
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(urlPathEqualTo("/murun/auth/oauth/check_token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"aud\":[\"oauth-resource\"],\"exp\":" + unixTimestamp +  ",\"user_name\":\"marku\",\"authorities\":[\"ROLE_ADMIN\",\"ROLE_USER\"],\"client_id\":\"trusted-client\",\"scope\":[\"read\",\" write\",\" trust\"]}")));

    }


    @After
    public void tearDown() {

    }

    @Test
    public void testGetAllEntities() throws Exception {

        List<LegalEntity> legalEntities = new ArrayList<>();
        legalEntities.add(TestService.createIndividualLegalEntity());
        legalEntities.add(TestService.createCorporateLegalEntity());
        legalEntities.add(TestService.createLivingTrustLegalEntity());


        given(legalEntityService.getAllLegalEntities()).willReturn(legalEntities);

        mockMvc.perform( get( "/entities" )
                .param("access_token", "token"))

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

        mockMvc.perform( get("/entities?entity_type='ZZZ'")
                .param("access_token", "token"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCorporationsOnly() throws Exception {

        List<LegalEntity> legalEntities = new ArrayList<>();
        legalEntities.add(TestService.createCorporateLegalEntity());


        given(legalEntityTypeService.isValidLegalEntityType("Corporation")).willReturn(true);
        given(legalEntityService.getAllEntitiesFilterByEntityType("Corporation")).willReturn(legalEntities);

        mockMvc.perform( get("/entities?entity_type=Corporation")
                .param("access_token", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].legalEntityType.legalEntityTypeText", is("Corporation")) );
    }


    @Test
    public void shouldGetById() throws Exception {

        LegalEntity legalEntity = TestService.createCorporateLegalEntity();
        legalEntity.setLegalEntityId(1);

        given(legalEntityService.getEntityById(1)).willReturn(Optional.of(legalEntity));

        mockMvc.perform( get("/entities/id/1")
                .param("access_token", "token"))
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

        mockMvc.perform( get("/entities/city/Westwood")
                .param("access_token", "token"))
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

        mockMvc.perform( get("/entities/state/CA")
                .param("access_token", "token"))
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

        mockMvc.perform( get("/entities/address-type/Residence")
                .param("access_token", "token"))
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
        mockMvc.perform( get("/entities/address-type/Residence")
                .param("access_token", "token"))
                .andExpect(status().isBadRequest());

    }


}

