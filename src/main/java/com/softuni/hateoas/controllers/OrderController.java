package com.softuni.hateoas.controllers;

import com.softuni.hateoas.mappers.OrderMapper;
import com.softuni.hateoas.model.dto.OrderDto;
import com.softuni.hateoas.model.entity.StudentEntity;
import com.softuni.hateoas.repositories.StudentRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final StudentRepository studentRepository;

    private final OrderMapper orderMapper;

    public OrderController(StudentRepository studentRepository, OrderMapper orderMapper) {
        this.studentRepository = studentRepository;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<CollectionModel<EntityModel<OrderDto>>> get(@PathVariable long studentId) {

        Optional<StudentEntity> optional = studentRepository.findById(studentId);
        if (optional.isPresent()) {
            List<EntityModel<OrderDto>> ordersList =
                    optional.get().getOrders().stream()
                            .map(orderMapper::fromEntityToDto)
                            .map(dto -> EntityModel.of(dto, getOrderLinks(dto)))
                            .collect(Collectors.toList());
            return ResponseEntity.ok(CollectionModel.of(ordersList));
        }

        return ResponseEntity.notFound().build();
    }


    private Link[] getOrderLinks(OrderDto orderDto) {
        List<Link> links = new ArrayList<>();

        Link studentLink = linkTo(methodOn(StudentController.class).getStudentById(orderDto.getStudentId())).withRel("student");
        links.add(studentLink);

        return links.toArray(new Link[0]);
    }
}
