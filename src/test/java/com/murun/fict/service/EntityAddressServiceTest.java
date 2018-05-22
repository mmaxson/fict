package com.murun.fict.service;


import com.murun.fict.dto.EntityAddressDTO;
import com.murun.fict.main.ApplicationConfiguration;
import com.murun.fict.model.Address;
import com.murun.fict.model.AddressType;
import com.murun.fict.model.EntityAddress;
import com.murun.fict.model.LegalEntity;
import com.murun.fict.repository.EntityAddressRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.murun.fict.TestService.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfiguration.class)


public class EntityAddressServiceTest {

    @Resource
    private EntityAddressService entityAddressService;


    @MockBean
    private EntityAddressRepository entityAddressRepository;

    @MockBean
    private LegalEntityTypeService legalEntityTypeService;

    @MockBean
    private AddressTypeService addressTypeService;


    @Test
    public void shouldGetEntityAddressesByEntityId() throws Exception {
        List<EntityAddress> entityAddresses = new ArrayList<>();
        entityAddresses.add( createEntityAddress(createCorporateLegalEntity(),createAddress("city", "CA","11111"), createWorkAddressType()));

        Page<EntityAddress> entityAddressesPage = new PageImpl(entityAddresses);
        when(entityAddressRepository.getEntityAddressesByLegalEntityId(1, PageRequest.of( 0, 20))).thenReturn(entityAddressesPage);

        assertEquals( entityAddressesPage, entityAddressService.getEntityAddressesByLegalEntityId(1, PageRequest.of( 0, 20)));
    }

    @Test
    public void shouldSaveEntityAddress() throws Exception {

        Address address = createAddress("city", "CA", "11111");
        LegalEntity legalEntity = createCorporateLegalEntity();
        legalEntity.setLegalEntityId(1);
        EntityAddressDTO entityAddressDTO = new EntityAddressDTO(1, AddressTypeTestEnum.WORK.addressTypeId(), address, 1);


        AddressType addressType = createWorkAddressType();
        when(addressTypeService.createAddressTypeById(entityAddressDTO.getAddressTypeId())).thenReturn(addressType);

        EntityAddress entityAddress = createEntityAddress( 1, legalEntity, address, addressType );
        EntityAddress savedEntityAddress = createEntityAddress( 1, legalEntity, address, addressType );

        when(entityAddressRepository.saveAndFlush(entityAddress)).thenReturn(savedEntityAddress);

        assertEquals( savedEntityAddress,  entityAddressService.saveEntityAddress(entityAddressDTO));
    }

    @Test
    public void shouldDeleteEntityAddressById() throws Exception {

        when( entityAddressRepository.deleteEntityAddressesByEntityAddressId(1)).thenReturn(1);
        entityAddressService.deleteEntityAddressesByEntityAddressId(1);
        assertEquals( Integer.valueOf(1), entityAddressService.deleteEntityAddressesByEntityAddressId(1) );
    }

}