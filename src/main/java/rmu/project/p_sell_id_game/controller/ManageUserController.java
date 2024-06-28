package rmu.project.p_sell_id_game.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rmu.project.p_sell_id_game.model.ResponseModel;
import rmu.project.p_sell_id_game.service.ManageUserService;

@RestController
@RequestMapping("/manage/user")
public class ManageUserController {

	@Autowired
	private ManageUserService manageUserService;

	@GetMapping("/getAllUser")
	public ResponseModel getAllUser() {
		ResponseModel response = new ResponseModel();

		try {
			// service
			response.setData(manageUserService.getAllUser());
			response.setStatus("SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			response.setStatus("ERROR");
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
