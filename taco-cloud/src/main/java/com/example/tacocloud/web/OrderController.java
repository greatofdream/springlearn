package com.example.tacocloud.web;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
//end::baseClass[]
import org.springframework.web.bind.annotation.PostMapping;
//tag::baseClass[]
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import com.example.tacocloud.Order;
import com.example.tacocloud.data.OrderRepository;

//@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
  private OrderRepository orderRepo;
  public OrderController(OrderRepository orderRepo) {
    this.orderRepo = orderRepo;
  }
//end::baseClass[]
//tag::orderForm[]
  @GetMapping("/current")
  public String orderForm(Model model) {
    //model.addAttribute("order", new Order());
    return "orderForm";
  }
//end::orderForm[]

/*
//tag::handlePost[]
  @PostMapping
  public String processOrder(Order order) {
    log.info("Order submitted: " + order);
    return "redirect:/";
  }
//end::handlePost[]
*/
  
//tag::handlePostWithValidation[]
  @PostMapping
  public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {// @Valid validation
    if (errors.hasErrors()) {
      return "orderForm";
    }
    orderRepo.save(order);
    sessionStatus.setComplete();// 重置session，清理掉旧订单

    //log.info("Order submitted: " + order);
    return "redirect:/";
  }
//end::handlePostWithValidation[]
  
//tag::baseClass[]
  
}
//end::baseClass[]
