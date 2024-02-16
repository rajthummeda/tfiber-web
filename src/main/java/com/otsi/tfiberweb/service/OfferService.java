package com.otsi.tfiberweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otsi.tfiberweb.dto.OfferDTO;
import com.otsi.tfiberweb.dto.PlanDTO;
import com.otsi.tfiberweb.dto.ServiceDTO;
import com.otsi.tfiberweb.entities.Offers;
import com.otsi.tfiberweb.entities.Plans;
import com.otsi.tfiberweb.entities.Services;
import com.otsi.tfiberweb.repository.OffersRepository;

@Service
public class OfferService {

    @Autowired
    private OffersRepository offerRepository;

    public List<OfferDTO> getAllOffers() {
        List<Offers> offers = offerRepository.findAll();
        return offers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OfferDTO convertToDto(Offers offer) {
        OfferDTO dto = new OfferDTO();
        dto.setOfferId(offer.getOfferId());
        dto.setPlan(convertToDto(offer.getPlan()));
        dto.setService(convertToDto(offer.getService()));
        dto.setDiscount(offer.getDiscount());
        return dto;
    }

    private PlanDTO convertToDto(Plans plan) {
        PlanDTO dto = new PlanDTO();
        dto.setPlanId(plan.getPlanId());
        dto.setName(plan.getName());
        dto.setSpeed(plan.getSpeed());
        dto.setPrice(plan.getPrice());
        return dto;
    }

    private ServiceDTO convertToDto(Services service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setServiceId(service.getServiceId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        return dto;
    }
}
