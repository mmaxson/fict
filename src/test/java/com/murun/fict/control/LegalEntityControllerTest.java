package com.murun.fict.control;


import com.murun.fict.TestService;
import com.murun.fict.main.ApplicationConfig;
import com.murun.fict.model.LegalEntity;
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
import java.util.List;

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
    public void shouldReturnBadRequest() throws Exception {

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

}
