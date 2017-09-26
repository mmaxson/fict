package com.murun.fict.control;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.murun.commonrest.model.SuccessResource;
import com.murun.fict.TestService;
import com.murun.fict.dto.EntityAddressDTO;
import com.murun.fict.main.ApplicationConfiguration;
import com.murun.fict.model.Address;
import com.murun.fict.model.AddressType;
import com.murun.fict.model.EntityAddress;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.repository.EntityAddressRepository;
import com.murun.fict.service.AddressTypeService;
import com.murun.fict.service.EntityAddressService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.murun.fict.TestService.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= {ApplicationConfiguration.class})

@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class EntityAddressControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    EntityAddressService entityAddressService;

    @MockBean
    EntityAddressRepository entityAddressRepository;

    @MockBean
    AddressTypeService addressTypeService;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9001);


    @Before
    public void beforeEach() throws Exception {
        long unixTimestamp = Instant.now().plusSeconds(10).getEpochSecond();
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(urlPathEqualTo("/murun/auth/oauth/check_token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"aud\":[\"oauth-resource\"],\"exp\":" + unixTimestamp +  ",\"user_name\":\"marku\",\"authorities\":[\"ROLE_ADMIN\",\"ROLE_USER\"],\"client_id\":\"trusted-client\",\"scope\":[\"read\",\" write\",\" trust\"]}")));

    }


    @After
    public void afterEach() {

    }


    @Test
    public void testGetAddressesByEntityId() throws Exception {

        LegalEntity legalEntity = TestService.createCorporateLegalEntity();
        legalEntity.setLegalEntityId(1);

        List<EntityAddress> entityAddresses = new ArrayList<>();
        entityAddresses.add(createMailingEntityAddr( 1, legalEntity, 1, "Mail Street", "CA", "11111" ));
        entityAddresses.add(createWorkEntityAddr( 1, legalEntity, 2, "Work Street", "CA", "22222" ));

        Page<EntityAddress> entityAddressesPage = new PageImpl(entityAddresses);

        given(entityAddressService.getEntityAddressesByLegalEntityId(1, new PageRequest( 0, 20))).willReturn(entityAddressesPage);

        mockMvc.perform( get("/addresses/id/1")
                .param("access_token", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].entityAddressId", is(1)) )
                .andExpect(jsonPath("$.content[0].addressType.addressTypeId", is(AddressTypeTestEnum.MAIL.addressTypeId())) )
                .andExpect(jsonPath("$.content[0].address.addressId", is(1)) )

                .andExpect(jsonPath("$.content[1].entityAddressId", is(1)) )
                .andExpect(jsonPath("$.content[1].addressType.addressTypeId", is(AddressTypeTestEnum.WORK.addressTypeId())) )
                .andExpect(jsonPath("$.content[1].address.addressId", is(2)) );
    }

    @Test
    public void testUpdateEntityAddress() throws Exception {

        Address address = createAddress("city", "CA", "11111");
        EntityAddressDTO entityAddressDTO = new EntityAddressDTO(1, AddressTypeTestEnum.WORK.addressTypeId(), address, 1);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(put("/addresses")
                .param("access_token", "token")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(entityAddressDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddEntityAddress() throws Exception {

        Address address = createAddress("city", "CA", "11111");
        EntityAddressDTO entityAddressDTO = new EntityAddressDTO(1, AddressTypeTestEnum.WORK.addressTypeId(), address, 1);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/addresses")
                .param("access_token", "token")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(entityAddressDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEntityAddress() throws Exception {
        mockMvc.perform( get("/addresses/id/1")
                .param("access_token", "token"))
                .andExpect(status().isOk());
    }
}

