package com.murun.fict.service;


import com.murun.fict.main.ApplicationConfiguration;
import com.murun.fict.model.AddressType;
import com.murun.fict.repository.AddressTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfiguration.class)


public class AddressTypeServiceTest {

    @Resource
    AddressTypeService addressTypeService;


    @MockBean
    AddressTypeRepository addressTypeRepository;

    private List<AddressType> addressTypes = new ArrayList<>();

    @Before
    public void setUp() {
        addressTypes.add( new AddressType(1, "Residence"));
        addressTypes.add( new AddressType(2, "Work"));
        when(addressTypeRepository.findAll()).thenReturn(addressTypes);
        addressTypeService.initialize();
    }

    @Test
    public void validEntityType() throws Exception {
        assertTrue(addressTypeService.isValidAddressType("Residence"));
        assertTrue(addressTypeService.isValidAddressType("residence"));
    }

    @Test
    public void invalidEntityType() throws Exception {
        assertFalse(addressTypeService.isValidAddressType("zzz"));
    }

    @Test
    public void shouldGetEntityTypeIdByEntityTypeText() throws Exception {
        assertEquals(1, addressTypeService.getAddressTypeId("Residence").intValue());
        assertEquals(1, addressTypeService.getAddressTypeId("residence").intValue());
    }

    @Test
    public void shouldNotFindEntityTypeIdByEntityTypeText() throws Exception {
        assertNull( addressTypeService.getAddressTypeId("zzz"));
    }
}
