package com.example.parking.facade;


import com.example.parking.dto.RentSpotDTO;
import com.example.parking.exception.NotEnoughMoney;
import com.example.parking.exception.NotEnoughTickets;
import com.example.parking.model.PSpot;
import com.example.parking.model.Order;
import com.example.parking.model.User;
import com.example.parking.security.AuthenticationUtils;
import com.example.parking.service.PSpotService;
import com.example.parking.service.OrderService;
import com.example.parking.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@AllArgsConstructor
@Component
public class AvailablePSpotsFacade {
  private final PSpotService PSpotService;
  private final OrderService orderService;
  private final UserService userService;

  public void getProductsPage(Model model) {
    model.addAttribute("pspots", PSpotService.getPSpotsAvailable());
  }

  public void getBuyPage(Model model, Long pspotId) {
    model.addAttribute("pspotId", pspotId);
    model.addAttribute("buyDTO", new RentSpotDTO());
  }

  public void buyProduct(Long ticketId, RentSpotDTO dto) throws NotEnoughTickets {
    PSpot PSpot = PSpotService.findById(ticketId);
    User user = userService.findUserById(AuthenticationUtils.getUserId());
    checkEnoughMoney(user, PSpot, dto);
    user.setBalance(user.getBalance()-dto.getAmount()* PSpot.getPrice());
    PSpot.setBooked(true);
    var order = createOrder(dto, user, PSpot);
    PSpot.getOrders().add(order);
    user.getOrders().add(order);
    userService.saveUser(user);
    PSpotService.savePSpot(PSpot);
    orderService.saveOrder(order);
  }

  private void checkEnoughMoney(User user, PSpot PSpot, RentSpotDTO dto) {
    if (user.getBalance() < PSpot.getPrice() * dto.getAmount()) {
      throw new NotEnoughMoney("Not enough money to buy this amount");
    }
  }


  private Order createOrder(RentSpotDTO dto, User user, PSpot PSpot){
    Order order = new Order();
    order.setAmount(dto.getAmount());
    order.setUser(user);
    order.setPspot(PSpot);
    return order;
  }
}
