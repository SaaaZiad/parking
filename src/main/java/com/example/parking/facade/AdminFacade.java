package com.example.parking.facade;


import com.example.parking.dto.ConfirmAdminDTO;
import com.example.parking.model.PSpot;
import com.example.parking.model.Role;
import com.example.parking.security.AuthenticationUtils;
import com.example.parking.service.PSpotService;
import com.example.parking.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@AllArgsConstructor
@Component
@PropertySource("classpath:application.yml")
public class AdminFacade {
  private final PSpotService PSpotService;
  private final UserService userService;
  private final String secret = "adminpassword";

  public void getConfirmPage(Model model) {
    model.addAttribute("confirmAdminDTO", new ConfirmAdminDTO());
  }

  public void confirmAdmin(String confirmKey, Model model) {
    Long userId = AuthenticationUtils.getUserId();
    if (confirmKey.equals(secret)) {
      var user = userService.findUserById(userId);
      user.setRole(Role.ADMIN);
      userService.saveUser(user);
      AuthenticationUtils.updateRole(Role.ADMIN);
    } else {
      model.addAttribute("errorConfirming", "Wrong confirm code");
    }
  }

  public void getTicketsPage(Model model){
    model.addAttribute("pspots", PSpotService.getPSpots());
  }

  public void getCreatePSpotPage(Model model) {
    model.addAttribute("pspot", new PSpot());
  }

  public void createPSpot(PSpot PSpot) throws Exception{
    PSpotService.savePSpot(PSpot);
  }
}
