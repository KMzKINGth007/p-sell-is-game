package rmu.project.p_sell_id_game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rmu.project.p_sell_id_game.model.ResponseModel;
import rmu.project.p_sell_id_game.model.UserRequestModel;
import rmu.project.p_sell_id_game.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/insert")
    public ResponseModel save(@RequestBody UserRequestModel request) {

        ResponseModel response = new ResponseModel();

        try {
            // service
            response.setData(registerService.save(request));
            response.setStatus("SUCCESS");
        } catch (Exception e) {
            // TODO: handle exception
            response.setStatus("ERROR");
            response.setMessage(e.getMessage());
        }

        return response;
    }

}
