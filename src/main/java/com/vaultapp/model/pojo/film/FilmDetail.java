package com.vaultapp.model.pojo.film;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

public class FilmDetail{

	@JsonProperty("original_language")
	private String originalLanguage;

	@JsonProperty("imdb_id")
	private String imdbId;

	@JsonProperty("video")
	private boolean video;

	@JsonProperty("title")
	private String title;

	@JsonProperty("backdrop_path")
	private String backdropPath;

	@JsonProperty("revenue")
	private int revenue;

	@JsonProperty("genres")
	private List<GenresItem> genres;

	@JsonProperty("popularity")
	private Object popularity;

	@JsonProperty("production_countries")
	private List<ProductionCountriesItem> productionCountries;

	@JsonProperty("id")
	private int id;

	@JsonProperty("vote_count")
	private int voteCount;

	@JsonProperty("budget")
	private int budget;

	@JsonProperty("overview")
	private String overview;

	@JsonProperty("original_title")
	private String originalTitle;

	@JsonProperty("runtime")
	private int runtime;

	@JsonProperty("poster_path")
	private String posterPath;

	@JsonProperty("spoken_languages")
	private List<SpokenLanguagesItem> spokenLanguages;

	@JsonProperty("production_companies")
	private List<ProductionCompaniesItem> productionCompanies;

	@JsonProperty("release_date")
	private String releaseDate;

	@JsonProperty("vote_average")
	private Object voteAverage;

	@JsonProperty("belongs_to_collection")
	private Object belongsToCollection;

	@JsonProperty("tagline")
	private String tagline;

	@JsonProperty("adult")
	private boolean adult;

	@JsonProperty("homepage")
	private String homepage;

	@JsonProperty("status")
	private String status;

	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public String getImdbId(){
		return imdbId;
	}

	public boolean isVideo(){
		return video;
	}

	public String getTitle(){
		return title;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public int getRevenue(){
		return revenue;
	}

	public List<GenresItem> getGenres(){
		return genres;
	}

	public Object getPopularity(){
		return popularity;
	}

	public List<ProductionCountriesItem> getProductionCountries(){
		return productionCountries;
	}

	public int getId(){
		return id;
	}

	public int getVoteCount(){
		return voteCount;
	}

	public int getBudget(){
		return budget;
	}

	public String getOverview(){
		return overview;
	}

	public String getOriginalTitle(){
		return originalTitle;
	}

	public int getRuntime(){
		return runtime;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public List<SpokenLanguagesItem> getSpokenLanguages(){
		return spokenLanguages;
	}

	public List<ProductionCompaniesItem> getProductionCompanies(){
		return productionCompanies;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public Object getVoteAverage(){
		return voteAverage;
	}

	public Object getBelongsToCollection(){
		return belongsToCollection;
	}

	public String getTagline(){
		return tagline;
	}

	public boolean isAdult(){
		return adult;
	}

	public String getHomepage(){
		return homepage;
	}

	public String getStatus(){
		return status;
	}
}