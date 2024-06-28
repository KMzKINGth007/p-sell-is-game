package rmu.project.p_sell_id_game.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rmu.project.p_sell_id_game.entity.UserDetailEntity;
import rmu.project.p_sell_id_game.entity.UserEntity;
import rmu.project.p_sell_id_game.model.UserRequestModel;
import rmu.project.p_sell_id_game.model.UserResponseModel;
import rmu.project.p_sell_id_game.repository.UserDetailRepository;
import rmu.project.p_sell_id_game.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Transactional
    public Integer update(UserRequestModel request, Integer userId) {

        Integer response = null;

        if (null != request) {

            Optional<UserEntity> userEntity = userRepository.findById(userId);

            if (userEntity.isPresent()) {
                UserEntity user = userEntity.get();

                response = user.getUserId();

                user.setUserPassword(null != request.getUserPassword() ? request.getUserPassword() : user.getUserPassword());
                user.setStatus(null != request.getStatus() ? request.getStatus() : user.getStatus());
                user.setUpdateAt(new Date());

                userRepository.save(user);

                UserDetailEntity userDetail = userDetailRepository.findByUserId(userId);

                if(null != userDetail) {
                    userDetail.setFirstName(null != request.getFirstName() ? request.getFirstName() : userDetail.getFirstName());
                    userDetail.setLastName(null != request.getLastName() ? request.getLastName() : userDetail.getLastName());
                    userDetail.setAge(null != request.getAge() ? request.getAge() : userDetail.getAge());
                    userDetail.setPhone(null != request.getPhone() ? request.getPhone() : userDetail.getPhone());
                    userDetail.setEmail(null != request.getEmail() ? request.getEmail() : userDetail.getEmail());
                    userDetail.setLine(null != request.getLine() ? request.getLine() : userDetail.getLine());
                    userDetailRepository.save(userDetail);
                }

            }
        }

        return response;
    }


    public UserResponseModel getById(Integer userId){
        
        UserResponseModel response = null;

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if(userEntity.isPresent()){

            response = new UserResponseModel();
            UserEntity user = userEntity.get();
            UserDetailEntity userDetail = userDetailRepository.findByUserId(userId);

            response.setUserId(user.getUserId());
            response.setUserName(user.getUserName());
            response.setUserPassword(user.getUserPassword());
            response.setRoleId(user.getRoleId());
            response.setStatus(user.getStatus());

            if(null != userDetail) {
                response.setFirstName(userDetail.getFirstName());
                response.setLastName(userDetail.getLastName());
                response.setAge(userDetail.getAge());
                response.setPhone(userDetail.getPhone());
                response.setEmail(userDetail.getEmail());
                response.setLine(userDetail.getLine());
            }
        }

        return response;
    }

    @Transactional
    public Integer delete(Integer userId) {

        Integer response = null;
        if(null != userId) {
            userRepository.deleteByUserId(userId);
            userDetailRepository.deleteByUserId(userId);
            response = userId;
        }

        return response;
    }
}
