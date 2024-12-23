package com.example.parking.controllers;

import com.example.parking.dto.RentSpotDTO;
import com.example.parking.facade.AvailablePSpotsFacade;
import com.example.parking.exception.NotEnoughTickets;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/tickets")
public class AvailablePSpotsController {
  private final AvailablePSpotsFacade availablePSpotsFacade;
  @GetMapping
  public String getProductsPage(Model model){
    availablePSpotsFacade.getProductsPage(model);
    return "available_tickets";
  }
  @GetMapping("/buy_tickets/{ticketId}")
  public String getBuyPage(@PathVariable("ticketId") Long ticketId, Model model){
    availablePSpotsFacade.getBuyPage(model, ticketId);
    return "buy_tickets";
  }
  @PostMapping("/buy_tickets/{ticketId}")
  public String buyTicket(Model model,
                          @PathVariable("ticketId") Long ticketId,
                          @ModelAttribute("buyDTO") RentSpotDTO dto,
                          BindingResult bindingResult){
    if(bindingResult.hasErrors()){
      return "buy_tickets";
    }
    try{
      availablePSpotsFacade.buyProduct(ticketId, dto);
    }catch (NotEnoughTickets e){
      model.addAttribute("error", "Недостаточно билетов для приобретения");
      return "buy_tickets";
    }
    return "redirect:/tickets";
  }
}

