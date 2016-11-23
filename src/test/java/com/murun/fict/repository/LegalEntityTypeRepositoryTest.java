package com.murun.fict.repository;


import com.murun.fict.main.ApplicationConfig;
import com.murun.fict.model.LegalEntityType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfig.class)
//@DataJpaTest
public class LegalEntityTypeRepositoryTest {


    @Resource
    LegalEntityTypeRepository legalEntityTypeRepository;


    @Test
    public void shouldSave() {

        LegalEntityType legalEntityTypeSaved = new LegalEntityType();
        assertNull(legalEntityTypeSaved.getLegalEntityTypeId());

        legalEntityTypeSaved.setLegalEntityTypeId(1);
        legalEntityTypeSaved.setLegalEntityTypeText("Some Text-1");
        legalEntityTypeRepository.save(legalEntityTypeSaved);


        LegalEntityType legalEntityTypeRetrieved = legalEntityTypeRepository.findOne(1);
        assertEquals(legalEntityTypeSaved.getLegalEntityTypeId(), legalEntityTypeRetrieved.getLegalEntityTypeId());
    }

    @Test
    public void shouldUpdate() {

        LegalEntityType legalEntityTypeSaved = new LegalEntityType();


        legalEntityTypeSaved.setLegalEntityTypeId(1);
        legalEntityTypeSaved.setLegalEntityTypeText("Some Text");
        legalEntityTypeRepository.save(legalEntityTypeSaved);
        legalEntityTypeSaved.setLegalEntityTypeText("Some Text Updated");
        legalEntityTypeRepository.save(legalEntityTypeSaved);

        LegalEntityType legalEntityTypeRetrieved = legalEntityTypeRepository.findOne(1);
        assertEquals(legalEntityTypeSaved.getLegalEntityTypeText(), legalEntityTypeRetrieved.getLegalEntityTypeText());
    }

    @Test
    public void verifyCount() {
        assertEquals(legalEntityTypeRepository.count(), legalEntityTypeRepository.findAll().stream().count());
    }
}