package com.qa.choonz.utils.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.ArtistNotFoundException;
import com.qa.choonz.exception.TrackNotFoundException;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.AlbumDTO;

@Service
public class AlbumMapper {

	private TrackRepository tRepo;
	private ArtistRepository aRepo;
	private GenreRepository gRepo;
Genre emptyGenre = new Genre();
Artist emptyArtist = new Artist();
	@Autowired
	public AlbumMapper(TrackRepository tRepo, GenreRepository gRepo, ArtistRepository aRepo) {
		super();
		this.tRepo = tRepo;
		this.aRepo = aRepo;
		this.gRepo = gRepo;
	}

	public AlbumDTO MapToDTO(Album album) {
		AlbumDTO albumDTO = new AlbumDTO();
		albumDTO.setId(album.getId());
		albumDTO.setName(album.getName());
		try {
			albumDTO.setGenre(album.getGenre().getId());

		} catch (Exception e) {
			albumDTO.setGenre(0L);
			
		}
		
		try {
			albumDTO.setArtist(album.getArtist().getId());

		} catch (Exception e) {
			albumDTO.setArtist(0L);
		}
		
		albumDTO.setCover(album.getCover());
		ArrayList<Long> tracks = new ArrayList<Long>();
		for (Track track : album.getTracks()) {
			track.setAlbum(album);
			tracks.add(track.getId());
		}
		tRepo.saveAll(album.getTracks());
		albumDTO.setTracks(tracks);
		albumDTO.setCover(album.getCover());
		albumDTO.setFeaturedArtists(album.getFeaturedArtistIds());
		return albumDTO;
	}
	
	public Album MapFromDTO(AlbumDTO albumDTO) {
		Album album = new Album();
		album.setId(albumDTO.getId());
		album.setName(albumDTO.getName());
		if(albumDTO.getGenre()>0) {
		try {
			album.setGenre(gRepo.findById(albumDTO.getGenre()).get());

		} catch (Exception e) {
			album.setGenre(null);
		}
		}else {
			album.setGenre(null);
		}
		if(albumDTO.getArtist()>0) {
		try {
			album.setArtist(aRepo.findById(albumDTO.getArtist()).get());

		} catch (Exception e) {
			album.setArtist(null);
		}
		}else {
			album.setArtist(null);
		}
		album.setCover(albumDTO.getCover());
		
		ArrayList<Track> tracks = new ArrayList<Track>();
		if(albumDTO.getTracks().size()>0) {
		for (Long track : albumDTO.getTracks()) {
			Track tra = (tRepo.findById(track).orElseThrow(TrackNotFoundException::new));
			tracks.add(tra);
		}
		}
		album.setTracks(tracks);

		
		ArrayList<Artist> featuredArtists = new ArrayList<Artist>();
		if(albumDTO.getFeaturedArtists().size()>0) {
		for (Long artist : albumDTO.getFeaturedArtists()) {
			Artist art = (aRepo.findById(artist).orElseThrow(ArtistNotFoundException::new));
			art.getAlbums().add(album);
			featuredArtists.add(art);
		}
		}
		album.setFeaturedArtists(featuredArtists);
	
		return album;
	}
}
