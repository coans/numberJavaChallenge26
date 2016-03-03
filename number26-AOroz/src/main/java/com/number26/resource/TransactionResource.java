package com.number26.resource;

public class TransactionResource {

	private double amount;
	private String type;
	private int parentId;
	
	public TransactionResource() {
		super();
	}
	
	public TransactionResource( double amount, String type, int parentId) {
		super();
		this.amount = amount;
		this.type = type;
		this.parentId = parentId;
	}

	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "{ \"type\": " + type + ", \"amount\": " + amount + ", \"parent_id\": " + parentId + "}";
	}
}
