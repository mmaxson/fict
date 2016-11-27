package com.murun.fict.service;


import com.murun.fict.main.ApplicationConfig;
import com.murun.fict.model.LegalEntityType;
import com.murun.fict.repository.LegalEntityTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfig.class)


public class LegalEntityTypeServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(LegalEntityTypeServiceTest.class);


    @Resource
    LegalEntityTypeService legalEntityTypeService;


    @MockBean
    LegalEntityTypeRepository legalEntityTypeRepository;

    private List<LegalEntityType> legalEntityTypes = new ArrayList<>();

    @Before
    public void setUp() {
        legalEntityTypes.add( new LegalEntityType(1, "Corporation"));
        legalEntityTypes.add( new LegalEntityType(2, "Individual"));
        when(legalEntityTypeRepository.findAll()).thenReturn(legalEntityTypes);
        legalEntityTypeService.initialize();
    }

    @Test
    public void validEntityType() throws Exception {
        assertTrue(legalEntityTypeService.isValidLegalEntityType("Corporation"));
        assertTrue(legalEntityTypeService.isValidLegalEntityType("corporation"));
    }

    @Test
    public void invalidEntityType() throws Exception {
        assertFalse(legalEntityTypeService.isValidLegalEntityType("zorporation"));
    }

    @Test
    public void shouldGetEntityTypeIdByEntityTypeText() throws Exception {
        assertEquals(1, legalEntityTypeService.getLegalEntityTypeId("Corporation").intValue());
        assertEquals(1, legalEntityTypeService.getLegalEntityTypeId("corporation").intValue());
    }

    @Test
    public void shouldNotFindEntityTypeIdByEntityTypeText() throws Exception {
        assertNull( legalEntityTypeService.getLegalEntityTypeId("zzz"));
    }
}
