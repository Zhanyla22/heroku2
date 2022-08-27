package com.example.SpringZhanyl.RestController;

import com.example.SpringZhanyl.Entity.CustomerEntity;
import com.example.SpringZhanyl.Model.CustomerModel;
import com.example.SpringZhanyl.ServiceImplement.CustomerServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    @Autowired
    CustomerServiceImplement customerServiceImplement;

    @GetMapping("/getCustomer/{cId}")
    //чтобы не писать в классе конфиг чз antMatchers права можно сюда
    @PreAuthorize("hasAuthority('can:read')")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Integer cId)throws Exception{
        return customerServiceImplement.getCustomerById(cId);
    }

    @GetMapping("/getAllCustomer")
    @PreAuthorize("hasAuthority('can:read')")
    public List<CustomerEntity> getAllEmployee(){
        return customerServiceImplement.getAllCustomer();
    }

    @PostMapping("/createNewCustomer")
    @PreAuthorize("hasAuthority('can:write')")
    public ResponseEntity<CustomerModel> createCustomer(@RequestBody CustomerModel customerModel ) throws Exception{
        return customerServiceImplement.createNewCustomer(customerModel);
    }

    @PutMapping("/updateCustomer/{cid}")
    @PreAuthorize("hasAuthority('can:write')")
    public ResponseEntity<CustomerModel> updateCustomerById(@RequestBody CustomerModel customerModel) throws Exception{
        return customerServiceImplement.updateCustomerById(customerModel);
    }

    @DeleteMapping("/deleteCustomer/{cId}")
    @PreAuthorize("hasAuthority('can:write')")
    public void deleteCustomer(@PathVariable Integer cId){
        customerServiceImplement.deleteCustomerById(cId);
    }



}
