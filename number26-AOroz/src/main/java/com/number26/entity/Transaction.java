package com.number26.entity;

public class Transaction {

	private long id;
	private int parentId;
	private String type;
	private double amount;
	
	public Transaction() {
		super();
	}
	
	public Transaction(long id, int parentId, String type, double amount) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.type = type;
		this.amount = amount;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
		return "{ \"id\": " + id + ", \"type\": " + type + ", \"amount\": " + amount + ", \"parent_id\": " + parentId + "}";
	}
}
