package com.murun.fict.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@javax.persistence.Entity
@Table(name="LEGAL_ENTITY_TYPE")
public class LegalEntityType implements Serializable {

    @Id
    @Column(name = "LEGAL_ENTITY_TYPE_ID")
    @JsonIgnore
    private Integer legalEntityTypeId;

    @Column(name = "LEGAL_ENTITY_TYPE_TEXT")
    private String legalEntityTypeText;


    public LegalEntityType() {
    }

    public LegalEntityType(Integer legalEntityTypeId, String legalEntityTypeText) {
        this.legalEntityTypeId = legalEntityTypeId;
        this.legalEntityTypeText = legalEntityTypeText;
    }

    public Integer getLegalEntityTypeId() {
        return legalEntityTypeId;
    }

    public void setLegalEntityTypeId(Integer legalEntityTypeId) {
        this.legalEntityTypeId = legalEntityTypeId;
    }

    public String getLegalEntityTypeText() {
        return legalEntityTypeText;
    }

    public void setLegalEntityTypeText(String legalEntityTypeText) {
        this.legalEntityTypeText = legalEntityTypeText;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegalEntityType that = (LegalEntityType) o;

        if (getLegalEntityTypeId() != that.getLegalEntityTypeId()) return false;
        return getLegalEntityTypeText() != null ? getLegalEntityTypeText().equals(that.getLegalEntityTypeText()) : that.getLegalEntityTypeText() == null;

    }

    @Override
    public int hashCode() {
        int result = getLegalEntityTypeId();
        result = 31 * result + (getLegalEntityTypeText() != null ? getLegalEntityTypeText().hashCode() : 0);
        return result;
    }
}
