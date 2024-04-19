package org.example.service;

import org.example.dto.UserIncomingDto;
import org.example.dto.UserOutGoingDto;
import org.example.entity.User;
import org.example.mapper.UserDtoMapper;
import org.example.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public UserOutGoingDto save(UserIncomingDto userIncomingDto) {
        User user = userRepository.save(userDtoMapper.mapToUser(userIncomingDto));
        return userDtoMapper.mapToOutGoing(user);
    }

    public void update(UserIncomingDto userIncomingDto) {
        User user = userDtoMapper.mapToUser(userIncomingDto);
        userRepository.update(user);
    }

    public boolean deleteById(long id) {
        return userRepository.deleteById(id);
    }

    public UserOutGoingDto findById(long id) {
        User user = userRepository.findById(id);
//        List<Post> posts = userRepository.findPostsByUserId(id);
//        List<Album> albums = userRepository.findAllByAuthorId(id);
//        user.setPosts(posts);
//        user.setAlbums(albums);
        return userDtoMapper.mapToOutGoing(user);
    }

    public List<UserOutGoingDto> findAll() {
        List<User> users = userRepository.findAll();
        return userDtoMapper.mapToUotGoings(users);
    }
//
//    public void addPost(Post post) {
//        User author = post.getAuthor();
//        author.getPosts().add(post);
//    }
}
