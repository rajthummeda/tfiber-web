package com.otsi.tfiberweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otsi.tfiberweb.dto.ServiceDTO;
import com.otsi.tfiberweb.entities.Services;
import com.otsi.tfiberweb.repository.ServicesRepository;

@Service
public class ServicesService {

    @Autowired
    private ServicesRepository serviceRepository;

    public List<ServiceDTO> getAllServices() {
        List<Services> services = serviceRepository.findAll();
        return services.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ServiceDTO convertToDto(Services service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setServiceId(service.getServiceId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        return dto;
    }
}