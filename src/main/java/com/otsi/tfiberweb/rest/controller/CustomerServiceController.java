package com.otsi.tfiberweb.rest.controller;

import java.util.ArrayList;
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

import com.otsi.tfiberweb.dto.CustomerServiceDTO;
import com.otsi.tfiberweb.entities.Customer;
import com.otsi.tfiberweb.entities.CustomerService;
import com.otsi.tfiberweb.entities.Offer;
import com.otsi.tfiberweb.entities.Plan;
import com.otsi.tfiberweb.entities.Service;
import com.otsi.tfiberweb.repository.CustomerRepository;
import com.otsi.tfiberweb.repository.CustomerServiceRepository;
import com.otsi.tfiberweb.repository.OfferRepository;
import com.otsi.tfiberweb.repository.PlanRepository;
import com.otsi.tfiberweb.repository.ServiceRepository;

@RestController
@RequestMapping("/api/v1/customer-services")
public class CustomerServiceController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerServiceRepository customerServiceRepository;

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private OfferRepository offerRepository;

	/* Create a new customer service */
	@PostMapping
	public ResponseEntity<String> createCustomerService(@RequestBody CustomerServiceDTO customerServiceDTO) {
		// Retrieve plan, service, and offer by their IDs
		Plan plan = planRepository.findById(customerServiceDTO.getPlanId()).orElse(null);
		Service service = serviceRepository.findById(customerServiceDTO.getServiceId()).orElse(null);
		Offer offer = offerRepository.findById(customerServiceDTO.getOfferId()).orElse(null);

		// Check if plan, service, and offer exist
		if (plan == null || service == null || offer == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		/* Check if the customer exists */
		if (!customerRepository.existsById(customerServiceDTO.getCustomerId())) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		/* Set the customer for the contact */
		Customer customer = customerRepository.findById(customerServiceDTO.getCustomerId()).get();

		/* Create a new customer service */
		CustomerService customerService = new CustomerService();
		customerService.setName(customerServiceDTO.getName());
		customerService.setDataLimit(plan.getDataLimit());
		customerService.setPrice(plan.getPrice());
		customerService.setStartDate(customerServiceDTO.getStartDate());
		customerService.setEndDate(customerServiceDTO.getEndDate());
		customerService.setPlan(plan);
		customerService.setService(service);
		customerService.setOffer(offer);
		customerService.setCustomer(customer);

		/* Save the customer service */
		customerServiceRepository.save(customerService);

		return new ResponseEntity<>("Customer Service is Created.", HttpStatus.CREATED);
	}

	/* Update an existing customer service */
	@PutMapping("/{serviceId}")
	public ResponseEntity<String> updateCustomerService(@PathVariable Long serviceId,
			@RequestBody CustomerServiceDTO customerServiceDTO) {
		Optional<CustomerService> optionalCustomerService = customerServiceRepository.findById(serviceId);
		if (!optionalCustomerService.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// Retrieve plan, service, and offer by their IDs
		Plan plan = planRepository.findById(customerServiceDTO.getPlanId()).orElse(null);
		Service service = serviceRepository.findById(customerServiceDTO.getServiceId()).orElse(null);
		Offer offer = offerRepository.findById(customerServiceDTO.getOfferId()).orElse(null);

		// Check if plan, service, and offer exist
		if (plan == null || service == null || offer == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		/* Check if the customer exists */
		if (!customerRepository.existsById(customerServiceDTO.getCustomerId())) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		/* Set the customer for the contact */
		Customer customer = customerRepository.findById(customerServiceDTO.getCustomerId()).get();

		/* Create a new customer service */
		CustomerService customerService = new CustomerService();
		customerService.setName(customerServiceDTO.getName());
		customerService.setDataLimit(plan.getDataLimit());
		customerService.setPrice(plan.getPrice());
		customerService.setStartDate(customerServiceDTO.getStartDate());
		customerService.setEndDate(customerServiceDTO.getEndDate());
		customerService.setPlan(plan);
		customerService.setService(service);
		customerService.setOffer(offer);
		customerService.setCustomer(customer);

		/* Save the customer service */
		customerServiceRepository.save(customerService);

		return new ResponseEntity<>("Customer Service is Updated.", HttpStatus.CREATED);
	}

	/* Delete a customer service by ID */
	@DeleteMapping("/{serviceId}")
	public ResponseEntity<Void> deleteCustomerService(@PathVariable Long serviceId) {
		Optional<CustomerService> optionalCustomerService = customerServiceRepository.findById(serviceId);
		if (!optionalCustomerService.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		customerServiceRepository.deleteById(serviceId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/* Get a customer service by ID */
	@GetMapping("/{serviceId}")
	public ResponseEntity<CustomerServiceDTO> getCustomerService(@PathVariable Long serviceId) {
		CustomerService customerService = customerServiceRepository.findById(serviceId).orElse(null);
        if (customerService == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerServiceDTO customerServiceDTO = mapToCustomerServiceDTO(customerService);
        return new ResponseEntity<>(customerServiceDTO, HttpStatus.OK);
	}

	/* Get all customer services */
	@GetMapping
    public ResponseEntity<List<CustomerServiceDTO>> getAllCustomerServices() {
        List<CustomerService> customerServices = customerServiceRepository.findAll();
        if (customerServices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CustomerServiceDTO> customerServiceDTOs = new ArrayList<>();
        for (CustomerService customerService : customerServices) {
            customerServiceDTOs.add(mapToCustomerServiceDTO(customerService));
        }

        return new ResponseEntity<>(customerServiceDTOs, HttpStatus.OK);
    }
	
	private CustomerServiceDTO mapToCustomerServiceDTO(CustomerService customerService) {
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