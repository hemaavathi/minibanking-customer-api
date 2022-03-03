package com.minibanking.customer.api.repositorytests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.minibanking.customer.api.entity.Customer;
import com.minibanking.customer.api.repository.CustomerRepository;

@DataJpaTest
public class CustomerRepositoryTests {
	
	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private EntityManager entity;
	
	
	@Test
	public void testGetAllCustomers() {
		Customer cust1 = Customer.builder()
				.name("Hema")
				.address("Australia")
				.dob("2020-11-20")
				.email("hema@igreendata.com")
				.kycNo("1234")
				.kycType("DL").build();
		
		Customer cust2 = Customer.builder()
				.name("Muraly")
				.address("India")
				.dob("2011-11-20")
				.email("muraly@igreendata.com")
				.kycNo("12345678")
				.kycType("DL").build();		
		
		entity.persist(cust1);
		entity.persist(cust2);
		entity.flush();
		entity.close();

		List<Customer> custList = repo.findAll();
		
		assertNotNull(custList);
		assertEquals(2, custList.size());
		
	}
	
	@Test
	public void testDeleteCustomer() {
		Customer cust1 = Customer.builder()
				.name("Sam")
				.address("Australia")
				.dob("2020-11-20")
				.email("hema@igreendata.com")
				.kycNo("1234")
				.kycType("DL").build();
		
		Customer cust2 = Customer.builder()
				.name("Pam")
				.address("India")
				.dob("2011-11-20")
				.email("muraly@igreendata.com")
				.kycNo("12345678")
				.kycType("DL").build();		
		
		entity.persist(cust1);
		entity.persist(cust2);
		entity.flush();
		entity.close();
		
		repo.delete(cust2);
		
		List<Customer> custList = repo.findAll();
		
		assertNotNull(custList);
		assertEquals(1,custList.size());
	}
	
	@Test
	public void testAddCustomer() {
		
		Customer customer = Customer.builder()
					.name("Pam")
					.address("India")
					.dob("2011-11-20")
					.email("pam@yahoo.com")
					.kycNo("12345678")
					.kycType("DL").build();	
		
		entity.persist(customer);
		entity.flush();
		entity.close();
		
		repo.save(customer);
		
		List<Customer> custList = repo.findAll();
		
		assertNotNull(custList);
		assertEquals(1,custList.size());
		
	}
	
	

}
