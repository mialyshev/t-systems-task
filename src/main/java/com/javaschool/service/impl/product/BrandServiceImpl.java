package com.javaschool.service.impl.product;

import com.javaschool.dto.product.BrandDto;
import com.javaschool.entity.Brand;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.BrandMapperImpl;
import com.javaschool.repository.product.BrandRepository;
import com.javaschool.service.product.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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
        } catch (ProductException e) {
            log.error("Error getting all the brands", e);
        }catch (Exception e){
            log.error("Error at BrandService.getAll()", e);
        }
        return brandDtoList;
    }

    @Override
    public BrandDto getById(long id) {
        BrandDto brandDto = null;
        try {
            brandDto = brandMapper.toDto(brandRepository.findById(id));
        } catch (ProductException e) {
            log.error("Error getting a brand by id", e);
        }catch (Exception e){
            log.error("Error at BrandService.getById()", e);
        }
        return brandDto;
    }

    @Override
    @Transactional
    public BrandDto getByName(String brandName) {
        BrandDto brandDto = null;
        try {
            brandDto = brandMapper.toDto(brandRepository.findByName(brandName));
        } catch (ProductException e) {
            log.error("Error getting a category by name", e);
        }catch (Exception e){
            log.error("Error at BrandService.getByName()", e);
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

    @Override
    public void getAllBrandsController(Model model) {
        model.addAttribute("brands", getAll());
        model.addAttribute("brandForm", new BrandDto());
    }

    @Override
    @Transactional
    public String addNewBrandController(BindingResult bindingResult,  BrandDto brandDto, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", getAll());
            return "admin-brand";
        }
        if(getByName(brandDto.getBrandName()) != null){
            model.addAttribute("brandError", "A brand with the same name already exists");
            List<BrandDto> brands = getAll();
            model.addAttribute("brands", brands);
            return "admin-brand";
        }
        addBrand(brandDto);
        return "redirect:/product/brand";
    }
}
