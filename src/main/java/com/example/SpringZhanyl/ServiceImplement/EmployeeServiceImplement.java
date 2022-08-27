package com.example.SpringZhanyl.ServiceImplement;

import com.example.SpringZhanyl.Entity.EmployeeEntity;
import com.example.SpringZhanyl.Model.EmployeeModel;
import com.example.SpringZhanyl.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImplement {
    @Autowired
    private EmployeeRepository employeeRepository;


    public EmployeeServiceImplement(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeEntity> getAllEmployee() { // Model;как это делаааааать
        return  employeeRepository.findAll();

    }

    public ResponseEntity<EmployeeModel> getEmployeeById(Integer empId) throws Exception{
        try {
            EmployeeEntity employeeEntity = employeeRepository.findById(empId).get();
            EmployeeModel employeeModel = new EmployeeModel();
            employeeModel.setEmpId(employeeEntity.getEmpId());
            employeeModel.setName(employeeEntity.getName());
            employeeModel.setLastName(employeeEntity.getLastName());
            employeeModel.setDepartment(employeeEntity.getDepartment());
            employeeModel.setSalary(employeeEntity.getSalary());

            return new ResponseEntity<EmployeeModel>(employeeModel, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<EmployeeModel>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<EmployeeModel> createNewEmployee(EmployeeModel employeeModel) throws Exception{
        try {
            EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setEmpId(employeeModel.getEmpId());
            employeeEntity.setName(employeeModel.getName());
            employeeEntity.setLastName(employeeModel.getLastName());
            employeeEntity.setDepartment(employeeModel.getDepartment());
            employeeEntity.setWorkExperienceYear(employeeModel.getWorkExperienceYear());
            employeeRepository.save(employeeEntity);
            return new ResponseEntity<EmployeeModel>(HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<EmployeeModel>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    public ResponseEntity<EmployeeModel> updateEmployeeById(Integer empId,EmployeeModel employeeModel) throws Exception{ //переделать возвращаем updated и новые updated список,
        try {

            EmployeeEntity employeeFromDB = employeeRepository.findById(empId).get();
            System.out.println(employeeFromDB.toString());
            employeeFromDB.setEmpId(employeeModel.getEmpId());
            employeeFromDB.setName(employeeModel.getName());
            employeeFromDB.setLastName(employeeModel.getLastName());
            employeeFromDB.setDepartment(employeeModel.getDepartment());
            employeeFromDB.setSalary(employeeModel.getSalary());
            employeeFromDB.setWorkExperienceYear(employeeModel.getWorkExperienceYear());
            employeeRepository.save(employeeFromDB);
            return new ResponseEntity<EmployeeModel>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<EmployeeModel>(HttpStatus.NOT_FOUND);
        }


    }

    public void deleteEmployeeById(Integer empId){
        employeeRepository.deleteById(empId);
    }
}
