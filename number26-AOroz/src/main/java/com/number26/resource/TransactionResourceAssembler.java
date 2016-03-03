package com.number26.resource;


import org.springframework.stereotype.Service;

import com.number26.entity.Transaction;

@Service
public class TransactionResourceAssembler {
	
	public TransactionResource toResource(Transaction entity) {
		TransactionResource resource = new TransactionResource();
		
		resource.setAmount(entity.getAmount());
		resource.setParentId(entity.getParentId());
		resource.setType(entity.getType());
		
		return resource;
	}

	public Transaction fromResource(TransactionResource resource, long transactionId) {
		Transaction entity = new Transaction();
		
		entity.setId(transactionId);
		entity.setAmount(resource.getAmount());
		entity.setParentId(resource.getParentId());
		entity.setType(resource.getType());
		
		return entity;
	}
}
