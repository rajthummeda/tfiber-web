package com.otsi.tfiberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.tfiberweb.entities.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

}
