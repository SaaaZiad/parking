package com.example.parking.repositories;

import com.example.parking.model.PSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PSpotRepo extends JpaRepository<PSpot, Long>{
  @Query("select o from PSpot o where o.isBooked=False")
  List<PSpot> findAllAvailable();
}
