package com.example.parking.controllers;


import com.example.parking.dto.ConfirmAdminDTO;
import com.example.parking.facade.AdminFacade;
import com.example.parking.model.PSpot;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
  private final AdminFacade adminFacade;

  @GetMapping("/confirm")
  public String getConfirmPage(Model model) {
    adminFacade.getConfirmPage(model);
    return "admin/admin_confirm";
  }

  @PostMapping("/confirm")
  public String confirmAdmin(Model model,
                             @ModelAttribute("confirmAdminDTO") @Valid ConfirmAdminDTO confirmAdminDTO,
                             BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "admin/admin_confirm";
    }
    adminFacade.confirmAdmin(confirmAdminDTO.getConfirmKey(), model);
    if (model.getAttribute("errorConfirming") != null) {
      return "admin/admin_confirm";
    } else {
      return "redirect:/admin/available_tickets";
    }
  }

  @GetMapping
  public String getMainPage() {
    return "admin/available_tickets";
  }

  @GetMapping("/available_tickets")
  public String getPSpotPage(Model model) {
    adminFacade.getTicketsPage(model);
    return "admin/admin_available_tickets";
  }

  @GetMapping("/available_tickets/create")
  public String getCreatePSpotPage(Model model) {
    adminFacade.getCreatePSpotPage(model);
    return "admin/admin_create_ticket";
  }

  @PostMapping("/available_tickets/create")
  public String createTicket(@ModelAttribute("pspot") @Valid PSpot PSpot,
                             BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return "admin/admin_create_ticket";
    }
    adminFacade.createPSpot(PSpot);
    return "redirect:/admin/available_tickets";
  }


}