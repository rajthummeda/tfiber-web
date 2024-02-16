package com.otsi.tfiberweb.dto;

import java.util.List;

public class PlansOffersServicesDTO {

	private List<PlanDTO> plans;
	private List<OfferDTO> offers;
	private List<ServiceDTO> services;

	public List<PlanDTO> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanDTO> plans) {
		this.plans = plans;
	}

	public List<OfferDTO> getOffers() {
		return offers;
	}

	public void setOffers(List<OfferDTO> offers) {
		this.offers = offers;
	}

	public List<ServiceDTO> getServices() {
		return services;
	}

	public void setServices(List<ServiceDTO> services) {
		this.services = services;
	}

}