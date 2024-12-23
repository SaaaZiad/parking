package com.example.parking.service;


import com.example.parking.exception.NotFound;
import com.example.parking.model.PSpot;
import com.example.parking.repositories.PSpotRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PSpotService {
  private final PSpotRepo PSpotRepo;

  public List<PSpot> getPSpots() {
    return PSpotRepo.findAll();
  }

  public List<PSpot> getPSpotsAvailable(){
    List<PSpot> allPSpots = PSpotRepo.findAllAvailable();
    List<PSpot> readyPSpots = new ArrayList<>();
    for (PSpot PSpot : allPSpots) {
      if (!PSpot.isBooked()) {
        readyPSpots.add(PSpot);
      }
    }
    return readyPSpots;
  }

  public void savePSpot(PSpot PSpot) {
    PSpotRepo.save(PSpot);
  }

  public PSpot findById(Long pspotId) {
    return PSpotRepo.findById(pspotId).orElseThrow(() -> new NotFound("Error getting pspot"));
  }

}
