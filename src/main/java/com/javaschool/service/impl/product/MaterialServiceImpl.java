package com.javaschool.service.impl.product;

import com.javaschool.dto.product.MaterialDto;
import com.javaschool.entity.Material;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.MaterialMapperImpl;
import com.javaschool.repository.product.MaterialRepository;
import com.javaschool.service.product.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialMapperImpl materialMapper;

    private static Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);

    @Override
    public List<MaterialDto> getAll() {
        log.info("Get all materials");
        List<MaterialDto> materialDtoList = null;
        try {
            materialDtoList = materialMapper.toDtoList(materialRepository.findAll());
        } catch (ProductException e) {
            log.error("Error getting all the materials", e);
        } catch (Exception e) {
            log.error("Error at MaterialService.getAll()", e);
        }
        return materialDtoList;
    }

    @Override
    public MaterialDto getById(long id) {
        log.info("Get material with id: " + id);
        MaterialDto materialDto = null;
        try {
            materialDto = materialMapper.toDto(materialRepository.findById(id));
        } catch (ProductException e) {
            log.error("Error getting a material by id", e);
        } catch (Exception e) {
            log.error("Error at MaterialService.getById()", e);
        }
        return materialDto;
    }

    @Override
    @Transactional
    public MaterialDto getByName(String materialName) {
        log.info("Get material with name: " + materialName);
        MaterialDto materialDto = null;
        try {
            materialDto = materialMapper.toDto(materialRepository.findByName(materialName));
        } catch (ProductException e) {
            log.error("Error getting a material by name", e);
        } catch (Exception e) {
            log.error("Error at MaterialService.getByName()", e);
        }
        return materialDto;
    }

    @Override
    @Transactional
    public void addMaterial(MaterialDto materialDto) {
        log.info("Save new material");
        Material material = new Material();
        material.setMaterialName(materialDto.getMaterialName());
        materialRepository.save(material);
    }

    @Override
    public void getAllMaterialsController(Model model) {
        log.info("Getting all materials to display on the model");
        model.addAttribute("materials", getAll());
        model.addAttribute("materialForm", new MaterialDto());
    }

    @Override
    @Transactional
    public String addNewMaterialController(BindingResult bindingResult, MaterialDto materialDto, Model model) {
        log.info("Retrieving information about a new material to save it");
        if (bindingResult.hasErrors()) {
            model.addAttribute("materials", getAll());
            return "admin-material";
        }
        if (getByName(materialDto.getMaterialName()) != null) {
            model.addAttribute("materialError", "A material with the same name already exists");
            List<MaterialDto> materials = getAll();
            model.addAttribute("materials", materials);
            return "admin-material";
        }
        addMaterial(materialDto);
        return "redirect:/product/material";
    }
}
