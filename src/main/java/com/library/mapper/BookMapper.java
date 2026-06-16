package com.library.mapper;

import com.library.dto.BookRequestDTO;
import com.library.dto.BookResponseDTO;
import com.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookRequestDTO dto);

    @Mapping(
            target = "categoryId",
            source = "category.id")
    @Mapping(
            target = "categoryName",
            source = "category.name")

    @Mapping(target = "publisher",
            source = "publisher")
    @Mapping(target = "imageName", source = "imageName")
    BookResponseDTO toDto(Book book);
}