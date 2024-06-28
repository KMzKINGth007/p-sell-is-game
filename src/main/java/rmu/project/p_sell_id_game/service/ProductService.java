package rmu.project.p_sell_id_game.service;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import rmu.project.p_sell_id_game.entity.ProductEntity;
import rmu.project.p_sell_id_game.entity.ProductImgEntity;
import rmu.project.p_sell_id_game.entity.ProductTypeEntity;
import rmu.project.p_sell_id_game.model.ProductImgResponseModel;
import rmu.project.p_sell_id_game.model.ProductRequestModel;
import rmu.project.p_sell_id_game.model.ProductResponseModel;
import rmu.project.p_sell_id_game.model.ProductTypeResponseModel;
import rmu.project.p_sell_id_game.repository.ProductImgRepository;
import rmu.project.p_sell_id_game.repository.ProductRepository;
import rmu.project.p_sell_id_game.repository.ProductTypeRepository;
import rmu.project.p_sell_id_game.utils.Constants;
import rmu.project.p_sell_id_game.utils.ImgUtils;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImgRepository productImgRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Transactional
    public Integer insert(ProductRequestModel productRequest) throws IOException {

        Integer response = null;

        if (null != productRequest) {

            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductName(productRequest.getProductName());
            productEntity.setProductDesc(productRequest.getProductDesc());
            productEntity.setProductPrice(productRequest.getProductPrice());
            productEntity.setProductStock(productRequest.getProductStock());
            productEntity.setProductTypeId(productRequest.getProductTypeId());

            productEntity = productRepository.save(productEntity);
            response = productEntity.getProductId();

        }

        return response;

    }

    public List<ProductResponseModel> getAll() {

        List<ProductResponseModel> response = null;

        List<ProductEntity> entitys = productRepository.findAll();

        if (null != entitys) {
            response = new ArrayList<>();
            for (ProductEntity product : entitys) {

                ProductResponseModel object = new ProductResponseModel();

                object.setProductId(product.getProductId());
                object.setProductName(product.getProductName());
                object.setProductDesc(product.getProductDesc());
                object.setProductTypeId(product.getProductTypeId());
                object.setProductPrice(product.getProductPrice());
                object.setProductStock(product.getProductStock());

                response.add(object);
            }

        }

        return response;

    }

    public ProductResponseModel getById(Integer productId) {

        ProductResponseModel response = null;

        Optional<ProductEntity> entity = productRepository.findById(productId);

        if (entity.isPresent()) {
            ProductEntity product = entity.get();
            response = new ProductResponseModel();

            response.setProductId(product.getProductId());
            response.setProductName(product.getProductName());
            response.setProductDesc(product.getProductDesc());
            response.setProductTypeId(product.getProductTypeId());
            response.setProductPrice(product.getProductPrice());
            response.setProductStock(product.getProductStock());

        }

        return response;

    }

    @Transactional
    public Integer update(ProductRequestModel productRequest, Integer productId) throws IOException {
        Integer response = null;

        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        if (productEntity.isPresent()) {
            ProductEntity product = productEntity.get();
            product.setProductName(null != productRequest.getProductName() ? productRequest.getProductName()
                    : product.getProductName());
            product.setProductDesc(null != productRequest.getProductDesc() ? productRequest.getProductDesc()
                    : product.getProductDesc());
            product.setProductTypeId(null != productRequest.getProductTypeId() ? productRequest.getProductTypeId()
                    : product.getProductTypeId());
            product.setProductPrice(null != productRequest.getProductPrice() ? productRequest.getProductPrice()
                    : product.getProductPrice());
            product.setProductStock(
                    null != productRequest.getProductStock() ? productRequest.getProductStock()
                            : product.getProductStock());

            product = productRepository.save(product);

            response = product.getProductId();

        }

        return response;
    }

    @Transactional
    public void removeImgAndDeleteFileImgByProductId(Integer productId) throws IOException {
        List<ProductImgEntity> productImgList = productImgRepository.findByProductId(productId);
        productImgRepository.deleteAll(productImgList);
    }

    @Transactional
    public void deleteImgByFileName(String fileName) throws IOException {
        ProductImgEntity productImgList = productImgRepository.findByProductName(fileName);
        productImgRepository.delete(productImgList);
    }

    public byte[] getImageByte(String fileName) throws IOException, DataFormatException {
        ProductImgEntity productImg = productImgRepository.findByProductName(fileName);

        if (null != productImg) {
            return ImgUtils.decompressImage(productImg.getProductImageData());
        }

        return null;
    }

    public List<ProductImgResponseModel> getProductImgByProductId(Integer productId) {
        List<ProductImgResponseModel> response = null;

        List<ProductImgEntity> productImgList = productImgRepository.findByProductId(productId);

        if (null != productImgList) {
            response = new ArrayList<>();
            for (ProductImgEntity productImg : productImgList) {
                ProductImgResponseModel objectResponse = new ProductImgResponseModel();
                objectResponse.setProductImgId(productImg.getProductImageId());
                objectResponse.setProductId(productImg.getProductId());
                objectResponse.setProductImageName(productImg.getProductImageName());
                objectResponse.setProductImagePath(productImg.getProductImagePath());
                objectResponse.setProductImageData(productImg.getProductImageData());
                response.add(objectResponse);
            }
        }

        return response;
    }

    @Transactional
    public Integer saveImage(MultipartFile file, Integer productId) throws IOException {
        Integer response = null;
        if (null != file && null != productId) {
            ProductImgEntity productImg = new ProductImgEntity();
            String preFixNameFile = ImgUtils.genaratePrefixFile();
            String genarateFileName = ImgUtils.genarateFileName() + ImgUtils.subStrFileName(file.getOriginalFilename());
            String fileName = ImgUtils.concatStr(preFixNameFile, genarateFileName);
            productImg.setProductImageName(fileName);
            productImg.setProductImagePath(ImgUtils.getPathInput());
            productImg.setProductId(productId);
            productImg.setProductImageData(ImgUtils.compressImage(file.getBytes()));
            productImg.setCreateAt(new Date());
            productImg.setUpdateAt(new Date());

            productImg = productImgRepository.save(productImg);

            response = productImg.getProductImageId();

            // ImgUtils.saveFile(file, fileName, Constants.PATH_TYPE_INPUT);
        }
        return response;
    }

    @Transactional
    public void deleteImgSever(String fileName) throws IOException {
        if (null != fileName) {
            ImgUtils.deleteFile(fileName, Constants.PATH_TYPE_OUTPUT);
        }

    }

    public List<ProductTypeResponseModel> getAllProductType() {
        List<ProductTypeResponseModel> response = null;

        List<ProductTypeEntity> productEntitys = productTypeRepository.findAll();
        if (null != productEntitys) {
            response = new ArrayList<>();
            for (ProductTypeEntity product : productEntitys) {
                ProductTypeResponseModel objectResponse = new ProductTypeResponseModel();
                objectResponse.setProductTypeId(product.getProductTypeId());
                objectResponse.setProductTypeName(product.getProductTypeName());
                objectResponse.setProductTypeDesc(product.getProductTypeDesc());
                objectResponse.setProductTypeStatus(product.getProductTypeStatus());
                response.add(objectResponse);
            }
        }

        return response;
    }

    @Transactional
    public Integer delete(Integer productId) throws IOException {
        Integer response = null;
        if (null != productId) {
            productRepository.deleteByProductId(productId);
            this.removeImgAndDeleteFileImgByProductId(productId);
            response = productId;
        }
        return response;
    }
}
