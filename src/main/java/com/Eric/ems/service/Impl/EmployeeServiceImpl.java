package com.Eric.ems.service.Impl;

import com.Eric.ems.dto.EmployeeDto;
import com.Eric.ems.entity.Employee;
import com.Eric.ems.exception.ResourceNotFoundException;
import com.Eric.ems.mapper.EmployeeMapper;
import com.Eric.ems.repository.EmployeeRepository;
import com.Eric.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public EmployeeDto createEmployee(EmployeeDto employeeDto) {
    Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
    Employee savedEmployee = employeeRepository.save(employee);
    return EmployeeMapper.mapToEmployeeDto(savedEmployee);
  }

  @Override
  public EmployeeDto getEmployeeById(Long employeeId) {
    Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(()-> new ResourceNotFoundException("Employee is not exists with given id : " + employeeId));
    return EmployeeMapper.mapToEmployeeDto(employee);
  }

  @Override
  public List<EmployeeDto> getAllEmployees() {
    List<Employee> employees = employeeRepository.findAll();
    return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
            .collect(Collectors.toList());
  }

  @Override
  public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
   Employee employee =  employeeRepository.findById(employeeId).orElseThrow(
            ()->new ResourceNotFoundException("Employee is not exists with given id: " + employeeId)
    );
   employee.setFirstName(updatedEmployee.getFirstName());
   employee.setLastName(updatedEmployee.getLastName());
   employee.setEmail(updatedEmployee.getEmail());

   Employee updatedEmployeeObj = employeeRepository.save(employee);
    return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
  }

  @Override
  public void deleteEmployee(Long employeeId) {
    Employee employee =  employeeRepository.findById(employeeId).orElseThrow(
            ()->new ResourceNotFoundException("Employee is not exists with given id: " + employeeId)
    );
    employeeRepository.deleteById(employeeId);
  }
}
