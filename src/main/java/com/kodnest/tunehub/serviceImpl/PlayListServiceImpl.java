package com.kodnest.tunehub.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.tunehub.entity.Playlist;
import com.kodnest.tunehub.repository.PlayListRepository;
import com.kodnest.tunehub.services.PlayListService;

@Service
public class PlayListServiceImpl implements PlayListService{
	@Autowired
	PlayListRepository playListRepository;

	@Override
	public void addplaylist(Playlist playlist) {
	 playListRepository.save(playlist);
		
	}

	@Override
	public List<Playlist> fetchAllPlaylists() {
		List<Playlist> allplaylist=playListRepository.findAll();
		return allplaylist;
	}

	

	
	
	
	
	
}
