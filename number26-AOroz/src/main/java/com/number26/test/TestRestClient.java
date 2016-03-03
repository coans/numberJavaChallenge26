package com.number26.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.number26.bean.TransactionStatusResult;
import com.number26.bean.TransactionSumResult;
import com.number26.resource.TransactionResource;

public class TestRestClient {

	static RestTemplate rest = new RestTemplate();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testAddTransactionOK() {
		TransactionResource resource = new TransactionResource(8.5, "Type10", 0);
		HttpEntity entity =  new HttpEntity(resource);
		ResponseEntity<TransactionStatusResult> result = rest.exchange("http://localhost:8080/transactionservice/transaction/10", HttpMethod.PUT, entity, TransactionStatusResult.class);
		assertEquals( result.getStatusCode(), HttpStatus.OK );
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test (expected = Exception.class)
	public void testAddTransactionWrongId() {
		TransactionResource resource = new TransactionResource(6.3, "Type15", 0);
		HttpEntity entity =  new HttpEntity(resource);
		ResponseEntity<TransactionStatusResult> result = rest.exchange("http://localhost:8080/transactionservice/transaction/10", HttpMethod.PUT, entity, TransactionStatusResult.class);
		assertEquals( result.getStatusCode(), HttpStatus.BAD_REQUEST );		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test (expected = Exception.class)
	public void testAddTransactionWrongParentId() {	
		TransactionResource resource = new TransactionResource(6.3, "Type15", 56);
		HttpEntity entity =  new HttpEntity(resource);
		ResponseEntity<TransactionStatusResult> result = rest.exchange("http://localhost:8080/transactionservice/transaction/12", HttpMethod.PUT, entity, TransactionStatusResult.class);
		assertEquals( result.getStatusCode(), HttpStatus.BAD_REQUEST );	
		
	}
	
	@Test
	public void testGetTransactionOK() {	
		ResponseEntity<TransactionResource> result = rest.exchange("http://localhost:8080/transactionservice/transaction/1", HttpMethod.GET, null, TransactionResource.class);
		assertEquals( result.getStatusCode(), HttpStatus.OK );	
	}
	
	@Test (expected = Exception.class)
	public void testGetTransactionWrongId() {	
		ResponseEntity<TransactionResource> result = rest.exchange("http://localhost:8080/transactionservice/transaction/159", HttpMethod.GET, null, TransactionResource.class);
		assertEquals( result.getStatusCode(), HttpStatus.BAD_REQUEST );	
	}	
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testGetTransactionByTypeOK() {	
		ResponseEntity<List> result = rest.exchange("http://localhost:8080/transactionservice/types/Type1", HttpMethod.GET, null, List.class);
		assertEquals( result.getStatusCode(), HttpStatus.OK );	
	}

	@Test
	public void testGetSumByTransactionIdOK() {	
		ResponseEntity<TransactionSumResult> result = rest.exchange("http://localhost:8080/transactionservice/sum/1", HttpMethod.GET, null, TransactionSumResult.class);
		assertEquals( result.getStatusCode(), HttpStatus.OK );	
	}
	
	@Test
	public void testGetSumByTransactionIdWrongId() {	
		ResponseEntity<TransactionSumResult> result = rest.exchange("http://localhost:8080/transactionservice/sum/159", HttpMethod.GET, null, TransactionSumResult.class);
		assertEquals( result.getStatusCode(), HttpStatus.OK );	
	}
}
