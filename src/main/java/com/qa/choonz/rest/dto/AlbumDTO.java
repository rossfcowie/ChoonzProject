package com.qa.choonz.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.qa.choonz.persistence.domain.Album;

public class AlbumDTO {

	private long id;
	private String name;
	private List<Long> tracks;
	private Long artist;
	private Long genre;
	private String cover;
	private List<Long> featuredArtists;

	public AlbumDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AlbumDTO(Album album) {
		super();
		this.id = album.getId();
		this.name = album.getName();
		this.tracks = album.getTracksId();
		this.cover = album.getCover();
		if(album.getGenre()!=null) {
		this.genre = album.getGenre().getId();}
		if(album.getArtist()!=null) {
		this.artist = album.getArtist().getId();}
		this.featuredArtists = album.getFeaturedArtistIds();
	}

	public AlbumDTO(String name, String cover) {
		super();
		this.name = name;
		this.cover = cover;
		this.tracks = new ArrayList<Long>();
		this.genre = 0L;
		this.artist = 0L;
		this.featuredArtists = new ArrayList<Long>();
	}

	public AlbumDTO(long id, String name, List<Long> tracks, Long artist, Long genre, String cover) {
		super();
		this.id = id;
		this.name = name;
		this.tracks = tracks;
		this.artist = artist;
		this.genre = genre;
		this.cover = cover;
		this.featuredArtists = new ArrayList<Long>();
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getTracks() {
		return tracks;
	}

	public void setTracks(List<Long> tracks) {
		this.tracks = tracks;
	}

	public Long getArtist() {
		return artist;
	}

	public void setArtist(Long artist) {
		this.artist = artist;
	}

	public Long getGenre() {
		return genre;
	}

	public void setGenre(Long genre) {
		this.genre = genre;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AlbumDTO [id=").append(id).append(", name=").append(name).append(", tracks=").append(tracks)
				.append(", artist=").append(artist).append(", genre=").append(genre).append(", cover=").append(cover)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(artist, cover, genre, id, name, tracks);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AlbumDTO)) {
			return false;
		}
		AlbumDTO other = (AlbumDTO) obj;
		return Objects.equals(artist, other.artist) && Objects.equals(cover, other.cover)
				&& Objects.equals(genre, other.genre) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(tracks, other.tracks);
	}

	public List<Long> getFeaturedArtists() {
		return featuredArtists;
	}

	public void setFeaturedArtists(List<Long> featuredArtists) {
		this.featuredArtists = featuredArtists;
	}

}
