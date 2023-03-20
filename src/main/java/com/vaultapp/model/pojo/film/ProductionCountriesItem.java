package com.vaultapp.model.pojo.film;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductionCountriesItem{

	@JsonProperty("iso_3166_1")
	private String iso31661;

	@JsonProperty("name")
	private String name;

	public String getIso31661(){
		return iso31661;
	}

	public String getName(){
		return name;
	}
}