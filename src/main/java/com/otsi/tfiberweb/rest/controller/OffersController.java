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

import com.otsi.tfiberweb.entities.Offers;
import com.otsi.tfiberweb.repository.OffersRepository;

@RestController
@RequestMapping("/api/v1/offers")
public class OffersController {

    @Autowired
    private OffersRepository offerRepository;

	/* Create a new offer */
    @PostMapping
    public ResponseEntity<Offers> createOffer(@RequestBody Offers offer) {
        Offers savedOffer = offerRepository.save(offer);
        return new ResponseEntity<>(savedOffer, HttpStatus.CREATED);
    }

	/* Get all offers */
    @GetMapping
    public List<Offers> getAllOffers() {
        return offerRepository.findAll();
    }

	/* Get an offer by ID */
    @GetMapping("/{offerId}")
    public ResponseEntity<Offers> getOfferById(@PathVariable Long offerId) {
        Optional<Offers> optionalOffer = offerRepository.findById(offerId);
        return optionalOffer.map(offer -> new ResponseEntity<>(offer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

