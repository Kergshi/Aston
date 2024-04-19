package org.example.mapper;

import org.example.dto.UserIncomingDto;
import org.example.dto.UserOutGoingDto;
import org.example.entity.Album;
import org.example.entity.Post;
import org.example.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserDtoMapperTest {
    @Spy
    private UserDtoMapperImpl mapper ;

    @Test
    void mapToUser() {
        UserIncomingDto userIncomingDto = new UserIncomingDto(1,"as");
        User user = mapper.mapToUser(userIncomingDto);
        assertEquals("as",user.getUsername());
    }

    @Test
    void mapToOutGoing() {
        User user = new User(1L, "testUser", Collections.emptyList(), Collections.emptyList());
        UserOutGoingDto outGoingDto = mapper.mapToOutGoing(user);
        assertEquals("testUser", outGoingDto.getUsername());
    }

    @Test
    void mapToUotGoings() {
        User user1 = new User(1L, "user1", Collections.emptyList(), Collections.emptyList());
        User user2 = new User(2L, "user2", Collections.emptyList(), Collections.emptyList());
        List<User> users = Arrays.asList(user1, user2);

        List<UserOutGoingDto> outGoingDtos = mapper.mapToUotGoings(users);

        assertEquals(2, outGoingDtos.size());
        assertEquals("user1", outGoingDtos.get(0).getUsername());
        assertEquals("user2", outGoingDtos.get(1).getUsername());
    }

    @Test
    void mapById() {
        Post post = new Post();
        post.setId(123L);
        Long postId = mapper.mapToId(post);
        assertEquals(123L, postId);
    }

    @Test
    void testMapById() {
        Album album = new Album();
        album.setId(456L);
        Long albumId = mapper.mapToId(album);
        assertEquals(456L, albumId);
    }
}