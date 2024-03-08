package me.moriya.mapper;

import me.moriya.entity.User;
import me.moriya.model.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    /*
    * In this case the UserRequest object is being mapped to a User object,
    * but the UserRequest object does not have an id attribute, so it is ignored in the mapping.
    * */
    @Mapping(target = "id", ignore = true) //
    User toEntity(final UserRequest userRequest);
}
