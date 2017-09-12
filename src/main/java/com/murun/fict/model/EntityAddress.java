package com.murun.fict.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="ENTITY_ADDRESS")
public class EntityAddress implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENTITY_ADDRESS_ID")
    //@JsonIgnore
    private Integer entityAddressId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_TYPE_ID", nullable = false)
    private AddressType addressType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_ID", nullable = true)
    private Address address ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEGAL_ENTITY_ID", nullable = true)
    @JsonBackReference

    private LegalEntity legalEntity;


    public EntityAddress() {
    }

    public Integer getEntityAddressId() {
        return entityAddressId;
    }

    public void setEntityAddressId(Integer entityAddressId) {
        this.entityAddressId = entityAddressId;
    }


    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityAddress that = (EntityAddress) o;

        if (getEntityAddressId() != null ? !getEntityAddressId().equals(that.getEntityAddressId()) : that.getEntityAddressId() != null)
            return false;
        if (getAddressType() != null ? !getAddressType().equals(that.getAddressType()) : that.getAddressType() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        return getLegalEntity() != null ? getLegalEntity().equals(that.getLegalEntity()) : that.getLegalEntity() == null;

    }

    @Override
    public int hashCode() {
        int result = getEntityAddressId() != null ? getEntityAddressId().hashCode() : 0;
        result = 31 * result + (getAddressType() != null ? getAddressType().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getLegalEntity() != null ? getLegalEntity().hashCode() : 0);
        return result;
    }
}

