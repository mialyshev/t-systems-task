package com.javaschool.service.impl.product;

import com.javaschool.dto.product.MaterialDto;
import com.javaschool.entity.Material;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.MaterialMapperImpl;
import com.javaschool.repository.product.MaterialRepository;
import com.javaschool.service.product.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialMapperImpl materialMapper;

    @Override
    public List<MaterialDto> getAll() {
        List<MaterialDto> materialDtoList = null;
        try {
            materialDtoList = materialMapper.toDtoList(materialRepository.findAll());
        } catch (ProductException e) {
            log.error("Error getting all the materials", e);
        }catch (Exception e){
            log.error("Error at MaterialService.getAll()", e);
        }
        return materialDtoList;
    }

    @Override
    public MaterialDto getById(long id) {
        MaterialDto materialDto = null;
        try {
            materialDto = materialMapper.toDto(materialRepository.findById(id));
        } catch (ProductException e) {
            log.error("Error getting a material by id", e);
        }catch (Exception e){
            log.error("Error at MaterialService.getById()", e);
        }
        return materialDto;
    }

    @Override
    @Transactional
    public MaterialDto getByName(String materialName) {
        MaterialDto materialDto = null;
        try {
            materialDto = materialMapper.toDto(materialRepository.findByName(materialName));
        } catch (ProductException e) {
            log.error("Error getting a material by name", e);
        }catch (Exception e){
            log.error("Error at MaterialService.getByName()", e);
        }
        return materialDto;
    }

    @Override
    @Transactional
    public void addMaterial(MaterialDto materialDto) {
        Material material = new Material();
        material.setMaterialName(materialDto.getMaterialName());
        materialRepository.save(material);
    }
}
