package com.murun.fict.repository;


import com.murun.fict.main.ApplicationConfiguration;
import com.murun.fict.model.LegalEntityType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfiguration.class)
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


        Optional<LegalEntityType> legalEntityTypeRetrieved = legalEntityTypeRepository.findById(1);
        assertEquals(legalEntityTypeSaved.getLegalEntityTypeId(), legalEntityTypeRetrieved.get().getLegalEntityTypeId());
    }

    @Test
    public void shouldUpdate() {

        LegalEntityType legalEntityTypeSaved = new LegalEntityType();


        legalEntityTypeSaved.setLegalEntityTypeId(1);
        legalEntityTypeSaved.setLegalEntityTypeText("Some Text");
        legalEntityTypeRepository.save(legalEntityTypeSaved);
        legalEntityTypeSaved.setLegalEntityTypeText("Some Text Updated");
        legalEntityTypeRepository.save(legalEntityTypeSaved);

        Optional<LegalEntityType> legalEntityTypeRetrieved = legalEntityTypeRepository.findById(1);
        assertEquals(legalEntityTypeSaved.getLegalEntityTypeText(), legalEntityTypeRetrieved.get().getLegalEntityTypeText());
    }

    @Test
    public void verifyCount() {
        LegalEntityType legalEntityTypeSaved = new LegalEntityType();
        legalEntityTypeSaved.setLegalEntityTypeId(1);
        legalEntityTypeSaved.setLegalEntityTypeText("Text");
        legalEntityTypeRepository.save(legalEntityTypeSaved);

        assertEquals(legalEntityTypeRepository.count(), legalEntityTypeRepository.findAll().size());
    }
}