package com.murun.fict.repository;

import com.murun.fict.model.Address;
import com.murun.fict.model.EntityAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AddressRepository extends JpaRepository<Address, Integer> {



}




