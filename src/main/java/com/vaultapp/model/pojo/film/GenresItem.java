package com.vaultapp.model.pojo.film;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenresItem{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}
}