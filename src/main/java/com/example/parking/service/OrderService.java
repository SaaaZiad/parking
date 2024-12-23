package com.example.parking.service;

import com.example.parking.model.Order;
import com.example.parking.repositories.OrderRepo;
import com.example.parking.repositories.PSpotRepo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
  private final OrderRepo orderRepo;
  private final PSpotRepo PSpotRepo;

  public void saveOrder(Order order){
    orderRepo.save(order);
  }
  public List<Order> findByUserId(Long userId){
    return orderRepo.findOrdersByUserId(userId);
  }
  public void deleteOrder(Order order) {orderRepo.delete(order);}
  @Scheduled(fixedRate = 10000)
  public void refreshRemainTime() {
    List<Order> orders = orderRepo.findAll();
    for (Order order : orders) {
      if (order.getAmount() == 0) {
        order.getPspot().setBooked(false);
        PSpotRepo.save(order.getPspot());
        deleteOrder(order);
      }
      else {
        order.setAmount(order.getAmount() - 1);
        saveOrder(order);
      }
    }
  }
}
