package com.javaschool.service.impl.product;

import com.javaschool.dto.product.ColorDto;
import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.SizeDto;
import com.javaschool.entity.Order;
import com.javaschool.entity.Product;
import com.javaschool.entity.Product_;
import com.javaschool.entity.Size;
import com.javaschool.mapper.product.ProductMapperImpl;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import com.javaschool.repository.order.OrderRepository;
import com.javaschool.repository.product.*;
import com.javaschool.service.product.ProductService;
import com.javaschool.service.user.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;

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
            productDtoList = getUnique(productMapper.toDtoList(productRepository.findAll()));
        } catch (Exception e) {
            log.error("Error getting all the products", e);
        }
        return productDtoList;
    }

    @Override
    public List<ProductDto> getAllActive() {
        List<ProductDto> productDtoListFromRepo = null;
        try {
            productDtoListFromRepo = getUnique(productMapper.toDtoList(productRepository.findAllActive()));
        } catch (Exception e) {
            log.error("Error getting all the active products", e);
        }
        return productDtoListFromRepo;
    }

    private List<ProductDto> getUnique(List<ProductDto> productDtos){
        List<ProductDto> productDtoList = new ArrayList<>();
        for(ProductDto productDto : productDtos){
            if(isUnique(productDtoList, productDto)){
                productDtoList.add(productDto);
            }
        }
        return productDtoList;
    }

    private boolean isUnique(List<ProductDto> productDtos, ProductDto productDto){
        for (ProductDto productDto1 : productDtos){
            if(productDto1.equals(productDto)){
                return false;
            }
        }
        return true;
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

    @Override
    public List<ProductBucketDto> getSelectedList(Integer[] selected, ArrayList<ProductBucketDto> bucket) {
        List<ProductBucketDto> productDtos = new ArrayList<>();
        for(Integer id : selected){
            productDtos.add(findByProductId(bucket, id));
        }
        return productDtos;
    }

    private ProductBucketDto findByProductId(ArrayList<ProductBucketDto> bucket, long productId){
        for(ProductBucketDto productBucketDto: bucket){
            if(productBucketDto.getProductDto().getId() == productId){
                return productBucketDto;
            }
        }
        return null;
    }

    @Override
    public boolean isAvailable(List<ProductBucketDto> productDtos) {
        for(ProductBucketDto productBucketDto : productDtos){
            Product product = productRepository.findById(productBucketDto.getProductDto().getId());
            if(productBucketDto.getQuantityInBucket() > product.getQuantity()){
                return false;
            }
        }
        return true;
    }

    @Override
    public int calcPrice(List<ProductBucketDto> productDtos) {
        int price = 0;
        for(ProductBucketDto productBucketDto : productDtos){
            price += (productBucketDto.getProductDto().getPrice() * productBucketDto.getQuantityInBucket());
        }
        return price;
    }

    @Override
    @Transactional
    public void updateProductQuantity(List<ProductBucketDto> productDtoList) {
        for(ProductBucketDto productDto : productDtoList){
            Product product = productRepository.findById(productDto.getProductDto().getId());
            product.setQuantity(product.getQuantity() - productDto.getQuantityInBucket());
            if (product.getQuantity() <= 0){
                product.setActive(false);
            }
            productRepository.updateProduct(product);
        }
    }

    @Override
    @Transactional
    public List<ProductDto> getProductsByParam(List<SearchCriteria> params) {
        List<ProductDto> productDtoList = null;
        try {
            productDtoList = productMapper.toDtoList(productRepository.findByParam(params));
        } catch (Exception e) {
            log.error("Error getting all the products by param", e);
        }
        return productDtoList;
    }

    @Override
    @Transactional
    public void addProductBySizeQuantity(float size, int quantity, long productId) {
        Size sizeFromRepo = sizeRepository.findBySize(size);
        Product product = new Product();
        Product productFromRepo = productRepository.findById(productId);
        if(productFromRepo.getSize() == sizeFromRepo){
            if(!productFromRepo.isActive()){
                productFromRepo.setActive(true);
            }
            productFromRepo.setQuantity(productFromRepo.getQuantity() + quantity);
            productRepository.updateProduct(productFromRepo);
            return;
        }
        product.setActive(true);
        product.setModel(productFromRepo.getModel());
        product.setQuantity(quantity);
        product.setPrice(productFromRepo.getPrice());
        product.setUrl(productFromRepo.getUrl());
        product.setBrand(productFromRepo.getBrand());
        product.setCategory(productFromRepo.getCategory());
        product.setColor(productFromRepo.getColor());
        product.setMaterial(productFromRepo.getMaterial());
        product.setSeason(productFromRepo.getSeason());
        product.setSize(sizeRepository.findBySize(size));
        productRepository.save(product);
    }

    @Override
    public List<SizeDto> getAvailableSizesForProduct(long productId) {
        List<SizeDto> sizeDtos = new ArrayList<>();
        List<ProductDto> products = null;
        ProductDto product = productMapper.toDto(productRepository.findById(productId));
        try {
            products = productMapper.toDtoList(productRepository.findAllActiveByModel(product.getModel()));
        } catch (Exception e) {
            log.error("Error getting all the active products for check available sizes", e);
        }
        for(ProductDto productFromRepo : products){
            if(productFromRepo.equals(product)){
                SizeDto sizeDto = new SizeDto();
                sizeDto.setSize(productFromRepo.getSize());
                sizeDtos.add(sizeDto);
            }
        }
        return sizeDtos;
    }

    @Override
    public void addProductToBucket(long productId, float size, ArrayList<ProductDto> bucket) {
        ProductDto product = productMapper.toDto(productRepository.findById(productId));
        if(product.getSize() == size){
            bucket.add(product);
            return;
        }
        List<ProductDto> products = null;
        try {
            products = productMapper.toDtoList(productRepository.findAllActiveByModel(product.getModel()));
        } catch (Exception e) {
            log.error("Error getting all the active products for add to bucket", e);
        }
        for (ProductDto productDto : products){
            if(productDto.equals(product)){
                if(productDto.getSize() == size){
                    bucket.add(productDto);
                    return;
                }
            }
        }
    }
}
