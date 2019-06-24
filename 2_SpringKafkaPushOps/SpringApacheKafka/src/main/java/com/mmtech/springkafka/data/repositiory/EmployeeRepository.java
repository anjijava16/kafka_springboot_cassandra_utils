package com.mmtech.springkafka.data.repositiory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmtech.springkafka.data.entity.Employee;

	
	
	
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
