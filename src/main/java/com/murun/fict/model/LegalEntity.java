package com.murun.fict.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;



@Entity
@Table(name="LEGAL_ENTITY")
public class LegalEntity implements Serializable {

    @org.springframework.data.annotation.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LEGAL_ENTITY_ID")
    //@JsonIgnore
    private Integer legalEntityId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEGAL_ENTITY_TYPE_ID", nullable = false)
   // @JsonIgnore
    private LegalEntityType legalEntityType;


    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<EntityName> entityNames;


    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<EntityAddress> entityAddresses;


    public LegalEntity() {

    }

    public LegalEntity(LegalEntityType legalEntityType, Set<EntityName> entityNames, Set<EntityAddress> entityAddresses) {
        this.legalEntityType = legalEntityType;
        this.entityNames = entityNames;
        this.entityAddresses = entityAddresses;
    }

    public Integer getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(Integer legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public LegalEntityType getLegalEntityType() {
        return legalEntityType;
    }



    public void setLegalEntityType(LegalEntityType legalEntityType) {
        this.legalEntityType = legalEntityType;
    }

    public Set<EntityName> getEntityNames() {
        return entityNames;
    }

    public void setEntityNames(Set<EntityName> entityNames) {
        this.entityNames = entityNames;
    }

    public Set<EntityAddress> getEntityAddresses() {
        return entityAddresses;
    }

    public void setEntityAddresses(Set<EntityAddress> entityAddresses) {
        this.entityAddresses = entityAddresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegalEntity that = (LegalEntity) o;

        return getLegalEntityId() != null ? getLegalEntityId().equals(that.getLegalEntityId()) : that.getLegalEntityId() == null;

    }

    @Override
    public int hashCode() {
        return getLegalEntityId() != null ? getLegalEntityId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "LegalEntity{" +
                "legalEntityId=" + legalEntityId +
        //        "legalEntityTypeId=" + legalEntityType.getLegalEntityTypeId() +
        //        ", entityNames=" + entityNames +
                '}';
    }
}











