package com.javaschool.dto.order;

import com.javaschool.dto.product.ProductBucketDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRegisterDto {

    private long user_id;
    private long address_id;
    private String orderStatus;
    private String paymentStatus;
    private String paymentType;
    private boolean paid;
    private List<ProductBucketDto> productDtoList;
}
