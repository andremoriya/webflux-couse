package me.moriya.mapper;

import me.moriya.entity.User;
import me.moriya.model.request.UserRequest;
import me.moriya.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE, // Ignore null values
        nullValueCheckStrategy = ALWAYS // Always check for null values
)
public interface UserMapper {

    /*
    * In this case the UserRequest object is being mapped to a User object,
    * but the UserRequest object does not have an id attribute, so it is ignored in the mapping.
    * */
    @Mapping(target = "id", ignore = true) //
    User toEntity(final UserRequest userRequest);

    @Mapping(target = "id", ignore = true)
    User toEntity(final UserRequest userRequest,
                  @MappingTarget final User user);

    UserResponse toReponse(final User user);

    User toEntity(final UserResponse userResponse);


}
