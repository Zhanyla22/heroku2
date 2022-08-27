package com.example.SpringZhanyl.RestController;

import com.example.SpringZhanyl.Entity.EmployeeEntity;
import com.example.SpringZhanyl.Model.EmployeeModel;
import com.example.SpringZhanyl.ServiceImplement.EmployeeServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {
    @Autowired
    EmployeeServiceImplement employeeService;

    @GetMapping("/getEmployee/{empId}")
    @PreAuthorize("hasAuthority('can:read')")
    public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable Integer empId) throws Exception{
        return employeeService.getEmployeeById(empId);
    }

    @GetMapping("/allEmployee")
    @PreAuthorize("hasAuthority('can:read')")
    public List<EmployeeEntity> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @PostMapping("/createEmployee")
    @PreAuthorize("hasAuthority('can:write')")
    public ResponseEntity<EmployeeModel> createEmployee(@RequestBody EmployeeModel employeeModel) throws Exception{
        return employeeService.createNewEmployee(employeeModel);
    }



    @PutMapping("/updateEmployee/{empId}")
    @PreAuthorize("hasAuthority('can:write')")
    public ResponseEntity<EmployeeModel> updateEmployeeById(@PathVariable Integer  empId,@RequestBody EmployeeModel employeeModel) throws Exception {
        return employeeService.updateEmployeeById(empId,employeeModel);

    }


    @DeleteMapping("/deleteEmployee/{empId}")
    @PreAuthorize("hasAuthority('can:write')")
    public void deleteEmployeeById(@PathVariable Integer  empId){
        employeeService.deleteEmployeeById(empId);
    }
}
