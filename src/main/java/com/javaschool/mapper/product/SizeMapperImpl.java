package com.javaschool.mapper.product;

import com.javaschool.dto.product.SizeDto;
import com.javaschool.entity.Size;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SizeMapperImpl {
    public SizeDto toDto(Size size){
        SizeDto.SizeDtoBuilder sizeDto = SizeDto.builder();

        sizeDto.size(size.getSize());

        return sizeDto.build();
    }

    public List<SizeDto> toDtoList(List<Size> sizeList) {
        if (sizeList == null) {
            return null;
        }

        List<SizeDto> list = new ArrayList<SizeDto>(sizeList.size());
        for (Size size : sizeList) {
            list.add(toDto(size));
        }

        return list;
    }
}
