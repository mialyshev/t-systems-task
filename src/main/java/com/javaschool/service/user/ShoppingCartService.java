package com.javaschool.service.user;

import com.javaschool.dto.order.OrderRegisterDto;
import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.exception.ProductException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

public interface ShoppingCartService {

    void add(long productId, ArrayList<ProductBucketDto> bucket, float size);

    void delete(long productId, ArrayList<ProductBucketDto> bucket);

    void updateBucket(ArrayList<ProductBucketDto> bucket);

    void deleteSelectedProduct(List<ProductBucketDto> bucket, List<ProductBucketDto> selectedProducts);

    String getBucketFormController(Model model, ArrayList<ProductBucketDto> bucket);
}
