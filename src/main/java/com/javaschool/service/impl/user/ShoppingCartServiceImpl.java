package com.javaschool.service.impl.user;

import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.ProductMapperImpl;
import com.javaschool.repository.product.ProductRepository;
import com.javaschool.service.impl.product.BrandServiceImpl;
import com.javaschool.service.user.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;
    private final ProductMapperImpl productMapper;

    private static Logger log = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    @Override
    public void add(long productId, ArrayList<ProductBucketDto> bucket, float size, String quantity) {
        log.info("Adding an item to the cart");
        List<ProductDto> productDtoList = null;
        ProductDto productDto = null;
        int quantityInteger = Integer.parseInt(quantity);
        try {
            productDto = productMapper.toDto(productRepository.findById(productId));
            productDtoList = productMapper.toDtoList(productRepository.findAllActiveByModel(productDto.getModel()));
        } catch (ProductException e) {
            log.error("Error add product to bucket", e);
        }
        for (ProductBucketDto productBucket : bucket) {
            if (productBucket.getProductDto().equals(productDto) && productBucket.getProductDto().getSize() == size) {
                productBucket.setQuantityInBucket(productBucket.getQuantityInBucket() + quantityInteger);
                return;
            }
        }
        ProductBucketDto productBucketDto = new ProductBucketDto();
        for (ProductDto productDto1 : productDtoList) {
            if (productDto1.equals(productDto) && productDto1.getSize() == size) {
                productBucketDto.setProductDto(productDto1);
            }
        }
        productBucketDto.setQuantityInBucket(quantityInteger);
        productBucketDto.setAvailable(true);
        bucket.add(productBucketDto);
    }

    @Override
    public void delete(long productId, ArrayList<ProductBucketDto> bucket) {
        log.info("Removing an item from the cart");
        for (ProductBucketDto productBucket : bucket) {
            if (productBucket.getProductDto().getId() == productId) {
                productBucket.setQuantityInBucket(productBucket.getQuantityInBucket() - 1);
                if (productBucket.getQuantityInBucket() == 0) {
                    bucket.remove(productBucket);
                }
                updateBucket(bucket);
                return;
            }
        }
    }

    @Override
    public void updateBucket(ArrayList<ProductBucketDto> bucket) {
        log.info("Updating cart");
        for (ProductBucketDto productBucket : bucket) {
            if (productBucket.getQuantityInBucket() > productBucket.getProductDto().getQuantity()) {
                productBucket.setAvailable(false);
            } else {
                productBucket.setAvailable(true);
            }
        }
    }

    @Override
    public void deleteSelectedProduct(List<ProductBucketDto> bucket, List<ProductBucketDto> selectedProducts) {
        log.info("Removing selected products from the basket");
        for (ProductBucketDto productBucketDto : selectedProducts) {
            bucket.remove(productBucketDto);
        }
    }

    @Override
    public String getBucketFormController(Model model, ArrayList<ProductBucketDto> bucket) {
        log.info("Getting the cart form");
        if (bucket.isEmpty()) {
            model.addAttribute("bucketEmpty", "Your shopping cart is empty. It's time to shop!");
        }
        updateBucket(bucket);
        return "bucket";
    }
}
