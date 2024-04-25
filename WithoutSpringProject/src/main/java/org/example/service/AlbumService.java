package org.example.service;

import org.example.dto.AlbumIncomingDto;
import org.example.dto.AlbumOutGoingDto;
import org.example.entity.Album;
import org.example.exception.NotFoundException;
import org.example.mapper.AlbumDtoMapper;
import org.example.repository.AlbumRepository;
import org.example.repository.PostRepository;

import java.util.List;

public class AlbumService {
    private final AlbumDtoMapper albumDtoMapper;
    private final AlbumRepository albumRepository;
    private final PostRepository postRepository;

    public AlbumService(AlbumDtoMapper albumDtoMapper, AlbumRepository albumRepository, PostRepository postRepository) {
        this.albumDtoMapper = albumDtoMapper;
        this.albumRepository = albumRepository;
        this.postRepository = postRepository;
    }

    public AlbumOutGoingDto save(AlbumIncomingDto albumIncomingDto) {
        Album album = albumRepository.save(albumDtoMapper.map(albumIncomingDto));
        return albumDtoMapper.map(album);
    }

    public void update(AlbumIncomingDto albumIncomingDto) {
        Album album = albumDtoMapper.map(albumIncomingDto);
        albumRepository.update(album);
    }

    public boolean deleteById(long id) {
        return albumRepository.deleteById(id);
    }

    public AlbumOutGoingDto findById(long id) {
        Album album = albumRepository.findById(id);
        return albumDtoMapper.map(album);
    }

    public List<AlbumOutGoingDto> findAll() {
        List<Album> albums = albumRepository.findAll();
        return albumDtoMapper.map(albums);
    }

    public void addPost(long albumId, long postId) throws NotFoundException {
        checkExistAlbum(albumId);
        checkExistPost(postId);

        Album album = albumRepository.findById(albumId);
        album.getPosts().add(postRepository.findById(postId));
        albumRepository.addPost(albumId, postId);
    }


    private void checkExistPost(long postId) throws NotFoundException {
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException("Post not found.");
        }
    }

    private void checkExistAlbum(long albumId) throws NotFoundException {
        if (!albumRepository.existsById(albumId)) {
            throw new NotFoundException("Album not found.");
        }
    }
}
