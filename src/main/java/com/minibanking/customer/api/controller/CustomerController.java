package com.minibanking.customer.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.minibanking.customer.api.entity.Customer;
import com.minibanking.customer.api.exception.CustomerException;
import com.minibanking.customer.api.model.CustomerRequest;
import com.minibanking.customer.api.service.CustomerService;

@RestController
@Validated
public class CustomerController {
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	

	@RequestMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		logger.info("Entering getAllCustomers");
		List<Customer> customerList = customerService.getAllCustomers();
		ResponseEntity<List<Customer>> entity = new ResponseEntity<>(customerList, HttpStatus.OK);
		return entity;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/customer")
	public ResponseEntity<Map<String, Long>> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
		logger.info("Entering addCustomer");
		Customer customer = customerService.addCustomer(customerRequest);
		Long customerId = customer.getId();
		
		Map<String, Long> responseMap = new HashMap<>();
		responseMap.put("customerId", customerId);
		
		ResponseEntity<Map<String, Long>> entity = new ResponseEntity<>(responseMap, HttpStatus.CREATED);
		return entity;
		
		
	}
	
	@RequestMapping("/customer/{id}")
	public ResponseEntity<Map<String, Customer>> getCustomerById(@PathVariable Long id) {
		logger.info("Entering getCustomerById");
		Optional<Customer> customer = customerService.getCustomerById(id);

		Map<String, Customer> responseMap = new HashMap<>();
		if(customer.isPresent()) {
			responseMap.put("customer", customer.get());
		} else {			
			throw new CustomerException(2000,"Customer not found", HttpStatus.NOT_FOUND);
		}

		ResponseEntity<Map<String, Customer>> entity = new ResponseEntity<>(responseMap, HttpStatus.OK);
		return entity;
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/customer/{id}")
	public ResponseEntity<Map<String, String>> updateCustomer(@Valid @RequestBody CustomerRequest customerRequest, @PathVariable Long id) {
		logger.info("Entering updateCustomer");
		customerService.updateCustomer(id, customerRequest);
		Map<String,String> msg = new HashMap<>();
		msg.put("Message", "Customer updated successfully");
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/customer/{id}")
	public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable Long id) {
		logger.info("Entering deleteCustomer");
		customerService.deleteCustomer(id);
		Map<String,String> msg = new HashMap<>();
		msg.put("Message", "Deleted account successfully");
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
}
