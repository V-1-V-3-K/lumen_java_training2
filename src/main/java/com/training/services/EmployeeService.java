package com.training.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

import java.time.LocalDate;
import java.time.MonthDay;

import com.training.exception.ElementNotFoundException;
import com.training.model.Employee;
import com.training.repository.EmployeeRepoImpl;

public class EmployeeService {
	EmployeeRepoImpl repo = new EmployeeRepoImpl();
	
	public boolean add(Employee obj) {
		if(repo.ifEmpIdExist(obj.getEmployeeId())) {
			System.out.println("EID exists");
			return true;
		}else if( repo.ifEmployeeObjExists(obj)) {
			System.out.println("Obj exists");
			return true;
		}
		return repo.save(obj);
	}
	
	public List<String> getNames(){
		List<Employee> empList = (List<Employee>) repo.findAll();
		return empList.stream().map(e->e.getFirstName()).collect(toList());
	}
	
	public Map<String, Long> getFirstNamePhoneNumber(){
		List<Employee> empList = (List<Employee>) repo.findAll();
		return empList.stream().collect(toMap(Employee::getFirstName, Employee::getPhoneNumber));
	}
	
	public Map<String,String> getFnameEmailDob(MonthDay dob){
		List<Employee> empList = (List<Employee>) repo.findAll();
		return empList.stream()
				.filter(e->MonthDay.of(e.getDateOfBirth().getMonth()
						, e.getDateOfBirth().getDayOfMonth())
						.equals(dob))
				.collect(toMap(Employee::getFirstName, Employee::getEmail));
	}
	
	public Map<String,Long> getFnamePhoneDob(MonthDay wedt){
		List<Employee> empList = (List<Employee>) repo.findAll();
		if(wedt!=null) {
			return empList.stream()
					.filter(e->MonthDay.of(e.getWeddingDate().getMonth()
							, e.getWeddingDate().getDayOfMonth())
							.equals(wedt))
					.collect(toMap(Employee::getFirstName, Employee::getPhoneNumber));
		}
		return empList.stream().filter(e->e.getWeddingDate()==null).collect(toMap(Employee::getFirstName, Employee::getPhoneNumber));
	}
	
	public boolean updateEmailPhone(int id, String email, Long phoneNum) {
		
		Employee obj = repo.giveEmpById(id);
		obj.setEmail(email);
		obj.setPhoneNumber(phoneNum);
		return repo.update(obj);
	}
	
	public boolean deleteByName(String name) {
		return this.repo.delete(name);
	}
}
