package com.murun.fict.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.murun.fict.dto.EntityAddressDTO;

import javax.persistence.*;
import java.io.Serializable;
// import org.hibernate.annotations.Cascade;


@Entity
@Table(name="ENTITY_ADDRESS")
public class EntityAddress implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENTITY_ADDRESS_ID")
    //@JsonIgnore
    private Integer entityAddressId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_TYPE_ID", nullable = false)
    //@JsonBackReference
    private AddressType addressType;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", nullable = true)
    private Address address ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEGAL_ENTITY_ID", nullable = false)
    @JsonBackReference
    private LegalEntity legalEntity;


    public EntityAddress() {
    }

    public static EntityAddress createEntityAddress(EntityAddressDTO entityAddressDTO, AddressType addressType){
        EntityAddress retVal = new EntityAddress();
        retVal.setEntityAddressId( entityAddressDTO.getEntityAddressId());
        retVal.setAddressType(addressType);
        retVal.setAddress(entityAddressDTO.getAddress());
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setLegalEntityId(entityAddressDTO.getLegalEntityId());
        retVal.setLegalEntity(legalEntity);
        return retVal;
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
        int result = getAddressType() != null ? getAddressType().hashCode() : 0;
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getLegalEntity() != null ? getLegalEntity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EntityAddress{" +
                "entityAddressId=" + entityAddressId +
                ", addressType=" + addressType +
                ", address=" + address +
                ", legalEntity=" + legalEntity +
                '}';
    }
}

