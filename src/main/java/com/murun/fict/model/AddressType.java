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
    private Integer addressTypeId;

    @JsonIgnore
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

    @Override
    public String toString() {
        return "AddressType{" +
                "addressTypeId=" + addressTypeId +
                ", addressTypeText='" + addressTypeText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressType that = (AddressType) o;

        return getAddressTypeText().equals(that.getAddressTypeText());
    }

    @Override
    public int hashCode() {
        return getAddressTypeText().hashCode();
    }
}
