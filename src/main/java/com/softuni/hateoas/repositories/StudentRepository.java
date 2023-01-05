package com.softuni.hateoas.repositories;

import com.softuni.hateoas.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

//    Optional<StudentEntity> findById(long id);
}
