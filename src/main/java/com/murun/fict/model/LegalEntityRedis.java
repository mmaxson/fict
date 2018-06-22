package com.murun.fict.model;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@RedisHash("LegalEntityKeySpace")
public class LegalEntityRedis implements Serializable {



    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;


    private Integer legalEntityId;

    private String ordering;

  //  @Indexed
    private Integer legalEntityTypeId;

    private Set<EntityNameRedis> entityNames;


    public LegalEntityRedis() {

    }


    public static LegalEntityRedis from( LegalEntity legalEntity) {
        LegalEntityRedis retVal = new LegalEntityRedis();

        retVal.legalEntityId = legalEntity.getLegalEntityId();
        retVal.legalEntityTypeId = legalEntity.getLegalEntityType().getLegalEntityTypeId();
        retVal.ordering = retVal.legalEntityTypeId + legalEntity.getLegalEntityId().toString();
        retVal.setEntityNames( new HashSet<>());
        for( EntityName en: legalEntity.getEntityNames() ){
            retVal.getEntityNames().add( new EntityNameRedis(en.getEntityNameId(), en.getNameType(), en.getName() + "-"+legalEntity.getLegalEntityId()));
        }

        return retVal;
    }


   /* public LegalEntityRedis(Integer legalEntityTypeId, Set<EntityNameRedis> entityNames, Set<EntityAddress> entityAddresses) {
        this.legalEntityTypeId = legalEntityTypeId;
        this.entityNames = entityNames;
    }
*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(Integer legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public Integer getLegalEntityTypeId() {
        return legalEntityTypeId;
    }

    public void setLegalEntityTypeId(Integer legalEntityTypeId) {
        this.legalEntityTypeId = legalEntityTypeId;
    }

    public String getOrdering() {
        return ordering;
    }

    public void setOrdering(String ordering) {
        this.ordering = ordering;
    }

    public Set<EntityNameRedis> getEntityNames() {
        return entityNames;
    }

    public void setEntityNames(Set<EntityNameRedis> entityNames) {
        this.entityNames = entityNames;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        com.murun.fict.model.LegalEntity that = (com.murun.fict.model.LegalEntity) o;

        return getLegalEntityId() != null ? getLegalEntityId().equals(that.getLegalEntityId()) : that.getLegalEntityId() == null;

    }

    @Override
    public int hashCode() {
        return getLegalEntityId() != null ? getLegalEntityId().hashCode() : 0;
    }



    @Override
    public String toString() {
        String entityNamesText;
        if ( this.getEntityNames() != null){
            entityNamesText = this.getEntityNames().stream().map(x -> x.getName()).collect(Collectors.joining(",", "", ""));
        }

        return "LegalEntityRedis{" +
                " id=" + id +
                ", legalEntityId=" + legalEntityId +
                ", legalEntityTypeId=" + legalEntityTypeId +
                ", ordering=" + ordering +
                ", entityNames=" + entityNames +
                '}';
    }
}
