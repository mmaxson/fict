package com.murun.fict.dto;

import com.murun.fict.model.Address;

public class EntityAddressDTO {
    private Integer entityAddressId;
    private Integer addressTypeId;
    private Address address;
    private Integer legalEntityId;

    public EntityAddressDTO() {
    }

    public EntityAddressDTO(Integer entityAddressId, Integer addressTypeId, Address address, Integer legalEntityId) {
        this.entityAddressId = entityAddressId;
        this.addressTypeId = addressTypeId;
        this.address = address;
        this.legalEntityId = legalEntityId;
    }

    public Integer getEntityAddressId() {
        return entityAddressId;
    }

    public void setEntityAddressId(Integer entityAddressId) {
        this.entityAddressId = entityAddressId;
    }

    public Integer getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Integer addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(Integer legalEntityId) {
        this.legalEntityId = legalEntityId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityAddressDTO that = (EntityAddressDTO) o;

        if (!getEntityAddressId().equals(that.getEntityAddressId())) return false;
        if (!getAddressTypeId().equals(that.getAddressTypeId())) return false;
        if (!getAddress().equals(that.getAddress())) return false;
        return getLegalEntityId().equals(that.getLegalEntityId());
    }

    @Override
    public int hashCode() {
        int result = getEntityAddressId().hashCode();
        result = 31 * result + getAddressTypeId().hashCode();
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getLegalEntityId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EntityAddressDTO{" +
                "entityAddressId=" + entityAddressId +
                ", addressTypeId=" + addressTypeId +
                ", address=" + address +
                ", legalEntityId=" + legalEntityId +
                '}';
    }
}
