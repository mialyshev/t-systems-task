package com.javaschool.service.impl.product;

import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.SelectedParams;
import com.javaschool.dto.product.SizeDto;
import com.javaschool.entity.Product;
import com.javaschool.entity.Size;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.ProductMapperImpl;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import com.javaschool.repository.product.*;
import com.javaschool.service.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ColorService colorService;
    private final MaterialService materialService;
    private final SeasonService seasonService;
    private final SizeService sizeService;

    @Override
    public List<ProductDto> getAll() {
        List<ProductDto> productDtoList = null;
        try {
            productDtoList = getUnique(productMapper.toDtoList(productRepository.findAll()));
        } catch (ProductException e) {
            log.error("Error getting all the products", e);
        } catch (Exception e) {
            log.error("Error at ProductService.getAll()", e);
        }
        return productDtoList;
    }


    private List<ProductDto> getUnique(List<ProductDto> productDtos) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductDto productDto : productDtos) {
            if (isUnique(productDtoList, productDto)) {
                productDtoList.add(productDto);
            }
        }
        return productDtoList;
    }

    private boolean isUnique(List<ProductDto> productDtos, ProductDto productDto) {
        for (ProductDto productDto1 : productDtos) {
            if (productDto1.equals(productDto)) {
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
        } catch (ProductException e) {
            log.error("Error getting a product by id", e);
        } catch (Exception e) {
            log.error("Error at ProductService.getById()", e);
        }
        return productDto;
    }

    @Override
    @Transactional
    public void addProduct(ProductDto productDto) {
        Product product = new Product();
        try {
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
        } catch (ProductException e) {
            log.error("Error while add new product", e);
        } catch (Exception e) {
            log.error("Error at ProductService.addProduct()", e);
        }
    }

    @Override
    public List<ProductBucketDto> getSelectedList(Integer[] selected, ArrayList<ProductBucketDto> bucket) {
        List<ProductBucketDto> productDtos = new ArrayList<>();
        for (Integer id : selected) {
            productDtos.add(findByProductId(bucket, id));
        }
        return productDtos;
    }

    private ProductBucketDto findByProductId(ArrayList<ProductBucketDto> bucket, long productId) {
        for (ProductBucketDto productBucketDto : bucket) {
            if (productBucketDto.getProductDto().getId() == productId) {
                return productBucketDto;
            }
        }
        return null;
    }

    @Override
    public boolean isAvailable(List<ProductBucketDto> productDtos) {
        try {
            for (ProductBucketDto productBucketDto : productDtos) {
                Product product = productRepository.findById(productBucketDto.getProductDto().getId());
                if (productBucketDto.getQuantityInBucket() > product.getQuantity()) {
                    return false;
                }
            }
        } catch (ProductException e) {
            log.error("Error while find product by id", e);
        } catch (Exception e) {
            log.error("Error at ProductService.isAvailable()", e);
        }
        return true;
    }

    @Override
    public int calcPrice(List<ProductBucketDto> productDtos) {
        int price = 0;
        for (ProductBucketDto productBucketDto : productDtos) {
            price += (productBucketDto.getProductDto().getPrice() * productBucketDto.getQuantityInBucket());
        }
        return price;
    }

    @Override
    @Transactional
    public void updateProductQuantity(List<ProductBucketDto> productDtoList) {
        try {
            for (ProductBucketDto productDto : productDtoList) {
                Product product = productRepository.findById(productDto.getProductDto().getId());
                product.setQuantity(product.getQuantity() - productDto.getQuantityInBucket());
                if (product.getQuantity() <= 0) {
                    product.setActive(false);
                }
                productRepository.updateProduct(product);
            }
        } catch (ProductException e) {
            log.error("Error while find product by id", e);
        } catch (Exception e) {
            log.error("Error at ProductService.updateProductQuantity()", e);
        }
    }


    @Override
    @Transactional
    public void addProductBySizeQuantity(float size, int quantity, long productId) {
        try {
            Size sizeFromRepo = sizeRepository.findBySize(size);
            Product product = new Product();
            Product productFromRepo = productRepository.findById(productId);
            if (productFromRepo.getSize() == sizeFromRepo) {
                if (!productFromRepo.isActive()) {
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
        } catch (ProductException e) {
            log.error("Error while add product by size or quantity", e);
        } catch (Exception e) {
            log.error("Error at ProductService.addProductBySizeQuantity()", e);
        }
    }

    @Override
    public List<SizeDto> getAvailableSizesForProduct(long productId) {
        List<SizeDto> sizeDtos = new ArrayList<>();
        try {
            List<ProductDto> products = null;
            ProductDto product = productMapper.toDto(productRepository.findById(productId));
            try {
                products = productMapper.toDtoList(productRepository.findAllActiveByModel(product.getModel()));
            } catch (ProductException e) {
                log.error("Error getting all the active products for check available sizes", e);
            }
            for (ProductDto productFromRepo : products) {
                if (productFromRepo.equals(product)) {
                    SizeDto sizeDto = new SizeDto();
                    sizeDto.setSize(productFromRepo.getSize());
                    sizeDtos.add(sizeDto);
                }
            }
        } catch (ProductException e) {
            log.error("Error while getting all available sizes for product", e);
        } catch (Exception e) {
            log.error("Error at ProductService.getAvailableSizesForProduct()", e);
        }
        return sizeDtos;
    }


    @Override
    public List<ProductDto> getProductsByParam(String categoryName, String brandName, String colorName, String materialName, String seasonName, SelectedParams selectedParams) {
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        List<ProductDto> productDtoListFromRepo = null;
        try {
            if (categoryName != null) {
                params.add(new SearchCriteria("category", ":", categoryRepository.findByName(categoryName)));
                selectedParams.setCategory(categoryName);
            }
            if (brandName != null) {
                params.add(new SearchCriteria("brand", ":", brandRepository.findByName(brandName)));
                selectedParams.setBrand(brandName);
            }
            if (colorName != null) {
                params.add(new SearchCriteria("color", ":", colorRepository.findByName(colorName)));
                selectedParams.setColor(colorName);
            }
            if (materialName != null) {
                params.add(new SearchCriteria("material", ":", materialRepository.findByName(materialName)));
                selectedParams.setMaterial(materialName);
            }
            if (seasonName != null) {
                params.add(new SearchCriteria("season", ":", seasonRepository.findByName(seasonName)));
                selectedParams.setSeason(seasonName);
            }

            productDtoListFromRepo = getUnique(productMapper.toDtoList(productRepository.findByParam(params)));
        } catch (ProductException e) {
            log.error("Error while check all params", e);
        } catch (Exception e) {
            log.error("Error at ProductService.getProductsByParam()", e);
        }
        return productDtoListFromRepo;
    }

    @Override
    public List<ProductDto> getProductsByParamList(SelectedParams selectedParams) {
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        List<ProductDto> productDtoListFromRepo = null;
        try {
            if (selectedParams.getCategory() != null) {
                params.add(new SearchCriteria("category", ":", categoryRepository.findByName(selectedParams.getCategory())));
            }
            if (selectedParams.getBrand() != null) {
                params.add(new SearchCriteria("brand", ":", brandRepository.findByName(selectedParams.getBrand())));
            }
            if (selectedParams.getColor() != null) {
                params.add(new SearchCriteria("color", ":", colorRepository.findByName(selectedParams.getColor())));
            }
            if (selectedParams.getMaterial() != null) {
                params.add(new SearchCriteria("material", ":", materialRepository.findByName(selectedParams.getMaterial())));
            }
            if (selectedParams.getSeason() != null) {
                params.add(new SearchCriteria("season", ":", seasonRepository.findByName(selectedParams.getSeason())));
            }
            productDtoListFromRepo = getUnique(productMapper.toDtoList(productRepository.findByParam(params)));
        } catch (ProductException e) {
            log.error("Error while get product by params", e);
        } catch (Exception e) {
            log.error("Error at ProductService.getProductsByParamList()", e);
        }
        return productDtoListFromRepo;
    }

    @Override
    public void addNewProductPageController(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("seasons", seasonService.getAll());
        model.addAttribute("productForm", new ProductDto());
    }

    @Override
    @Transactional
    public String addNewProductController(ProductDto productDto, BindingResult bindingResult, float size, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("brands", brandService.getAll());
            model.addAttribute("colors", colorService.getAll());
            model.addAttribute("materials", materialService.getAll());
            model.addAttribute("seasons", seasonService.getAll());
            return "admin-product-page";
        }
        productDto.setSize(size);
        addProduct(productDto);
        return "redirect:/product";
    }

    @Override
    @Transactional
    public String addSizeOrQuantityForProductController(long id, float size, int quantity, Model model) {
        if (quantity <= 0) {
            model.addAttribute("quantityError", "Quantity must be greater than 0");
            model.addAttribute("productInfo", getById(id));
            return "admin-add-size-or-quantity";
        }
        addProductBySizeQuantity(size, quantity, id);
        return "redirect:/product/add-size-product/" + id;
    }
}
