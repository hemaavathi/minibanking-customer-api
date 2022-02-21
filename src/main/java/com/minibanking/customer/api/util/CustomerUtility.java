package com.minibanking.customer.api.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minibanking.customer.api.model.CustomerRequest;

public class CustomerUtility {
	
	static Logger logger = LoggerFactory.getLogger(CustomerUtility.class);
	
	public static boolean isValidDate(String date) {
		boolean validDate = false;
		try {
			LocalDate parsedDate = LocalDate.parse(date);
			validDate = true;			
		} catch(DateTimeParseException e){
			logger.error(e.getMessage());
		}
		return validDate;
		
	}
	
	
}