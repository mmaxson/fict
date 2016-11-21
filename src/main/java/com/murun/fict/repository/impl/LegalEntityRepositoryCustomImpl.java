package com.murun.fict.repository;

import com.murun.fict.model.LegalEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@Repository
public class LegalEntityRepositoryCustomImpl implements LegalEntityRepositoryCustom{

    @PersistenceContext
    private EntityManager em;


   // public List<LegalEntity> getEntityByFirstName(String firstName) {
     //   List<LegalEntity> retVal = new ArrayList<>();

//        retVal.setAll( em.createQuery(
//                "SELECT a FROM Address a WHERE a.state = :state")
//                .setParameter("state", state)
//
//                .getResultList());

   //     return retVal;
   // }

   /* public AddressList getAddressByStateAndCity(String state, String city) {
        AddressList retVal = new AddressList();

        retVal.setAll( em.createQuery(
                "SELECT a FROM Address a WHERE a.state = :state and a.city = :city")
                .setParameter("state", state)
                .setParameter("city", city)
                //.setMaxResults(10)
                .getResultList());
        return retVal;
    }

    public AddressList getAddressByZip(String zipCode) {
        AddressList retVal = new AddressList();

        retVal.setAll( em.createQuery(
                "SELECT a FROM Address a WHERE a.zipCode = :zipCode")
                .setParameter("zipCode", zipCode)
                //.setMaxResults(10)
                .getResultList());
        return retVal;
    }
*/

}
