package com.murun.fict.repository;


import com.murun.fict.main.ApplicationConfiguration;
import com.murun.fict.model.EntityAddress;
import com.murun.fict.model.LegalEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Optional;

import static com.murun.fict.TestService.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfiguration.class)

public class EntityAddressRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    EntityAddressRepository entityAddressRepository;

    @Resource
    LegalEntityRepository legalEntityRepository;

    @Resource
    LegalEntityTypeRepository legalEntityTypeRepository;

    @Resource
    AddressTypeRepository addressTypeRepository;

    private LegalEntity legalEntity;

    @Before
    public void beforeEach() throws Exception {
        addressTypeRepository.save(createAddressType(AddressTypeTestEnum.WORK));
        legalEntityTypeRepository.save(createLegalEntityType(LegalEntityTypeTestEnum.CORPORATION));
        legalEntity  = legalEntityRepository.save(createCorporateLegalEntity());
    }

    @After
    public void afterEach() {

        legalEntityRepository.deleteAll();
        legalEntityTypeRepository.deleteAll();
        addressTypeRepository.deleteAll();
        entityAddressRepository.deleteAll();
    }

    @Test
    public void shouldSave() {

        EntityAddress entityAddress = new EntityAddress();
        assertNull(entityAddress.getEntityAddressId());

        entityAddress = createEntityAddress(legalEntity, createAddress("city", "CA", "11111"), createWorkAddressType());
        entityAddress = entityAddressRepository.save(entityAddress);
        int entityAddressId = entityAddress.getEntityAddressId();

        Optional<EntityAddress> entityAddressRetrieved = entityAddressRepository.findById(entityAddressId);

        assertEquals(entityAddress.getEntityAddressId(), entityAddressRetrieved.get().getEntityAddressId());
    }

    @Test
    public void shouldUpdate() {

        EntityAddress entityAddress = createEntityAddress(legalEntity, createAddress("city", "CA", "11111"), createWorkAddressType());
        entityAddress = entityAddressRepository.save(entityAddress);
        int entityAddressId = entityAddress.getEntityAddressId();

        entityAddress.getAddress().setCity("new city");
        entityAddressRepository.save(entityAddress);

        Optional<EntityAddress> entityAddressRetrieved = entityAddressRepository.findById(entityAddressId);
        assertEquals(entityAddress.getAddress().getCity(), entityAddressRetrieved.get().getAddress().getCity());
    }

    @Test
    public void shouldGetAddressesByEntityId() {
        entityAddressRepository.save(createEntityAddress(legalEntity, createAddress("city-1", "CA","11111"), createWorkAddressType()));

        Page<EntityAddress> entityAddressPage = entityAddressRepository.getEntityAddressesByLegalEntityId(legalEntity.getLegalEntityId(), PageRequest.of( 0, 20));
        assertEquals(legalEntity.getEntityAddresses().iterator().next(), entityAddressPage.getContent().get(0));
    }

    @Test
    public void shouldDeleteEntityAddressesByEntityAddressId() {

        EntityAddress entityAddress = createEntityAddress(legalEntity, createAddress("city-1", "CA","11111"), createWorkAddressType());
        entityAddress = entityAddressRepository.save(entityAddress);
        int entityAddressId = entityAddress.getEntityAddressId();

        entityAddressRepository.deleteEntityAddressesByEntityAddressId(entityAddressId);
        assertFalse(entityAddressRepository.findById(entityAddressId).isPresent());
    }
}