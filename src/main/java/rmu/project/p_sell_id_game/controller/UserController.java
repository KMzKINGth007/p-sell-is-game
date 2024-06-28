package rmu.project.p_sell_id_game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import rmu.project.p_sell_id_game.model.ResponseModel;
import rmu.project.p_sell_id_game.model.UserRequestModel;
import rmu.project.p_sell_id_game.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update/{userId}")
    public ResponseModel update(@RequestBody UserRequestModel request, @PathVariable Integer userId) {

        ResponseModel response = new ResponseModel();

        try {
            response.setData(userService.update(request, userId));
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/getById")
    public ResponseModel getById(@RequestParam(name = "userId") Integer userId) {

        ResponseModel response = new ResponseModel();

        try {
            response.setData(userService.getById(userId));
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/delete")
    public ResponseModel delete(@RequestParam(name = "userId") Integer userId) {

        ResponseModel response = new ResponseModel();

        try {
            response.setData(userService.delete(userId));
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }

        return response;
    }

}
