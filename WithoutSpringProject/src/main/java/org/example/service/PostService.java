package org.example.service;

import org.example.dto.PostIncomingDto;
import org.example.dto.PostOutGoingDto;
import org.example.entity.Post;
import org.example.mapper.PostDtoMapper;
import org.example.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository;
    private final PostDtoMapper postDtoMapper;

    public PostService(PostRepository postRepository, PostDtoMapper postDtoMapper) {
        this.postRepository = postRepository;
        this.postDtoMapper = postDtoMapper;
    }

    public PostOutGoingDto save(PostIncomingDto postIncomingDto) {
        Post postIn = postDtoMapper.map(postIncomingDto);
        Post postOut = postRepository.save(postIn);
        return postDtoMapper.map(postOut);
    }

    public void update(PostIncomingDto postIncomingDto) {
        Post post = postDtoMapper.map(postIncomingDto);
        postRepository.update(post);
    }

    public boolean deleteById(long id) {
        return postRepository.deleteById(id);
    }

    public PostOutGoingDto findById(long id) {
        Post post = postRepository.findById(id);
        return postDtoMapper.map(post);
    }

    public List<PostOutGoingDto> findAll() {
        List<Post> posts = postRepository.findAll();
        return postDtoMapper.map(posts);
    }
}
