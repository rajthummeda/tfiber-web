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

import com.otsi.tfiberweb.entities.Offer;
import com.otsi.tfiberweb.repository.OfferRepository;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

	/* Create a new offer */
    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        Offer savedOffer = offerRepository.save(offer);
        return new ResponseEntity<>(savedOffer, HttpStatus.CREATED);
    }

	/* Get all offers */
    @GetMapping
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

	/* Get an offer by ID */
    @GetMapping("/{offerId}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        return optionalOffer.map(offer -> new ResponseEntity<>(offer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

