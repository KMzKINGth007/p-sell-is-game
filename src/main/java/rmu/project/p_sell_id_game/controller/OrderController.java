package rmu.project.p_sell_id_game.controller;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rmu.project.p_sell_id_game.model.OrderRequestModel;
import rmu.project.p_sell_id_game.model.OrderResponseModel;
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
            response.setData(orderService.addOrder(request));
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("/getOrderDetails/{orderId}")
    public ResponseModel getOrderDetails(@PathVariable Integer orderId) {
        ResponseModel response = new ResponseModel();
        try {
            OrderResponseModel orderDetails = orderService.getOrderDetails(orderId);
            if (orderDetails != null) {
                response.setData(orderDetails);
                response.setStatus("SUCCESS");
            } else {
                response.setStatus("ERROR");
                response.setMessage("Order not found");
            }
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("/getAllOrders")
    public ResponseModel getAllOrders() {
        ResponseModel response = new ResponseModel();
        try {
            List<OrderResponseModel> orders = orderService.getAllOrders();
            response.setData(orders);
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("/getOrdersByUserId/{userDetailId}")
    public ResponseModel getOrdersByUserId(@PathVariable Integer userDetailId) {
        ResponseModel response = new ResponseModel();
        try {
            List<OrderResponseModel> orders = orderService.getOrdersByUserId(userDetailId);
            if (!orders.isEmpty()) {
                response.setData(orders);
                response.setStatus("SUCCESS");
            } else {
                response.setStatus("ERROR");
                response.setMessage("No orders found for the given user ID");
            }
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseModel deleteOrder(@PathVariable Integer orderId) {
        ResponseModel response = new ResponseModel();
        try {
            orderService.deleteOrder(orderId);
            response.setStatus("SUCCESS");
            response.setMessage("Order deleted successfully");
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping(value = ("/saveOrderImg/{orderId}"), consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel saveOrderImage(@RequestParam("file") MultipartFile file, @PathVariable Integer orderId) {

        ResponseModel response = new ResponseModel();

        try {
            response.setData(orderService.saveOrderImage(file, orderId));
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            // TODO: handle exception
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/getOrderImgByOrderId")
    public ResponseModel getOrderImgByOrderId(@RequestParam(name = "orderId") Integer orderId) {
        ResponseModel response = new ResponseModel();

        try {
            response.setData(orderService.getOrderImgByOrderId(orderId));
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            // TODO: handle exception
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/getImageByte")
    public ResponseEntity<byte[]> getImageByte(@RequestParam(name = "fileName") String fileName)
            throws IOException, DataFormatException {

        return ResponseEntity.ok(orderService.getImageByte(fileName));
    }

    @DeleteMapping(value = ("/deleteOrderImgByFileName"))
    public ResponseModel deleteImgByFileName(@RequestParam(name = "fileName") String fileName) {

        ResponseModel response = new ResponseModel();

        try {
            orderService.deleteImgByFileName(fileName);
            response.setData("SUCCESS");
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            // TODO: handle exception
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @PutMapping("/updateOrder/{orderId}")
    public ResponseModel updateOrder(@PathVariable Integer orderId, @RequestBody OrderRequestModel request) {
        ResponseModel response = new ResponseModel();
        try {
            orderService.updateOrder(request, orderId);
            response.setStatus("SUCCESS");
            response.setMessage("Order updated successfully");
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
