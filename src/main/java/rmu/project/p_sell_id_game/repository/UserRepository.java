package rmu.project.p_sell_id_game.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rmu.project.p_sell_id_game.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.userName = ?1")
    public UserEntity findByUserName(String userName);

    @Query("select t from UserEntity t where t.userName = ?1 and t.userPassword = ?2")
    public UserEntity findByUserNameAndPassword(String userName, String userPassword);

    @Query("select t from UserEntity t where t.roleId = 2 ")
    public List<UserEntity> findAllUser();

    @Modifying(clearAutomatically = true)
    @Query("delete from UserEntity t where t.userId = ?1")
    void deleteByUserId(Integer userId);
}
