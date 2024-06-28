package rmu.project.p_sell_id_game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rmu.project.p_sell_id_game.entity.RoleEntity;
import rmu.project.p_sell_id_game.model.RoleRequestModel;
import rmu.project.p_sell_id_game.model.RoleResponseModel;
import rmu.project.p_sell_id_game.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Integer insert(RoleRequestModel request) {
        Integer roleId = null;
        if (null != request) {
            RoleEntity entity = new RoleEntity();
            entity.setRoleName(request.getRoleName());
            entity.setRoleDesc(request.getRoleDesc());
            entity.setRoleStatus(request.getRoleStatus());
            roleRepository.save(entity);
            roleId = entity.getRoleId();
        }
        return roleId;
    }

    public List<RoleResponseModel> getAllRole() {

        List<RoleEntity> roleEntityList = roleRepository.findAll();
        List<RoleResponseModel> responsesList = null;

        if (null != roleEntityList) {
            responsesList = new ArrayList<>();

            for (RoleEntity entity : roleEntityList) {
                RoleResponseModel response = new RoleResponseModel();
                response.setRoleId(entity.getRoleId());
                response.setRoleName(entity.getRoleName());
                response.setRoleDesc(entity.getRoleDesc());
                response.setRoleStatus(entity.getRoleStatus());
                responsesList.add(response);
            }
        }

        return responsesList;
    }

    public RoleResponseModel getRoleById(Integer roleId) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(roleId);
        RoleResponseModel response = null;

        if (roleEntity.isPresent()) {
            response = new RoleResponseModel();
            RoleEntity entity = roleEntity.get();
            response.setRoleId(entity.getRoleId());
            response.setRoleName(entity.getRoleName());
            response.setRoleDesc(entity.getRoleDesc());
            response.setRoleStatus(entity.getRoleStatus());
        }
        return response;
    }

    public void update(RoleRequestModel request) {

        if (null != request) {
            if(null != request.getRoleId()){
                
                Optional<RoleEntity> roleEntity = roleRepository.findById(request.getRoleId());

                if(roleEntity.isPresent()){
                    RoleEntity entity = roleEntity.get();
                    entity.setRoleName(request.getRoleName());
                    entity.setRoleDesc(request.getRoleDesc());
                    entity.setRoleStatus(request.getRoleStatus());
                    roleRepository.save(entity);
                }
            }
        }
    }

    public void delete(Integer roleId) {
        roleRepository.deleteById(roleId);
    }

}
