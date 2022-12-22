package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.CustomerCustomException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@RestController
@RequestMapping("/rest/auction")
@CrossOrigin(origins = "http://localhost:4200/")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepo;

	// Get all customers
	@GetMapping("/customer")
	public ResponseEntity<List<Customer>> getAllCustomer() {
		List<Customer> list = customerRepo.findAll();
		ResponseEntity<List<Customer>> resList = new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
		return resList;
	}

	// Add a customer
	@PostMapping("/customer")
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
		customerRepo.save(customer);
		ResponseEntity<String> resString = new ResponseEntity<String>("Customer added Successfully", HttpStatus.OK);
		return resString;
	}

	// Get customer by id
	@GetMapping("/customer/{customerID}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerID) {
		Customer customer = customerRepo.findById(customerID)
				.orElseThrow(() -> new CustomerCustomException("Customer not exist with id:" + customerID));
		return ResponseEntity.ok(customer);
	}

	// Delete customer
	@DeleteMapping("/customer/{customerID}")
	public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable Integer customerID) {
		Customer customer = customerRepo.findById(customerID)
				.orElseThrow(() -> new CustomerCustomException("Customer not exist with id:" + customerID));
		customerRepo.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);

	}

	// Update Customer
	@PutMapping("/customer/{customerID}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Integer customerID,
			@RequestBody Customer customerDetails) {
		Customer customer = customerRepo.findById(customerID)
				.orElseThrow(() -> new CustomerCustomException("Customer not exist with id:" + customerID));
		customer.setCustomerName(customerDetails.getCustomerName());
		customer.setCustomerEmail(customerDetails.getCustomerEmail());
		customer.setCustomerPassword(customerDetails.getCustomerPassword());
		customer.setCustomerAddress(customerDetails.getCustomerAddress());
		customer.setCustomerNumber(customerDetails.getCustomerNumber());
		Customer updatedCustomer = customerRepo.save(customer);
		return ResponseEntity.ok(updatedCustomer);
	}
	
	
	
}
