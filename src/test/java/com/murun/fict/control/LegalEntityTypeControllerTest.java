package com.murun.fict.control;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.murun.fict.TestService;
import com.murun.fict.main.ApplicationConfiguration;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.model.LegalEntityType;
import com.murun.fict.repository.LegalEntityTypeRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
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
public class LegalEntityTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    LegalEntityTypeService legalEntityTypeService;

    @MockBean
    LegalEntityTypeRepository legalEntityTypeRepository;

    @MockBean
    AddressTypeService addressTypeService;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9001);


    @Before
    public void beforeEach() throws Exception {
        TestService.getMockAuthToken();
    }




    @Test
    public void testGetAllEntityTypes() throws Exception {
        List<LegalEntityType> legalEntityTypes = new ArrayList<>();
        legalEntityTypes.add( TestService.createLegalEntityType(TestService.LegalEntityTypeTestEnum.INDIVIDUAL));
        legalEntityTypes.add( TestService.createLegalEntityType(TestService.LegalEntityTypeTestEnum.CORPORATION));
        legalEntityTypes.add( TestService.createLegalEntityType(TestService.LegalEntityTypeTestEnum.LIVING_TRUST));

        given(legalEntityTypeRepository.findAll()).willReturn(legalEntityTypes);

        mockMvc.perform( get( "/entity-types" )
                .param("access_token", "token"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].legalEntityTypeText", is(TestService.LegalEntityTypeTestEnum.INDIVIDUAL.entityTypeText())) )
                .andExpect(jsonPath("$[1].legalEntityTypeText", is(TestService.LegalEntityTypeTestEnum.CORPORATION.entityTypeText())) )
                .andExpect(jsonPath("$[2].legalEntityTypeText", is(TestService.LegalEntityTypeTestEnum.LIVING_TRUST.entityTypeText())) );
    }

}

