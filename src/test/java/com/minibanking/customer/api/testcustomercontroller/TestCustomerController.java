package com.minibanking.customer.api.testcustomercontroller;

import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minibanking.customer.api.controller.CustomerController;
import com.minibanking.customer.api.entity.Customer;
import com.minibanking.customer.api.model.CustomerRequest;
import com.minibanking.customer.api.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class TestCustomerController {

	@MockBean
	CustomerService customerService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testGetAllCustomers() throws Exception {
		Customer customer1 = new Customer(1002022001L,"sam","1978-04-03","Melbourne","9847878986","sam@klf.com","DL","298498");
		Customer customer2 = new Customer(1002022002L,"pam","1978-03-23","Melbourne","9999888888","pam@klf.com","Passport","P0929848");

		List<Customer> customers = new ArrayList<>();
		customers.add(customer1);
		customers.add(customer2);
		
		
		Mockito.when(customerService.getAllCustomers()).thenReturn(customers);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON));
						//.andExpect(jsonPath("$.*", isA(ArrayList.class)));
	}
	
	@Test
	public void testAddCustomer() throws Exception {
		CustomerRequest customerRequest = new CustomerRequest("sam","1978-04-04","Melbourne","9847878988","sam@klf.com","DL","298498");
		Customer customer = new Customer(customerRequest);
		customer.setId(1002022004L);
		Mockito.when(customerService.addCustomer(customerRequest)).thenReturn(customer);
		ObjectMapper objectMapper = new ObjectMapper();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/customer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerRequest)))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void testInvalidPhoneNumber() throws Exception {
		CustomerRequest customerRequest = new CustomerRequest("sam","1978-04-04","Melbourne","984787898","sam@klf.com","DL","298498");
				mockMvc.perform(MockMvcRequestBuilders.post("/customer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customerRequest)))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testInvalidName() throws Exception {
		CustomerRequest customerRequest = new CustomerRequest("sam12","1978-04-04","Melbourne","9847878998","sam@klf.com","DL","298498");
				mockMvc.perform(MockMvcRequestBuilders.post("/customer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customerRequest)))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		CustomerRequest customerRequest = new CustomerRequest("sam","1978-04-04","Melbourne","9847878988","sam@klf.com","DL","298498");

		ObjectMapper objectMapper = new ObjectMapper();
		
		mockMvc.perform(MockMvcRequestBuilders.put("/customer/1002022002")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerRequest)))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/customer/1002022002")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testGetCustomerById() throws Exception {
		Customer customer = new Customer(1002022001L,"sam","1978-04-03","Melbourne","9847878986","sam@klf.com","DL","298498");
		
		Mockito.when(customerService.getCustomerById(1002022001L)).thenReturn(Optional.of(customer));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/1002022001")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print());
	}
	
}
