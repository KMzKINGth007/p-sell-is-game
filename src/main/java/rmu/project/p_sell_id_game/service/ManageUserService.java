package rmu.project.p_sell_id_game.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rmu.project.p_sell_id_game.entity.UserDetailEntity;
import rmu.project.p_sell_id_game.entity.UserEntity;
import rmu.project.p_sell_id_game.model.ManageUserResponseModel;
import rmu.project.p_sell_id_game.repository.UserDetailRepository;
import rmu.project.p_sell_id_game.repository.UserRepository;

@Service
public class ManageUserService {

    @Autowired
	UserRepository userRepository;
	
	@Autowired
	UserDetailRepository userDetailRepository;
	
	public List<ManageUserResponseModel> getAllUser(){
		
		List<ManageUserResponseModel> response = null;
		
		List<UserEntity> userEntityList = userRepository.findAllUser();
		
		if(null != userEntityList) {
			
			response = new ArrayList<>();
			
			for(UserEntity user : userEntityList) {
				
                ManageUserResponseModel responseObject = new ManageUserResponseModel();

                responseObject.setUserId(user.getUserId());
                responseObject.setUserName(user.getUserName());
                responseObject.setUserPassword(user.getUserPassword());
                responseObject.setRoleId(user.getRoleId());
                responseObject.setStatus(user.getStatus());
                responseObject.setCreateAt(user.getCreateAt());
                responseObject.setUpdateAt(user.getUpdateAt());
				
				UserDetailEntity userDetail = userDetailRepository.findByUserId(user.getUserId());
                
                if(null != userDetail) {
                    responseObject.setFirstName(userDetail.getFirstName());
                    responseObject.setLastName(userDetail.getLastName());
                    responseObject.setAge(userDetail.getAge());
                    responseObject.setPhone(userDetail.getPhone());
                    responseObject.setEmail(userDetail.getEmail());
                    responseObject.setLine(userDetail.getLine());
                }

				response.add(responseObject);
			}
		}
		
		
		return response;
	}
}
