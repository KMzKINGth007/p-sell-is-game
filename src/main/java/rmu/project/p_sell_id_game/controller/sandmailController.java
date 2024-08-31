package rmu.project.p_sell_id_game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rmu.project.p_sell_id_game.model.OrderResponseModel;
import rmu.project.p_sell_id_game.service.SendMailService;

@RestController
@RequestMapping("/sandmail")
public class sandmailController {

    @Autowired
    private SendMailService sendMailService;

    @PostMapping("/sendReceipt")
    public void sendOrderMail(@RequestBody OrderResponseModel order, @RequestParam String message) {
        sendMailService.sendOrderMail(order, message);
    }
}
