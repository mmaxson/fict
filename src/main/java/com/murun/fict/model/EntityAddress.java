package com.murun.fict.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="ENTITY_ADDRESS")
public class EntityAddress implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENTITY_ADDRESS_ID")
    private Integer entityAddressId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_TYPE_ID", nullable = false)
    private AddressType addressType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private Address address ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEGAL_ENTITY_ID")
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

}

