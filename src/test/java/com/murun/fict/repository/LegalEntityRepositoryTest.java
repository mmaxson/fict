/*
package com.murun.fict.repository;


import com.murun.fict.TestService;
import com.murun.fict.model.EntityAddress;
import com.murun.fict.model.EntityName;
import com.murun.fict.model.LegalEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static com.murun.fict.TestService.createEntityAddress;
import static org.assertj.core.api.Assertions.*;



//@RunWith(SpringRunner.class)
//@DataJpaTest
public class LegalEntityRepositoryTest {






    @Test
    public void findByUsernameShouldReturnUser() {


        */
/*LegalEntity legalEntity = new LegalEntity();
        legalEntity.setLegalEntityType(TestService.createLegalEntityType(1,"Corporation"));


        Set<EntityName> entityNames = new HashSet<>();

        entityNames.add(TestService.createEntityName(TestService.createNameType(1,"First"),"Mark",legalEntity));
        entityNames.add(TestService.createEntityName(TestService.createNameType(2,"Last"),"Urun",legalEntity));
        entityNames.add(TestService.createEntityName(TestService.createNameType(3,"Middle"),"M",legalEntity));


        entityNames.add(TestService.createEntityName(TestService.createNameType(1,"First"),"Joe",legalEntity));
        entityNames.add(TestService.createEntityName(TestService.createNameType(2,"Last"),"McGee",legalEntity));
        legalEntity.setEntityNames(entityNames);

        Set<EntityAddress> entityAddresses = new HashSet<>();
        entityAddresses.add(createEntityAddress(legalEntity,1, 1, "Residence"));
        entityAddresses.add(createEntityAddress(legalEntity,2, 1, "Residence"));
        entityAddresses.add(createEntityAddress(legalEntity,3, 2, "Work"));
        entityAddresses.add(createEntityAddress(legalEntity,3, 3, "Mail"));

        legalEntity.setEntityAddresses(entityAddresses);
        this.entityManager.persist(legalEntity);

        LegalEntity actualLegalEntity = this.repository.findOne(1);
//        User user = this.repository.findByUsername("sboot");

        assertThat(actualLegalEntity.getLegalEntityId()).isEqualTo("sboot");
       // assertThat(user.getVin()).isEqualTo("123");*//*

    }

}
*/
