package com.javaschool.service.impl.product;

import com.javaschool.dto.product.BrandDto;
import com.javaschool.entity.Brand;
import com.javaschool.mapper.product.BrandMapperImpl;
import com.javaschool.repository.product.BrandRepository;
import com.javaschool.service.product.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapperImpl brandMapper;

    @Override
    public List<BrandDto> getAll() {
        List<BrandDto> brandDtoList = null;
        try {
            brandDtoList = brandMapper.toDtoList(brandRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all the brands", e);
        }
        return brandDtoList;
    }

    @Override
    public BrandDto getById(long id) {
        BrandDto brandDto = null;
        try {
            brandDto = brandMapper.toDto(brandRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting a brand by id", e);
        }
        return brandDto;
    }

    @Override
    @Transactional
    public BrandDto getByName(String brandName) {
        BrandDto brandDto = null;
        try {
            brandDto = brandMapper.toDto(brandRepository.findByName(brandName));
        } catch (Exception e) {
            log.error("Error getting a category by name", e);
        }
        return brandDto;
    }

    @Override
    @Transactional
    public void addBrand(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setBrandName(brandDto.getBrandName());
        brandRepository.save(brand);
    }
}
