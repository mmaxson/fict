package com.murun.fict.control;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.murun.fict.TestService;
import com.murun.fict.main.ApplicationConfiguration;
import com.murun.fict.model.LegalEntityType;
import com.murun.fict.model.LegalEntityTypeNameType;
import com.murun.fict.repository.LegalEntityTypeNameTypeRepository;
import com.murun.fict.repository.LegalEntityTypeRepository;
import com.murun.fict.service.AddressTypeService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static com.murun.fict.TestService.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
public class LegalEntityTypeNameTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    LegalEntityTypeNameTypeRepository legalEntityTypeNameTypeRepository;


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
    public ResponseEntity<List<LegalEntityTypeNameType>> getAllLegalEntityTypeNameTypes() {
        List<LegalEntityTypeNameType> legalEntityTypeNameType = legalEntityTypeNameTypeRepository.findAll();
        return new ResponseEntity<List<LegalEntityTypeNameType>>(legalEntityTypeNameType, new HttpHeaders(), HttpStatus.OK);
    }


    @Test
    public void testGetAllLegalEntityTypeNameTypes() throws Exception {
        List<LegalEntityTypeNameType> legalEntityTypeNameType = new ArrayList<>();
        legalEntityTypeNameType.add( new LegalEntityTypeNameType( 1,  createLegalEntityType(LegalEntityTypeTestEnum.CORPORATION),
                                                   createNameType(NameTypeTestEnum.NAME_ORG)  )) ;
        legalEntityTypeNameType.add( new LegalEntityTypeNameType( 2,  createLegalEntityType(LegalEntityTypeTestEnum.INDIVIDUAL),
                createNameType(NameTypeTestEnum.NAME_FIRST)  )) ;
        legalEntityTypeNameType.add( new LegalEntityTypeNameType( 3,  createLegalEntityType(LegalEntityTypeTestEnum.INDIVIDUAL),
                createNameType(NameTypeTestEnum.NAME_LAST)  )) ;


        given(legalEntityTypeNameTypeRepository.findAll()).willReturn(legalEntityTypeNameType);

        mockMvc.perform( get( "/entityTypeNameTypes" )
                .param("access_token", "token"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].legalEntityType.legalEntityTypeText", is(LegalEntityTypeTestEnum.CORPORATION.entityTypeText())) )
                .andExpect(jsonPath("$[0].nameType.nameTypeText", is(NameTypeTestEnum.NAME_ORG.nameTypeText())))

                .andExpect(jsonPath("$[1].legalEntityType.legalEntityTypeText", is(LegalEntityTypeTestEnum.INDIVIDUAL.entityTypeText())) )
                .andExpect(jsonPath("$[1].nameType.nameTypeText", is(NameTypeTestEnum.NAME_FIRST.nameTypeText())))

                .andExpect(jsonPath("$[2].legalEntityType.legalEntityTypeText", is(LegalEntityTypeTestEnum.INDIVIDUAL.entityTypeText())) )
                .andExpect(jsonPath("$[2].nameType.nameTypeText", is(NameTypeTestEnum.NAME_LAST.nameTypeText()))) ;
    }

}

