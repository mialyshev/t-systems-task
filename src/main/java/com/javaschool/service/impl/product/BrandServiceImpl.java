package com.javaschool.service.impl.product;

import com.javaschool.dto.product.BrandDto;
import com.javaschool.entity.Brand;
import com.javaschool.repository.product.BrandRepository;
import com.javaschool.service.product.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    @Transactional
    public void addBrand(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setBrandName(brandDto.getBrandName());
        brandRepository.save(brand);
    }
}
