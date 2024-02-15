package com.otsi.tfiberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.tfiberweb.entities.Contact;
import com.otsi.tfiberweb.entities.Customer;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

	Contact findByCustomer(Customer customer);

}
