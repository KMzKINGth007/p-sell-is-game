package rmu.project.p_sell_id_game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rmu.project.p_sell_id_game.model.OrderRequestModel;
import rmu.project.p_sell_id_game.model.ResponseModel;
import rmu.project.p_sell_id_game.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseModel addOrder(@RequestBody OrderRequestModel request) {
        ResponseModel response = new ResponseModel();
        try {
            // service
            response.setData(orderService.addOrder(request));
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            // handle exception
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
