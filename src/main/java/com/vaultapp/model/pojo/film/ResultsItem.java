package com.vaultapp.model.pojo.film;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultsItem{

	@JsonProperty("overview")
	private String overview;

	@JsonProperty("original_language")
	private String originalLanguage;

	@JsonProperty("original_title")
	private String originalTitle;

	@JsonProperty("video")
	private boolean video;

	@JsonProperty("title")
	private String title;

	@JsonProperty("genre_ids")
	private List<Integer> genreIds;

	@JsonProperty("poster_path")
	private String posterPath;

	@JsonProperty("backdrop_path")
	private String backdropPath;

	@JsonProperty("release_date")
	private String releaseDate;

	@JsonProperty("popularity")
	private Object popularity;

	@JsonProperty("vote_average")
	private Object voteAverage;

	@JsonProperty("id")
	private int id;

	@JsonProperty("adult")
	private boolean adult;

	@JsonProperty("vote_count")
	private int voteCount;

	public String getOverview(){
		return overview;
	}

	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public String getOriginalTitle(){
		return originalTitle;
	}

	public boolean isVideo(){
		return video;
	}

	public String getTitle(){
		return title;
	}

	public List<Integer> getGenreIds(){
		return genreIds;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public Object getPopularity(){
		return popularity;
	}

	public Object getVoteAverage(){
		return voteAverage;
	}

	public int getId(){
		return id;
	}

	public boolean isAdult(){
		return adult;
	}

	public int getVoteCount(){
		return voteCount;
	}
}