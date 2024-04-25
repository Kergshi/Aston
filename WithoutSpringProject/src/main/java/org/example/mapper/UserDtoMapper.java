package org.example.mapper;

import org.example.dto.UserIncomingDto;
import org.example.dto.UserOutGoingDto;
import org.example.entity.Album;
import org.example.entity.Post;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {
    User mapToUser(UserIncomingDto userIncomingDto);

    UserOutGoingDto mapToOutGoing(User user);

    List<UserOutGoingDto> mapToUotGoings(List<User> user);

    default Long mapToId(Post post) {
        return post.getId();
    }

    default Long mapToId(Album album) {
        return album.getId();
    }
}
