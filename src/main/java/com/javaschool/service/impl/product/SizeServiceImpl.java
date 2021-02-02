package com.javaschool.service.impl.product;

import com.javaschool.dto.product.SizeDto;
import com.javaschool.entity.Size;
import com.javaschool.exception.ProductException;
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
        } catch (ProductException e) {
            log.error("Error getting all the sizes", e);
        }catch (Exception e){
            log.error("Error at SizeService.getAll()", e);
        }
        return sizeDtoList;
    }

    @Override
    public SizeDto getById(long id) {
        SizeDto sizeDto = null;
        try {
            sizeDto = sizeMapper.toDto(sizeRepository.findById(id));
        } catch (ProductException e) {
            log.error("Error getting a size by id", e);
        }catch (Exception e){
            log.error("Error at SizeService.getById()", e);
        }
        return sizeDto;
    }

    @Override
    @Transactional
    public SizeDto getByName(float size) {
        SizeDto sizeDto = null;
        try {
            sizeDto = sizeMapper.toDto(sizeRepository.findBySize(size));
        } catch (ProductException e) {
            log.error("Error getting a size by name", e);
        }catch (Exception e){
            log.error("Error at SizeService.getByName()", e);
        }
        return sizeDto;
    }

    @Override
    @Transactional
    public void addSize(SizeDto sizeDto) {
        Size size = new Size();
        size.setSize(sizeDto.getSize());
        sizeRepository.save(size);
    }
}
