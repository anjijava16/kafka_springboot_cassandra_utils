package com.mmtech.mmpocspringcass.data.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmtech.mmpocspringcass.data.entity.Employee;

	
	
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
