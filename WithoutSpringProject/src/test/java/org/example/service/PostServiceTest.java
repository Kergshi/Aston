package org.example.service;

import org.example.dto.PostIncomingDto;
import org.example.dto.PostOutGoingDto;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.mapper.PostDtoMapper;
import org.example.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private PostDtoMapper postDtoMapper;

    @InjectMocks
    private PostService postService;

    @Test
    void testSaveSuccessful() {
        PostIncomingDto incomingDto = new PostIncomingDto("Test content", 1L);
        Post post = new Post();
        post.setContent("Test content");

        PostOutGoingDto expectedDto = new PostOutGoingDto("Test content", 1L);

        when(postDtoMapper.map(incomingDto)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        when(postDtoMapper.map(post)).thenReturn(expectedDto);

        PostOutGoingDto result = postService.save(incomingDto);

        assertEquals(expectedDto.getContent(), result.getContent());
        assertEquals(expectedDto.getAuthorId(), result.getAuthorId());

        verify(postDtoMapper).map(incomingDto);
        verify(postRepository).save(post);
        verify(postDtoMapper).map(post);
    }

    @Test
    void testUpdateSuccessful() {
        PostIncomingDto incomingDto = new PostIncomingDto("Updated content", 1L);
        Post post = new Post();
        post.setContent("Updated content");

        when(postDtoMapper.map(incomingDto)).thenReturn(post);

        postService.update(incomingDto);

        verify(postDtoMapper).map(incomingDto);
        verify(postRepository).update(post);
    }

    @Test
    void testDeleteByIdSuccessful() {
        long postId = 1L;

        postService.deleteById(postId);

        verify(postRepository).deleteById(postId);
    }

    @Test
    void testFindByIdSuccessful() {
        long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setContent("Test content");
        post.setAuthor(new User());
        PostOutGoingDto expectedDto = new PostOutGoingDto("Test content", null);

        when(postRepository.findById(postId)).thenReturn(post);
        when(postDtoMapper.map(post)).thenReturn(expectedDto);

        PostOutGoingDto result = postService.findById(postId);

        assertEquals(expectedDto.getContent(), result.getContent());
        assertNull(result.getAuthorId());

        verify(postRepository).findById(postId);
        verify(postDtoMapper).map(post);
    }
    @Test
    public void testFindAllSuccessful() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, "Content 1", new User()));
        posts.add(new Post(2, "Content 2", new User()));

        List<PostOutGoingDto> expectedDtos = Arrays.asList(
                new PostOutGoingDto("Content 1", null),
                new PostOutGoingDto("Content 2", null)
        );
        when(postRepository.findAll()).thenReturn(posts);
        when(postDtoMapper.map(posts)).thenReturn(expectedDtos);

        List<PostOutGoingDto> result = postService.findAll();

        assertEquals(2, result.size());
        assertEquals("Content 1", result.get(0).getContent());
        assertEquals("Content 2", result.get(1).getContent());
    }
}