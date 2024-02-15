package com.otsi.tfiberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.tfiberweb.entities.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>{

}
