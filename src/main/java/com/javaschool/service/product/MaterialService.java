package com.javaschool.service.product;

import com.javaschool.dto.product.MaterialDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface MaterialService {

    List<MaterialDto> getAll();

    MaterialDto getById(long id);

    MaterialDto getByName(String materialName);

    void addMaterial(MaterialDto materialDto);

    void getAllMaterialsController(Model model);

    String addNewMaterialController(BindingResult bindingResult, MaterialDto materialDto, Model model);
}
