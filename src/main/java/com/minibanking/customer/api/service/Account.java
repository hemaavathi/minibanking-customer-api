package com.minibanking.customer.api.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Account {
	
	
	private Long acNo;

	
	private String acType;
	
	private String bsb;
	
	
	private String payId;
	
	
	private Long customerId;
	
	private Long balance;
	
	private String status;
	

	

}
