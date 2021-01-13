package com.javaschool.service.impl.product;

import com.javaschool.dto.product.ColorDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.entity.Product;
import com.javaschool.entity.Product_;
import com.javaschool.mapper.product.ProductMapperImpl;
import com.javaschool.repository.product.*;
import com.javaschool.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapperImpl productMapper;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final CategoryRepository categoryRepository;
    private final MaterialRepository materialRepository;
    private final SeasonRepository seasonRepository;
    private final SizeRepository sizeRepository;

    @Override
    public List<ProductDto> getAll() {
        List<ProductDto> productDtoList = null;
        try {
            productDtoList = productMapper.toDtoList(productRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all the products", e);
        }
        return productDtoList;
    }

    @Override
    public List<ProductDto> getAllActive() {
        List<ProductDto> productDtoList = null;
        try {
            productDtoList = productMapper.toDtoList(productRepository.findAllActive());
        } catch (Exception e) {
            log.error("Error getting all the active products", e);
        }
        return productDtoList;
    }

    @Override
    public ProductDto getById(long id) {
        ProductDto productDto = null;
        try {
            productDto = productMapper.toDto(productRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting a product by id", e);
        }
        return productDto;
    }

    @Override
    @Transactional
    public void addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setActive(true);
        product.setModel(productDto.getModel());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setUrl(productDto.getUrl());
        product.setBrand(brandRepository.findByName(productDto.getBrandName()));
        product.setCategory(categoryRepository.findByName(productDto.getCategoryName()));
        product.setColor(colorRepository.findByName(productDto.getColorName()));
        product.setMaterial(materialRepository.findByName(productDto.getMaterialName()));
        product.setSeason(seasonRepository.findByName(productDto.getSeasonName()));
        product.setSize(sizeRepository.findBySize(productDto.getSize()));
        productRepository.save(product);
    }
}
