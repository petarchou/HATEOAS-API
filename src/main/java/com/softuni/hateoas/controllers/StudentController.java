package com.softuni.hateoas.controllers;

import com.softuni.hateoas.mappers.StudentMapper;
import com.softuni.hateoas.model.dto.StudentDto;
import com.softuni.hateoas.model.entity.StudentEntity;
import com.softuni.hateoas.repositories.StudentRepository;
import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentController(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<StudentDto>>> getAll() {
        List<EntityModel<StudentDto>> students = studentRepository.findAll()
                .stream()
                .map(studentMapper::mapEntityToDto)
                .map(dto -> EntityModel.of(dto,getStudentLinks(dto)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(students));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<StudentDto>> getStudentById(@PathVariable long id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);

        StudentDto dto = optional.map(studentMapper::mapEntityToDto).orElse(null);
        Optional<StudentDto> opt = Optional.ofNullable(dto);

        return opt.map(studentEntity -> ResponseEntity.ok(
                EntityModel.of(studentEntity,getStudentLinks(studentEntity))
        )).orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<StudentDto>> update(@PathVariable long id, @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<StudentDto>> delete(@PathVariable long id) {
        studentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private Link[] getStudentLinks(StudentDto studentDto) {
        List<Link> links = new ArrayList<>();

        Link selfLink = linkTo(methodOn(StudentController.class).getStudentById(studentDto.getId())).withSelfRel();
        links.add(selfLink);
        Link updateLink = linkTo(methodOn(StudentController.class).update(studentDto.getId(),studentDto)).withRel("update");
        links.add(updateLink);
        Link deleteLink = linkTo(methodOn(StudentController.class).delete(studentDto.getId())).withRel("delete");
        links.add(deleteLink);
        Link ordersLink = linkTo(methodOn(OrderController.class).get(studentDto.getId())).withRel("orders");
        links.add(ordersLink);
        return links.toArray(new Link[0]);
    }
}
