package com.otsi.tfiberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.tfiberweb.entities.CustomerService;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerService, Long>{

}
