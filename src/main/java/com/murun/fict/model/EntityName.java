package com.murun.fict.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
@Table(name="ENTITY_NAME")
public class EntityName implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENTITY_NAME_ID")
  //  @JsonIgnore
    private Integer entityNameId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NAME_TYPE_ID", nullable = false)
    private NameType nameType;

    @Column(name = "NAME")
    private String Name;



    @ManyToOne
    @JoinColumn(name = "LEGAL_ENTITY_ID", nullable = true )
    @JsonBackReference
    private LegalEntity legalEntity;

    public EntityName() {
    }

    public Integer getEntityNameId() {
        return entityNameId;
    }

    public void setEntityNameId(Integer entityNameId) {
        this.entityNameId = entityNameId;
    }



    public NameType getNameType() {
        return nameType;
    }

    public void setNameType(NameType nameType) {
        this.nameType = nameType;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
    }

    @Override
    public String toString() {
        return "EntityName{" +
                "nameType=" + nameType +
                ", Name='" + Name + '\'' +
                '}';
    }
}

