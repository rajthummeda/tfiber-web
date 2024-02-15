package com.otsi.tfiberweb.rest.controller;

import java.util.List;
import java.util.Optional;

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

import com.otsi.tfiberweb.entities.Contact;
import com.otsi.tfiberweb.entities.Customer;
import com.otsi.tfiberweb.repository.ContactRepository;
import com.otsi.tfiberweb.repository.CustomerRepository;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

	@Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CustomerRepository customerRepository;

	/* Create a new contact */
    @PostMapping("/{customerId}")
    public ResponseEntity<String> createContactForCustomer(@PathVariable Long customerId, @RequestBody Contact contact) {
		/* Check if the customer exists */
        if (!customerRepository.existsById(customerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

		/* Set the customer for the contact */
        Customer customer = customerRepository.findById(customerId).get();
        contact.setCustomer(customer);

		/* Save the contact */
        contactRepository.save(contact);
        return new ResponseEntity<>("Contact is created.", HttpStatus.CREATED);
    }

	/* Update an existing contact */
    @PutMapping("/{customerId}")
    public ResponseEntity<String> updateContact(@PathVariable Long customerId, @RequestBody Contact contactDetails) {
    	/* Check if the customer exists */
        if (!customerRepository.existsById(customerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

		/* Retrieve the customer */
        Customer customer = customerRepository.findById(customerId).get();
        
		/* Retrieve the contact associated with the customer */
        Contact existingContact = contactRepository.findByCustomer(customer);

		/* Check if the contact exists */
        if (existingContact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingContact.setContactType(contactDetails.getContactType());
        existingContact.setContactName(contactDetails.getContactName());
        existingContact.setContactEmail(contactDetails.getContactEmail());
        existingContact.setContactPhone(contactDetails.getContactPhone());
        existingContact.setPincode(contactDetails.getPincode());

        contactRepository.save(existingContact);
        return new ResponseEntity<>("Contact is Updated.", HttpStatus.OK);
    }

	/* Get a contact by ID */
    @GetMapping("/{contactId}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long contactId) {
        Optional<Contact> optionalContact = contactRepository.findById(contactId);
        return optionalContact.map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

	/* Get all contacts */
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

	/* Delete a contact by ID */
    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long contactId) {
        Optional<Contact> optionalContact = contactRepository.findById(contactId);
        if (!optionalContact.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        contactRepository.deleteById(contactId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}