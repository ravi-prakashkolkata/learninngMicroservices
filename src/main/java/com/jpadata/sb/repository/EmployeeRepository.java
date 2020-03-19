 package com.jpadata.sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpadata.sb.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
