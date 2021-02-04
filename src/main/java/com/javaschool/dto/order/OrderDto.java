package com.javaschool.dto.order;

import com.javaschool.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private boolean paid;
    private LocalDate dateOfPurchase;
    private List<ProductDto> productDtoList;

}
