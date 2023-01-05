package com.softuni.hateoas.mappers;


import com.softuni.hateoas.model.dto.OrderDto;
import com.softuni.hateoas.model.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {


    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "student.id", target = "studentId")
    OrderDto fromEntityToDto(OrderEntity orderEntity);
}
