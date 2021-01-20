package com.javaschool.dto.order;

import com.javaschool.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private long id;
    private long user_id;
    private long address_id;
    private String orderStatus;
    private String paymentStatus;
    private String paymentType;
    private boolean isPaid;
    private List<ProductDto> productDtoList;

}
