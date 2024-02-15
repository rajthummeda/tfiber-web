package com.otsi.tfiberweb.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.tfiberweb.entities.Service;
import com.otsi.tfiberweb.repository.ServiceRepository;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

	/* Create a new service */
    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        Service savedService = serviceRepository.save(service);
        return new ResponseEntity<>(savedService, HttpStatus.CREATED);
    }

	/* Get all services */
    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

	/* Get a service by ID */
    @GetMapping("/{serviceId}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long serviceId) {
        Optional<Service> optionalService = serviceRepository.findById(serviceId);
        return optionalService.map(service -> new ResponseEntity<>(service, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}