package com.vaultapp.model.pojo.book;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

	@JsonProperty("q")
	private String q;

	@JsonProperty("docs")
	private List<DocsItem> docs;

	@JsonProperty("offset")
	private Object offset;

	@JsonProperty("numFound")
	private int numFound;

	@JsonProperty("start")
	private int start;

	@JsonProperty("numFoundExact")
	private boolean numFoundExact;

	@JsonProperty("num_found")
	private int num_Found;

	public String getQ(){
		return q;
	}

	public List<DocsItem> getDocs(){
		return docs;
	}

	public Object getOffset(){
		return offset;
	}

	public int getNumFound(){
		return numFound;
	}

	public int getStart(){
		return start;
	}

	public boolean isNumFoundExact(){
		return numFoundExact;
	}

	public int getNum_Found(){
		return numFound;
	}
}