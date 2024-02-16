package com.otsi.tfiberweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otsi.tfiberweb.dto.PlanDTO;
import com.otsi.tfiberweb.entities.Plans;
import com.otsi.tfiberweb.repository.PlansRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {

    @Autowired
    private PlansRepository planRepository;

    public List<PlanDTO> getAllPlans() {
        List<Plans> plans = planRepository.findAll();
        return plans.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PlanDTO convertToDto(Plans plan) {
        PlanDTO dto = new PlanDTO();
        dto.setPlanId(plan.getPlanId());
        dto.setName(plan.getName());
        dto.setSpeed(plan.getSpeed());
        dto.setPrice(plan.getPrice());
        return dto;
    }
}
