package com.javaschool.service.impl.product;

import com.javaschool.dto.product.SizeDto;
import com.javaschool.entity.Size;
import com.javaschool.repository.product.SizeRepository;
import com.javaschool.service.product.SizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;

    @Override
    public void addSize(SizeDto sizeDto) {
        Size size = new Size();
        size.setSize(sizeDto.getSize());
        sizeRepository.save(size);
    }
}
