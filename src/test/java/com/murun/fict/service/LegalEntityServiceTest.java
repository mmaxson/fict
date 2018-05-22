package com.murun.fict.service;


import com.murun.fict.TestService;
import com.murun.fict.main.ApplicationConfiguration;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.repository.LegalEntityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.murun.fict.TestService.LegalEntityTypeTestEnum;
import static com.murun.fict.TestService.createLegalEntityType;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfiguration.class)


public class LegalEntityServiceTest {

    @Resource
    private LegalEntityService legalEntityService;


    @MockBean
    private LegalEntityRepository legalEntityRepository;

    @MockBean
    private LegalEntityTypeService legalEntityTypeService;

    @MockBean
    private AddressTypeService addressTypeService;


    private final List<LegalEntity> legalEntityList = new ArrayList<>();
    private final List<LegalEntity> legalEntityList1 = new ArrayList<>();
    private final LegalEntity oneLegalEntity = TestService.createCorporateLegalEntity();
    private final LegalEntity exampleLegalEntity = new LegalEntity();

    @Before
    public void setUp() {

        legalEntityList.add(TestService.createCorporateLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());
        legalEntityList.add(TestService.createIndividualLegalEntity());
        when(legalEntityRepository.findAll()).thenReturn(legalEntityList);
       // when(legalEntityRepository.findOne(1)).thenReturn(oneLegalEntity);

        exampleLegalEntity.setLegalEntityType(createLegalEntityType(LegalEntityTypeTestEnum.CORPORATION));
        Example<LegalEntity> example = Example.of(exampleLegalEntity);
        legalEntityList1.add(oneLegalEntity);
        when(legalEntityRepository.findAll(example)).thenReturn(legalEntityList1);
    }

    @Test
    public void shouldGetAllLegalEntities() throws Exception {

        Pageable pageRequest = PageRequest.of( 0, 20);
        when(legalEntityRepository.findAll(pageRequest)).thenReturn(TestService.createLegalEntitiesPageMixedEntityType());
        assertEquals(3, legalEntityService.getAllLegalEntities(pageRequest).getTotalElements());
    }

    @Test
    public void shouldFindById() throws Exception {
        legalEntityList.get(0).setLegalEntityId(1);
        legalEntityList.get(1).setLegalEntityId(2);
        legalEntityList.get(2).setLegalEntityId(3);

        when(legalEntityRepository.findById(1)).thenReturn(Optional.of(legalEntityList.get(0)));
        assertEquals(legalEntityList.get(0), legalEntityService.getEntityById(1));
    }

    @Test
    public void shouldFilterByEntityType() throws Exception {


        Pageable pageRequest = PageRequest.of( 0, 20);

        when(legalEntityTypeService.getLegalEntityTypeId(LegalEntityTypeTestEnum.CORPORATION.entityTypeText()))
                .thenReturn(LegalEntityTypeTestEnum.CORPORATION.entityTypeId());
        when(legalEntityRepository.getAllEntitiesFilterByEntityType(LegalEntityTypeTestEnum.CORPORATION.entityTypeId(), pageRequest))
                .thenReturn(TestService.createLegalEntitiesPageMixedEntityType());
        assertEquals(3, legalEntityService.getAllEntitiesFilterByEntityType(LegalEntityTypeTestEnum.CORPORATION.entityTypeText(), pageRequest).getTotalElements());
    }


  /*  @Test
    public void shouldGetEntitiesWithAddressesInState() throws Exception {

        List<LegalEntity> legalEntityListLocal = new ArrayList<>();
        legalEntityListLocal.add(TestService.createCorporateLegalEntity());
        legalEntityListLocal.add(TestService.createIndividualLegalEntity());

        legalEntityListLocal.get(0).setEntityAddresses(new HashSet<>());
        legalEntityListLocal.get(0).getEntityAddresses().add((createResidenceEntityAddr(legalEntityListLocal.get(0), "Santa Monica", "CA", "90402")));
        legalEntityListLocal.get(0).getEntityAddresses().add((createResidenceEntityAddr(legalEntityListLocal.get(0), "Westwood", "AZ", "90402")));

        legalEntityListLocal.get(1).setEntityAddresses(new HashSet<>());
        legalEntityListLocal.get(1).getEntityAddresses().add((createResidenceEntityAddr(legalEntityListLocal.get(1), "Phoenix", "AZ", "90402")));

        when(legalEntityRepository.findAll()).thenReturn(legalEntityListLocal);
        assertEquals(2, legalEntityService.getEntitiesWithAddressesInState("AZ", null).getTotalElements());
    }


    @Test
    public void shouldGetEntitiesWithAddressesInCity() throws Exception {
        List<LegalEntity> legalEntityListLocal = new ArrayList<>();
        legalEntityListLocal.add(TestService.createCorporateLegalEntity());
        legalEntityListLocal.add(TestService.createIndividualLegalEntity());

        legalEntityListLocal.get(0).setEntityAddresses(new HashSet<>());
        legalEntityListLocal.get(0).getEntityAddresses().add((createResidenceEntityAddr(legalEntityListLocal.get(0), "Santa Monica", "CA", "90402")));
        legalEntityListLocal.get(0).getEntityAddresses().add((createResidenceEntityAddr(legalEntityListLocal.get(0), "Westwood", "AZ", "90402")));

        legalEntityListLocal.get(1).setEntityAddresses(new HashSet<>());
        legalEntityListLocal.get(1).getEntityAddresses().add((createResidenceEntityAddr(legalEntityListLocal.get(1), "Phoenix", "AZ", "90402")));

        when(legalEntityRepository.findAll()).thenReturn(legalEntityListLocal);
        assertEquals(1, legalEntityService.getEntitiesWithAddressesInCity("Santa Monica", null).getTotalElements());
    }


    @Test
    public void shouldGetEntitiesWithAddressesWithAddressType() throws Exception {
        List<LegalEntity> legalEntityListLocal = new ArrayList<>();
        legalEntityListLocal.add(TestService.createCorporateLegalEntity());
        legalEntityListLocal.add(TestService.createIndividualLegalEntity());

        legalEntityListLocal.get(0).setEntityAddresses(new HashSet<>());
        legalEntityListLocal.get(0).getEntityAddresses().add((createResidenceEntityAddr(legalEntityListLocal.get(0), "Santa Monica", "CA", "90402")));
        legalEntityListLocal.get(0).getEntityAddresses().add((createMailingEntityAddr(legalEntityListLocal.get(0), "Westwood", "CA", "90402")));

        legalEntityListLocal.get(1).setEntityAddresses(new HashSet<>());
        legalEntityListLocal.get(1).getEntityAddresses().add((createWorkEntityAddr(legalEntityListLocal.get(1), "Phoenix", "AZ", "90402")));

        when(addressTypeService.getAddressTypeId("Residence")).thenReturn( TestService.AddressTypeTestEnum.RESIDENCE.addressTypeId() );

        when(legalEntityRepository.findAll()).thenReturn(legalEntityListLocal);
        assertEquals(1, legalEntityService.getAllEntitiesWithAddressesWithAddressType("Residence", null).getTotalElements());
    }
*/}
