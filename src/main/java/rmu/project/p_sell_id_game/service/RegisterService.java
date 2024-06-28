package rmu.project.p_sell_id_game.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rmu.project.p_sell_id_game.entity.UserDetailEntity;
import rmu.project.p_sell_id_game.entity.UserEntity;
import rmu.project.p_sell_id_game.model.UserRequestModel;
import rmu.project.p_sell_id_game.repository.UserDetailRepository;
import rmu.project.p_sell_id_game.repository.UserRepository;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Transactional
	public Integer save(UserRequestModel request) {
		
		Integer response = null;
		
		if(null != request) {
			
			UserEntity entity = userRepository.findByUserName(request.getUserName());
			
			if(null != entity) {
				return null;
			}

            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(request.getUserName());
            userEntity.setUserPassword(request.getUserPassword());
            userEntity.setRoleId(null != request.getRoleId() ? request.getRoleId() : 3);
            userEntity.setStatus("ACTIVE");
            userEntity.setCreateAt(new Date());
            userEntity.setUpdateAt(new Date());
            userEntity = userRepository.save(userEntity);

            response = userEntity.getUserId();

            if(null != userEntity) {
                UserDetailEntity userDetailEntity = new UserDetailEntity();
                userDetailEntity.setUserId(userEntity.getUserId());
                userDetailEntity.setFirstName(request.getFirstName());
                userDetailEntity.setLastName(request.getLastName());
                userDetailEntity.setAge(request.getAge());
                userDetailEntity.setPhone(request.getPhone());
                userDetailEntity.setEmail(request.getEmail());
                userDetailEntity.setLine(request.getLine());

                userDetailRepository.save(userDetailEntity);
            }

        }
        
        return response;
    }
}
