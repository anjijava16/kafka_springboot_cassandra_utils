package com.mmtech.springkafkaconsu.data.repostitory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmtech.springkafkaconsu.data.entity.Employee;

	
	
	
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
	