package org.example;

import org.example.context.AppContext;
import org.example.db.ConnectionManager;
import org.example.db.HikariConnectionManager;
import org.example.mapper.AlbumDtoMapper;
import org.example.mapper.PostDtoMapper;
import org.example.mapper.UserDtoMapper;
import org.example.repository.AlbumRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.example.service.AlbumService;
import org.example.service.PostService;
import org.example.service.UserService;
import org.example.util.InitSql;
import org.mapstruct.factory.Mappers;

public class PostServiceApp {

    public static void initAppContext() {
        UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);
        AlbumDtoMapper albumDtoMapper = Mappers.getMapper(AlbumDtoMapper.class);
        PostDtoMapper postDtoMapper = Mappers.getMapper(PostDtoMapper.class);
        ConnectionManager connectionManager = HikariConnectionManager.getInstance();
        InitSql.initSqlMigration(connectionManager);

        UserRepository userRepository = new UserRepository(connectionManager);
        PostRepository postRepository = new PostRepository(connectionManager, userRepository);
        AlbumRepository albumRepository = new AlbumRepository(connectionManager, postRepository);
        UserService userService = new UserService(userRepository, userDtoMapper);
        PostService postService = new PostService(postRepository, postDtoMapper);
        AlbumService albumService = new AlbumService(albumDtoMapper, albumRepository, postRepository);

        AppContext.put(UserService.class, userService);
        AppContext.put(AlbumService.class, albumService);
        AppContext.put(PostService.class, postService);

        AppContext.put(PostRepository.class, postRepository);
        AppContext.put(UserRepository.class, userRepository);
        AppContext.put(ConnectionManager.class, connectionManager);
        AppContext.put(UserDtoMapper.class, userDtoMapper);
        AppContext.put(AlbumRepository.class, albumRepository);
    }
}
