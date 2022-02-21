package com.minibanking.customer.api.testcustomerservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.minibanking.customer.api.entity.Customer;
import com.minibanking.customer.api.exception.CustomerException;
import com.minibanking.customer.api.model.CustomerRequest;
import com.minibanking.customer.api.repository.CustomerRepository;
import com.minibanking.customer.api.service.Account;
import com.minibanking.customer.api.service.CustomerService;
import com.minibanking.customer.api.util.CustomerUtility;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

	@InjectMocks
	CustomerService customerService;
	
	@Mock
	CustomerRepository customerRepository;

	@Mock
	RestTemplate restTemplate;
	
	@Test
	public void testGetALLCustomers() {
		Customer customer1 = new Customer(1002022001L,"sam","1978-04-03","Melbourne","9847878986","sam@klf.com","DL","298498");
		Customer customer2 = new Customer(1002022002L,"pam","1978-03-23","Melbourne","9999888888","pam@klf.com","Passport","P0929848");
	
		List<Customer> customers = new ArrayList<>();
		customers.add(customer1);
		customers.add(customer2);
		
		when(customerRepository.findAll()).thenReturn(customers);
		
		List<Customer> customerList = customerService.getAllCustomers();
		
		assertEquals(2,customerList.size());
		verify(customerRepository).findAll();
	}
	
	@Test
	public void testGetCustomerById() {
		
		Customer customer = new Customer(1002022001L,"sam","1978-04-03","Melbourne","9847878986","sam@klf.com","DL","298498");
		
		when(customerRepository.findById(1002022001L)).thenReturn(Optional.of(customer));
		
		Optional<Customer> expectedCustomer = customerService.getCustomerById(1002022001L);
		
		assertEquals(expectedCustomer, Optional.of(customer));
	
	}
	
	@Test
	public void testAddCustomer() {
		CustomerRequest customerRequest = new CustomerRequest("sam","1978-04-03","Melbourne","9847878986","sam@klf.com","DL","298498");
		Customer customer = new Customer(customerRequest);
				
		when(customerRepository.save(customer)).thenReturn(customer);
		
		Customer expectedCustomer = customerService.addCustomer(customerRequest);
		
		assertEquals(expectedCustomer, customer);
		
	}
	
	@Test
	public void testUpdateCustomer() {
		CustomerRequest customerRequest = new CustomerRequest("sam","1978-04-03","Melbourne","9847878986","sam@klf.com","DL","298498");
		Customer customer = new Customer(customerRequest);
		customer.setId(1002022001L);
		
		when(customerRepository.findById(1002022001L)).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);
		
		customerService.updateCustomer(1002022001L, customerRequest);
		
		verify(customerRepository).save(customer);
		
	}
	
	
	@Test
	public void testInvalidDate() throws Exception{
		CustomerRequest customerRequest = new CustomerRequest("sam","1978-04-043","Melbourne","9847878986","sam@klf.com","DL","298498");
		
		assertThrows(CustomerException.class, () -> {
			customerService.updateCustomer(1002022001L, customerRequest);
			});
	}
	
		
	@Test
	public void testDeleteCustomer() {
		//mock inputs
		Customer customer = new Customer(1002022001L,"sam","1978-04-03","Melbourne","9847878986","sam@klf.com","DL","298498");
		List<Account> accountList = new ArrayList<>(2);
		//mock downstream calls
		when(customerRepository.findById(1002022001L)).thenReturn(Optional.of(customer));
		Map<String, Long> param = new HashMap<>();
		param.put("id", 1002022001L);		
		when(restTemplate.getForObject("http://localhost:8082/customer/{id}/accounts", List.class, param)).thenReturn(accountList);
		// call the actual unit to test and get output
		customerService.deleteCustomer(1002022001L);
		//validate the actual with the expected
		verify(customerRepository).deleteById(1002022001L);
	}
	
}
