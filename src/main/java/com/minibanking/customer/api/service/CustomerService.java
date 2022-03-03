package com.minibanking.customer.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.minibanking.customer.api.entity.Customer;
import com.minibanking.customer.api.exception.CustomerException;
import com.minibanking.customer.api.model.CustomerRequest;
import com.minibanking.customer.api.repository.CustomerRepository;
import com.minibanking.customer.api.util.CustomerUtility;

@Service
public class CustomerService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Value("${accountservice.hostname:localhost}")
	private String hostname;
	
	@Value("${accountservice.port:8082}")
	private String port;
	
	
	public List<Customer> getAllCustomers(){
		List<Customer> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customers::add);
		return customers;
	}
	
	public Customer addCustomer(CustomerRequest customerRequest) {
		if(!CustomerUtility.isValidDate(customerRequest.getDob())) {
			throw new CustomerException(6000,"Invalid DOB", HttpStatus.BAD_REQUEST);
		}
		Customer customer = new Customer(customerRequest);
		customerRepository.save(customer);
		return customer;
	}
	
	public Optional<Customer> getCustomerById(Long id) {
		return customerRepository.findById(id);
		
	}
	
	public void updateCustomer(Long id, CustomerRequest customerRequest) {
		if(!CustomerUtility.isValidDate(customerRequest.getDob())) {
			throw new CustomerException(6000,"Invalid DOB", HttpStatus.BAD_REQUEST);
		}
		if(getCustomerById(id).isPresent()) {
			Customer customer = new Customer(customerRequest);
			customer.setId(id);
			customerRepository.save(customer);
		} else {
			throw new CustomerException(2000,"Customer not found", HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	public void deleteCustomer(Long id) {
		Optional<Customer> cust = customerRepository.findById(id);
		if(cust.isPresent()) {
			Map<String, Long> param = new HashMap<>();
			param.put("id", id);
			try {
				List response = restTemplate.getForObject("http://" + hostname + ":" + port + "/customer/{id}/accounts", List.class, param);
				if(response != null && response.size() == 0) {
					customerRepository.deleteById(id);
				} else {
					throw new CustomerException(4000, "Cannot delete customer with accounts,delete accounts first ", HttpStatus.BAD_REQUEST);
				}
				
			} catch (RestClientException e) {
				throw new CustomerException(5000, "Unknown exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {			
			throw new CustomerException(2000,"Customer not found", HttpStatus.NOT_FOUND);
		}
	}
	// The below methods will be used for test cases to inject properties
		// from test classes as a workaround solution.
	
	public void setHostName(String hostname) {
		this.hostname = hostname;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
}
