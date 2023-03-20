package com.vaultapp.model.pojo.film;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductionCompaniesItem{

	@JsonProperty("logo_path")
	private String logoPath;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	@JsonProperty("origin_country")
	private String originCountry;

	public String getLogoPath(){
		return logoPath;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getOriginCountry(){
		return originCountry;
	}
}