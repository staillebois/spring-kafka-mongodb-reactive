package com.seal.reactive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Account {
 
    @Id
    private String id;
    private String owner;
    private Double value;
    
	public Account(String id, String owner, Double value) {
		this.id = id;
		this.owner = owner;
		this.value = value;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
}