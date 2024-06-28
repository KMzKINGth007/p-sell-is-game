package rmu.project.p_sell_id_game.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rmu.project.p_sell_id_game.entity.ProductImgEntity;


@Repository
public interface ProductImgRepository extends JpaRepository<ProductImgEntity, Integer>{

	@Query("select t from ProductImgEntity t where t.productId = ?1")
	public List<ProductImgEntity> findByProductId(Integer productId);
	
	@Modifying(clearAutomatically = true)
	@Query("delete from ProductImgEntity t where t.productId = ?1")
	void deleteByProductId(Integer productId);
	
	@Query("select t from ProductImgEntity t where t.productImageName = ?1")
	public ProductImgEntity findByProductName(String productImageName);
}
