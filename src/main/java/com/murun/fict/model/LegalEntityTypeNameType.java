package com.murun.fict.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;


@javax.persistence.Entity
@Table(name="LEGAL_ENTITY_TYPE_NAME_TYPES")
public class LegalEntityTypeNameType implements Serializable {

    @Id
    @Column(name = "LEGAL_ENTITY_TYPE_NAMES_TYPE_ID")
    @JsonIgnore
    private Integer legalEntityTypeNamesTypeId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEGAL_ENTITY_TYPE_ID", nullable = false)
    private LegalEntityType legalEntityType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NAME_TYPE_ID", nullable = false)
    private NameType nameType;

    public Integer getLegalEntityTypeNamesTypeId() {
        return legalEntityTypeNamesTypeId;
    }

    public void setLegalEntityTypeNamesTypeId(Integer legalEntityTypeNamesTypeId) {
        this.legalEntityTypeNamesTypeId = legalEntityTypeNamesTypeId;
    }


    public LegalEntityTypeNameType() {
    }

    public LegalEntityTypeNameType(Integer legalEntityTypeNamesTypeId, LegalEntityType legalEntityType, NameType nameType) {
        this.legalEntityTypeNamesTypeId = legalEntityTypeNamesTypeId;
        this.legalEntityType = legalEntityType;
        this.nameType = nameType;
    }

    public LegalEntityType getLegalEntityType() {
        return legalEntityType;
    }

    public void setLegalEntityType(LegalEntityType legalEntityType) {
        this.legalEntityType = legalEntityType;
    }

    public NameType getNameType() {
        return nameType;
    }

    public void setNameType(NameType nameType) {
        this.nameType = nameType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegalEntityTypeNameType that = (LegalEntityTypeNameType) o;

        if (!getLegalEntityTypeNamesTypeId().equals(that.getLegalEntityTypeNamesTypeId())) return false;
        if (!getLegalEntityType().equals(that.getLegalEntityType())) return false;
        return getNameType().equals(that.getNameType());
    }

    @Override
    public int hashCode() {
        int result = getLegalEntityTypeNamesTypeId().hashCode();
        result = 31 * result + getLegalEntityType().hashCode();
        result = 31 * result + getNameType().hashCode();
        return result;
    }
}
