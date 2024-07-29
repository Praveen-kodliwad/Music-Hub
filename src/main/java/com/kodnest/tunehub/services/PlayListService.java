package com.kodnest.tunehub.services;

import java.util.List;

import com.kodnest.tunehub.entity.Playlist;

public interface PlayListService {

	public void addplaylist(Playlist playlist);

	public List<Playlist> fetchAllPlaylists();

	
}
