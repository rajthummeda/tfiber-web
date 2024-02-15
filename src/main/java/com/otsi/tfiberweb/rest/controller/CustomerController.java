package com.otsi.tfiberweb.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.tfiberweb.dto.ContactDTO;
import com.otsi.tfiberweb.dto.CustomerDTO;
import com.otsi.tfiberweb.dto.CustomerServiceDTO;
import com.otsi.tfiberweb.entities.Contact;
import com.otsi.tfiberweb.entities.Customer;
import com.otsi.tfiberweb.entities.CustomerService;
import com.otsi.tfiberweb.repository.CustomerRepository;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

	/* Create a new customer */
    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>("Customer has created Successfully with ID :: " + savedCustomer.getCustomerId(), HttpStatus.CREATED);
    }

	/* Update an existing customer */
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customerDetails) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Customer existingCustomer = optionalCustomer.get();
        existingCustomer.setFirstName(customerDetails.getFirstName());
        existingCustomer.setLastName(customerDetails.getLastName());
        // Set other properties accordingly

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

	/* Get a customer by ID */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        return optionalCustomer.map(customer -> new ResponseEntity<>(convertToDto(customer), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
    /* Get all customers */
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
	/* Delete a customer by ID */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerRepository.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private CustomerDTO convertToDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setAlternatePhoneNumber(customer.getAlternatePhoneNumber());
        dto.setDateOfBirth(customer.getDateOfBirth());
        dto.setGovtId(customer.getGovtId());
        // Assuming PlanDTO, OfferDTO, and ServiceDTO are available and properly implemented
        dto.setPlanId(customer.getPlan().getPlanId());
        dto.setOfferId(customer.getOffer().getOfferId());
        dto.setServiceId(customer.getService().getServiceId());
        // Convert contacts to ContactDTO
        dto.setContacts(customer.getContacts().stream()
                .map(this::convertToContactDto)
                .collect(Collectors.toList()));
        // Convert customerServices to CustomerServiceDTO
        dto.setCustomerServices(customer.getCustomerServices().stream()
                .map(this::convertToCustomerServiceDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private ContactDTO convertToContactDto(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setContactId(contact.getContactId());
        dto.setCustomerId(contact.getCustomer().getCustomerId());
        dto.setContactType(contact.getContactType());
        dto.setContactName(contact.getContactName());
        dto.setContactEmail(contact.getContactEmail());
        dto.setContactPhone(contact.getContactPhone());
        dto.setPincode(contact.getPincode());
        return dto;
    }
    
    private CustomerServiceDTO convertToCustomerServiceDto(CustomerService customerService) {
        CustomerServiceDTO customerServiceDTO = new CustomerServiceDTO();
        customerServiceDTO.setName(customerService.getName());
        customerServiceDTO.setPlanId(customerService.getPlan().getPlanId());
        customerServiceDTO.setServiceId(customerService.getService().getServiceId());
        customerServiceDTO.setOfferId(customerService.getOffer().getOfferId());
        customerServiceDTO.setStartDate(customerService.getStartDate());
        customerServiceDTO.setEndDate(customerService.getEndDate());
        customerServiceDTO.setCustomerId(customerService.getCustomer().getCustomerId());
        return customerServiceDTO;
    }
}