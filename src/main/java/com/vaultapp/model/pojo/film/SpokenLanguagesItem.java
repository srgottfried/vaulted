package com.vaultapp.model.pojo.film;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpokenLanguagesItem{

	@JsonProperty("name")
	private String name;

	@JsonProperty("iso_639_1")
	private String iso6391;

	@JsonProperty("english_name")
	private String englishName;

	public String getName(){
		return name;
	}

	public String getIso6391(){
		return iso6391;
	}

	public String getEnglishName(){
		return englishName;
	}
}