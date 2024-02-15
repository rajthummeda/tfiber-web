package com.otsi.tfiberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.tfiberweb.entities.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>{

}
