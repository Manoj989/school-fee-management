package com.school.fee_management_system.repository;


import com.school.fee_management_system.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

}
