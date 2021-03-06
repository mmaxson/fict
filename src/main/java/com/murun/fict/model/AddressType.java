package com.murun.fict.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@javax.persistence.Entity
@Table(name="ADDRESS_TYPE")
public class AddressType implements Serializable {

    @Id
    @Column(name = "ADDRESS_TYPE_ID")
    @JsonIgnore
    private Integer addressTypeId;

    @Column(name = "ADDRESS_TYPE_TEXT")
    private String addressTypeText;


    public AddressType() {
    }

    public AddressType(Integer addressTypeId, String addressTypeText) {
        this.addressTypeId = addressTypeId;
        this.addressTypeText = addressTypeText;
    }

    public Integer getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Integer addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public String getAddressTypeText() {
        return addressTypeText;
    }

    public void setAddressTypeText(String addressTypeText) {
        this.addressTypeText = addressTypeText;
    }
}
