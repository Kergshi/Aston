package org.example.mapper;

import org.example.dto.AlbumIncomingDto;
import org.example.dto.AlbumOutGoingDto;
import org.example.entity.Album;
import org.example.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlbumDtoMapper {
    Album map(AlbumIncomingDto albumIncomingDto);

    AlbumOutGoingDto map(Album album);

    List<AlbumOutGoingDto> map(List<Album> albums);

    default Long mapToId(Post post) {
        return post.getId();
    }
}
