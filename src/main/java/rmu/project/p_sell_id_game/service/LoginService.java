package rmu.project.p_sell_id_game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rmu.project.p_sell_id_game.entity.UserDetailEntity;
import rmu.project.p_sell_id_game.entity.UserEntity;
import rmu.project.p_sell_id_game.model.LoginResponseModel;
import rmu.project.p_sell_id_game.repository.UserDetailRepository;
import rmu.project.p_sell_id_game.repository.UserRepository;



@Service
public class LoginService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserDetailRepository userDetailRepository;
	
	
	public LoginResponseModel authen(String userName, String userPassword) {
		
		LoginResponseModel response = null;
		
		UserEntity userEntity = userRepository.findByUserNameAndPassword(userName, userPassword);
		
		if(null != userEntity) {
			response = new LoginResponseModel();
			
			response.setUserId(userEntity.getUserId());
			response.setUserName(userEntity.getUserName());
			response.setUserPassword(userEntity.getUserPassword());
			response.setRoleId(userEntity.getRoleId());
			response.setStatus(userEntity.getStatus());
			
			UserDetailEntity userDetailEntity = userDetailRepository.findByUserId(userEntity.getUserId());
			if(null != userDetailEntity) {
				response.setFirstName(userDetailEntity.getFirstName());
				response.setLastName(userDetailEntity.getLastName());
				response.setAge(userDetailEntity.getAge());
				response.setPhone(userDetailEntity.getPhone());
				response.setEmail(userDetailEntity.getEmail());
				response.setLine(userDetailEntity.getLine());
			}
			
		}

		
		return response;
	}

}
