package com.softuni.hateoas.mappers;

import com.softuni.hateoas.model.dto.StudentDto;
import com.softuni.hateoas.model.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper  {

    StudentDto mapEntityToDto(StudentEntity entity);

}
