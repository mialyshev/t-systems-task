package com.javaschool.service.impl.user;

import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.ProductMapperImpl;
import com.javaschool.repository.product.ProductRepository;
import com.javaschool.service.product.ProductService;
import com.javaschool.service.user.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;
    private final ProductMapperImpl productMapper;

    @Override
    public void add(long productId, ArrayList<ProductBucketDto> bucket, float size) throws ProductException {
        List<ProductDto> productDtoList = null;
        ProductDto productDto = productMapper.toDto(productRepository.findById(productId));
        try {
            productDtoList = productMapper.toDtoList(productRepository.findAllActiveByModel(productDto.getModel()));
        } catch (Exception e) {
            log.error("Error getting all the products for add to cart", e);
        }
        for(ProductBucketDto productBucket : bucket){
            if(productBucket.getProductDto().equals(productDto) && productBucket.getProductDto().getSize() == size){
                productBucket.setQuantityInBucket(productBucket.getQuantityInBucket() + 1);
                return;
            }
        }
        ProductBucketDto productBucketDto = new ProductBucketDto();
        for(ProductDto productDto1 : productDtoList){
            if(productDto1.equals(productDto) && productDto1.getSize() == size){
                productBucketDto.setProductDto(productDto1);
            }
        }
        productBucketDto.setQuantityInBucket(1);
        productBucketDto.setAvailable(true);
        bucket.add(productBucketDto);
    }

    @Override
    public void delete(long productId, ArrayList<ProductBucketDto> bucket) {
        for(ProductBucketDto productBucket : bucket){
            if(productBucket.getProductDto().getId() == productId){
                productBucket.setQuantityInBucket(productBucket.getQuantityInBucket() - 1);
                if (productBucket.getQuantityInBucket() == 0){
                    bucket.remove(productBucket);
                }
                updateBucket(bucket);
                return;
            }
        }
    }

    @Override
    public void updateBucket(ArrayList<ProductBucketDto> bucket) {
        for(ProductBucketDto productBucket : bucket){
            if (productBucket.getQuantityInBucket() > productBucket.getProductDto().getQuantity()){
                productBucket.setAvailable(false);
            }else {
                productBucket.setAvailable(true);
            }
        }
    }

    @Override
    public void deleteSelectedProduct(List<ProductBucketDto> bucket, List<ProductBucketDto> selectedProducts) {
        for(ProductBucketDto productBucketDto : selectedProducts){
            bucket.remove(productBucketDto);
        }
    }
}
