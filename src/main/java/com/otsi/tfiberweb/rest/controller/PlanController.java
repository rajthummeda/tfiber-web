package com.otsi.tfiberweb.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.otsi.tfiberweb.entities.Plan;
import com.otsi.tfiberweb.repository.PlanRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/plans")
public class PlanController {

    @Autowired
    private PlanRepository planRepository;

	/* Create a new plan */
    @PostMapping
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        Plan savedPlan = planRepository.save(plan);
        return new ResponseEntity<>(savedPlan, HttpStatus.CREATED);
    }

	/* Get all plans */
    @GetMapping
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

	/* Get a plan by ID */
    @GetMapping("/{planId}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long planId) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        return optionalPlan.map(plan -> new ResponseEntity<>(plan, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

	/* Update a plan by ID */
    @PutMapping("/{planId}")
    public ResponseEntity<Plan> updatePlan(@PathVariable Long planId, @RequestBody Plan planDetails) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        if (!optionalPlan.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Plan existingPlan = optionalPlan.get();
        existingPlan.setName(planDetails.getName());
        existingPlan.setDescription(planDetails.getDescription());
        existingPlan.setPrice(planDetails.getPrice());
        existingPlan.setDataLimit(planDetails.getDataLimit());

        Plan updatedPlan = planRepository.save(existingPlan);
        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }
}