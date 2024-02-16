package com.otsi.tfiberweb.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.tfiberweb.entities.Plans;
import com.otsi.tfiberweb.repository.PlansRepository;

@RestController
@RequestMapping("/api/v1/plans")
public class PlansController {

    @Autowired
    private PlansRepository planRepository;

	/* Create a new plan */
    @PostMapping
    public ResponseEntity<Plans> createPlan(@RequestBody Plans plan) {
        Plans savedPlan = planRepository.save(plan);
        return new ResponseEntity<>(savedPlan, HttpStatus.CREATED);
    }

	/* Get all plans */
    @GetMapping
    public List<Plans> getAllPlans() {
        return planRepository.findAll();
    }

	/* Get a plan by ID */
    @GetMapping("/{planId}")
    public ResponseEntity<Plans> getPlanById(@PathVariable Long planId) {
        Optional<Plans> optionalPlan = planRepository.findById(planId);
        return optionalPlan.map(plan -> new ResponseEntity<>(plan, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

	/* Update a plan by ID */
    @PutMapping("/{planId}")
    public ResponseEntity<Plans> updatePlan(@PathVariable Long planId, @RequestBody Plans planDetails) {
        Optional<Plans> optionalPlan = planRepository.findById(planId);
        if (!optionalPlan.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Plans existingPlan = optionalPlan.get();
        existingPlan.setName(planDetails.getName());
        existingPlan.setPrice(planDetails.getPrice());
        existingPlan.setDataLimit(planDetails.getDataLimit());

        Plans updatedPlan = planRepository.save(existingPlan);
        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }
}