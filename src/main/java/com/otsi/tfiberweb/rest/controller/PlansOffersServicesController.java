package com.otsi.tfiberweb.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.tfiberweb.dto.PlansOffersServicesDTO;
import com.otsi.tfiberweb.service.OfferService;
import com.otsi.tfiberweb.service.PlanService;
import com.otsi.tfiberweb.service.ServicesService;

@RestController
@RequestMapping("/api/v1/plans-offers-services")
public class PlansOffersServicesController {
	
	@Autowired
    private PlanService planService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private ServicesService serviceService;

    @GetMapping
    public PlansOffersServicesDTO getAllData() {
        PlansOffersServicesDTO dto = new PlansOffersServicesDTO();
        dto.setPlans(planService.getAllPlans());
        dto.setOffers(offerService.getAllOffers());
        dto.setServices(serviceService.getAllServices());
        return dto;
    }
}