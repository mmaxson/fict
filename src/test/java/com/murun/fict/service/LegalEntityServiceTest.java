package com.murun.fict.service;


import com.murun.fict.TestService;
import com.murun.fict.main.ApplicationConfig;
import com.murun.fict.model.EntityAddress;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.model.LegalEntityType;
import com.murun.fict.repository.LegalEntityRepository;
import com.murun.fict.repository.LegalEntityTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.murun.fict.TestService.createAddress;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfig.class)


public class LegalEntityServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(LegalEntityServiceTest.class);


    @Resource
    LegalEntityService legalEntityService;


    @MockBean
    LegalEntityRepository legalEntityRepository;

    @MockBean
    LegalEntityTypeService legalEntityTypeService;

    private List<LegalEntity> legalEntityList = new ArrayList<>();
    private List<LegalEntity> legalEntityList1 = new ArrayList<>();
    private LegalEntity oneLegalEntity = TestService.createCorporateLegalEntity();
    private LegalEntity exampleLegalEntity = new LegalEntity();

    @Before
    public void setUp() {

        legalEntityList.add(TestService.createCorporateLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());
        when(legalEntityRepository.findAll()).thenReturn(legalEntityList);
        when(legalEntityRepository.findOne(1)).thenReturn(oneLegalEntity);

        exampleLegalEntity.setLegalEntityType(new LegalEntityType(1, "Corporation"));
        Example<LegalEntity> example = Example.of(exampleLegalEntity);
        legalEntityList1.add(oneLegalEntity);
        when(legalEntityRepository.findAll(example)).thenReturn(legalEntityList1);



    }

    @Test
    public void shouldGetAllLegalEntities() throws Exception {
        assertEquals(3, legalEntityService.getAllLegalEntities().size());
    }

    @Test
    public void shouldFindById() throws Exception {
        assertEquals(oneLegalEntity, legalEntityService.getEntityById(1));
    }

    @Test
    public void shouldFilterByEntityType() throws Exception {
        when(legalEntityTypeService.getLegalEntityTypeId("Corporation")).thenReturn(1);
        assertEquals(1, legalEntityService.getAllEntitiesFilterByEntityType("Corporation").size());
    }


    @Test
    public void shouldGetEntitiesWithAddressesInState() throws Exception {

        List<LegalEntity> legalEntityList = new ArrayList<>();
        legalEntityList.add(TestService.createCorporateLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());

        legalEntityList.get(0).setEntityAddresses(new HashSet<>());
        legalEntityList.get(0).getEntityAddresses().add((TestService.createResidenceAddrForLegalEntity(legalEntityList.get(0), "Santa Monica", "CA", "90402")));
        legalEntityList.get(0).getEntityAddresses().add((TestService.createResidenceAddrForLegalEntity(legalEntityList.get(0), "Westwood", "AZ", "90402")));

        legalEntityList.get(1).setEntityAddresses(new HashSet<>());
        legalEntityList.get(1).getEntityAddresses().add((TestService.createResidenceAddrForLegalEntity(legalEntityList.get(1), "Phoenix", "AZ", "90402")));

        when(legalEntityRepository.getEntitiesWithAddressesInState("AZ")).thenReturn(legalEntityList);
        assertEquals(2, legalEntityService.getEntitiesWithAddressesInState("AZ").size());
    }


    @Test
    public void shouldGetEntitiesWithAddressesInCity() throws Exception {
        List<LegalEntity> legalEntityList = new ArrayList<>();
        legalEntityList.add(TestService.createCorporateLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());

        legalEntityList.get(0).setEntityAddresses(new HashSet<>());
        legalEntityList.get(0).getEntityAddresses().add((TestService.createResidenceAddrForLegalEntity(legalEntityList.get(0), "Santa Monica", "CA", "90402")));
        legalEntityList.get(0).getEntityAddresses().add((TestService.createResidenceAddrForLegalEntity(legalEntityList.get(0), "Westwood", "AZ", "90402")));

        legalEntityList.get(1).setEntityAddresses(new HashSet<>());
        legalEntityList.get(1).getEntityAddresses().add((TestService.createResidenceAddrForLegalEntity(legalEntityList.get(1), "Phoenix", "AZ", "90402")));

        when(legalEntityRepository.getEntitiesWithAddressesInCity("Santa Monica")).thenReturn(legalEntityList.subList(0,1));
        assertEquals(1, legalEntityService.getEntitiesWithAddressesInCity("Santa Monica").size());
    }

}
