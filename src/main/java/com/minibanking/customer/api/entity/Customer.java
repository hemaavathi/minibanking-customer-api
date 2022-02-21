package com.minibanking.customer.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.minibanking.customer.api.model.CustomerRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
	@SequenceGenerator(name="customer_generator", sequenceName="customer_id", allocationSize=1)
	@Column(name="id", updatable = false, nullable = false)
	private Long id;
	
	private String name;
	private String dob;
	private String address;
	private String mobile;
	private String email;
	@Column(name="kyctype")
	private String kycType;
	@Column(name="kycno")
	private String kycNo;	

	public Customer(CustomerRequest customerRequest) {
		this.name = customerRequest.getName();
		this.dob = customerRequest.getDob();
		this.address = customerRequest.getAddress();
		this.mobile = customerRequest.getMobile();
		this.email = customerRequest.getEmail();
		this.kycType = customerRequest.getKycType();
		this.kycNo = customerRequest.getKycNo();
	}
	
}
