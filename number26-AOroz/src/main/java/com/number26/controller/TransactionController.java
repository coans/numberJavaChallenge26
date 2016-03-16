package com.number26.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.number26.bean.TransactionStatusResult;
import com.number26.bean.TransactionSumResult;
import com.number26.entity.Transaction;
import com.number26.exception.IDExistException;
import com.number26.resource.TransactionResource;
import com.number26.resource.TransactionResourceAssembler;


@RestController
@RequestMapping(value = "/transactionservice")
public class TransactionController extends BaseApiContoller{

	protected static Logger log = Logger.getLogger(TransactionController.class.getName());
	
	private static Map<Long, Transaction> transactions = new HashMap<Long, Transaction>();

	@Autowired
	private TransactionResourceAssembler transactionResourceAssembler;
	
	@PostConstruct
	public void loadTransactions() {
		Transaction t1 = new Transaction(1, 0, "Type1", 10);
		Transaction t2 = new Transaction(2, 1, "Type2", 15.5);
		Transaction t3 = new Transaction(3, 2, "Type3", 20);
		
		transactions.put(new Long(1), t1);
		transactions.put(new Long(2), t2);
		transactions.put(new Long(3), t3);
	}
    
	@RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.PUT)
	public ResponseEntity<TransactionStatusResult> addTransaction(@RequestBody TransactionResource transactionResource, @PathVariable long transactionId) throws Exception {
		Transaction transaction = transactionResourceAssembler.fromResource(transactionResource, transactionId);
		validate(transaction);
		transactions.put(transactionId, transaction);
		log.info("Transaction added.");
		
		return new ResponseEntity<>(new TransactionStatusResult("ok"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.GET)
	public ResponseEntity<TransactionResource> getTransaction(@PathVariable long transactionId) throws Exception {
		if (transactions.containsKey(transactionId)) {
			TransactionResource result = transactionResourceAssembler.toResource(transactions.get(transactionId));
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(new TransactionResource(), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/types/{type}", method = RequestMethod.GET)
	public ResponseEntity<List<Long>> getTransactionByType(@PathVariable String type) throws Exception {
		List<Long> result = new ArrayList<Long>();
		for (Transaction transaction : transactions.values()) {
			if (transaction.getType().equals(type)) {
				result.add(transaction.getId());
			}
		}
		log.info("Found [" + result.size() + "] transactions with type: " + type);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sum/{transactionId}", method = RequestMethod.GET)
	public ResponseEntity<TransactionSumResult> getSumByTransactionId(@PathVariable long transactionId) throws Exception {
		double sum = 0;
		for (Transaction transaction : transactions.values()) {
			if (transaction.getId() == transactionId || transaction.getParentId() == transactionId) {
				sum += transaction.getAmount();
			}
		}
		TransactionSumResult result = new TransactionSumResult(sum);
		log.info("Total amount for transaction ID [" + transactionId + "] is: " + result.getSum());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	private boolean validate(final Transaction transaction) throws Exception {
		if (transaction.getId() == 0) {
			throw new Exception("Transaction ID can not be 0!");
		}
		if (transactions.containsKey(transaction.getId())) {
			throw new IDExistException("Transaction with ID: " + transaction.getId() + " already exists!");
		}
		if (transaction.getParentId() != 0) {
			if (!transactions.containsKey(transaction.getParentId())) {
				throw new Exception("There is no parent transaction with ID: " + transaction.getParentId());
			}	
		}
		
		return true;
	}
}
