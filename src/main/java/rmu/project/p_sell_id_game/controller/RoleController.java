package rmu.project.p_sell_id_game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rmu.project.p_sell_id_game.model.ResponseModel;
import rmu.project.p_sell_id_game.model.RoleRequestModel;
import rmu.project.p_sell_id_game.model.RoleResponseModel;
import rmu.project.p_sell_id_game.service.RoleService;



@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/insert")
    public ResponseModel insert(@RequestBody RoleRequestModel request) {

        ResponseModel response = new ResponseModel();

        try {
            response.setData(roleService.insert(request));
            response.setStatus("เพิ่ม role สําเร็จ");
        } catch (Exception e) {
            response.setStatus("เพิ่ม role ไม่สําเร็จ โปรดตรวจสอบข้อมูลให้ถูกต้อง และลองอีกครั้ง");
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/getAllRole")
    public List<RoleResponseModel> getAllRole() {
        return roleService.getAllRole();
    }

    @GetMapping("/getRoleById")
    public RoleResponseModel getRoleById(@RequestParam(name = "roleId") Integer roleId) {
        return roleService.getRoleById(roleId);
    }

    @PutMapping("/update")
    public void update(@RequestBody RoleRequestModel request) {
        roleService.update(request);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(name = "roleId") Integer roleId) {
        roleService.delete(roleId);
    }
}
