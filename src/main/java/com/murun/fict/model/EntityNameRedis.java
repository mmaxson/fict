package com.murun.fict.model;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.io.Serializable;


public class EntityNameRedis implements Serializable {

    @org.springframework.data.annotation.Id
    private Integer entityNameId;

    private NameType nameType;


    private String name;




    public EntityNameRedis() {
    }

    public EntityNameRedis(Integer entityNameId, NameType nameType, String name) {
        this.entityNameId = entityNameId;
        this.nameType = nameType;
        this.name = name;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityNameRedis that = (EntityNameRedis) o;

        if (!getEntityNameId().equals(that.getEntityNameId())) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getEntityNameId().hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EntityName{" +
                "nameType=" + nameType +
                ", name='" + name + '\'' +
                '}';
    }
}

