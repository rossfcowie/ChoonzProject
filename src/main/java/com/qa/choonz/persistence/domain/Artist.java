package com.qa.choonz.persistence.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.qa.choonz.rest.dto.ArtistDTO;

@Entity
@Table(name = "artist")
public class Artist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(max = 100)
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Album> albums = new ArrayList<>();
	
	@ManyToMany(targetEntity = Album.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "featured", 
    		inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
     joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
	private List<Album> featuredAlbums = new ArrayList<>();
	
	public Artist() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Artist(ArtistDTO artistDTO) {
		super();
		this.id = artistDTO.getId();
		this.name = artistDTO.getName();
	}

	public Artist(long id, @NotNull @Size(max = 100) String name, List<Album> albums) {
		super();
		this.id = id;
		this.name = name;
		this.albums = albums;
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

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Artist [id=").append(id).append(", name=").append(name).append(", albums=").append(albums).append(", features on=").append(featuredAlbums)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(albums, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Artist)) {
			return false;
		}
		Artist other = (Artist) obj;
		return Objects.equals(albums, other.albums) && id == other.id && Objects.equals(name, other.name);
	}

	public void addAlbum(Album album) {
		featuredAlbums.add(album);
		
	}

	public List<Album> getFeaturedAlbums() {
		return featuredAlbums;
	}

	public void setFeaturedAlbums(List<Album> featuredAlbums) {
		this.featuredAlbums = featuredAlbums;
	}
	
	public ArrayList<Long> getAlbumIds() {
		ArrayList<Long> ids = new ArrayList<>();
		if(albums!=null) {
		albums.forEach(album -> {
			ids.add(album.getId());
		});}
		return ids;
	}

}
