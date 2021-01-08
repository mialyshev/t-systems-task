package com.javaschool.service.impl.product;

import com.javaschool.dto.product.SizeDto;
import com.javaschool.entity.Size;
import com.javaschool.mapper.product.SizeMapperImpl;
import com.javaschool.repository.product.SizeRepository;
import com.javaschool.service.product.SizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final SizeMapperImpl sizeMapper;

    @Override
    public List<SizeDto> getAll() {
        List<SizeDto> sizeDtoList = null;
        try {
            sizeDtoList = sizeMapper.toDtoList(sizeRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all the sizes", e);
        }
        return sizeDtoList;
    }

    @Override
    public SizeDto getById(long id) {
        SizeDto sizeDto = null;
        try {
            sizeDto = sizeMapper.toDto(sizeRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting a size by id", e);
        }
        return sizeDto;
    }

    @Override
    @Transactional
    public SizeDto getByName(float size) {
        SizeDto sizeDto = null;
        try {
            sizeDto = sizeMapper.toDto(sizeRepository.findBySize(size));
        } catch (Exception e) {
            log.error("Error getting a size by name", e);
        }
        return sizeDto;
    }

    @Override
    public void addSize(SizeDto sizeDto) {
        Size size = new Size();
        size.setSize(sizeDto.getSize());
        sizeRepository.save(size);
    }
}
