package com.murun.fict.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Immutable
@javax.persistence.Entity
@Table(name="NAME_TYPE")
public class NameType implements Serializable {

    @Id
    @Column(name = "NAME_TYPE_ID")
    private Integer nameTypeId;

    @JsonProperty("name type")
    @Column(name = "NAME_TYPE_TEXT")
    private String nameTypeText;


    public NameType() {
    }

    public Integer getNameTypeId() {
        return nameTypeId;
    }

    public void setNameTypeId(Integer nameTypeId) {
        this.nameTypeId = nameTypeId;
    }

    public String getNameTypeText() {
        return nameTypeText;
    }

    public void setNameTypeText(String nameTypeText) {
        this.nameTypeText = nameTypeText;
    }

    @Override
    public String toString() {
        return "NameType{" +
                "nameTypeText='" + nameTypeText + '\'' +
                '}';
    }
}

