package com.sparta.myblogbackend.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
@Builder
public class PageDto {

  private final Integer currentPage;
  private final Integer size;
  private String sortBy;

  @JsonCreator
  public PageDto(
      @JsonProperty("currentPage") Integer currentPage,
      @JsonProperty("size") Integer size,
      @JsonProperty("sortBy") String sortBy
  ) {
    this.currentPage = currentPage;
    this.size = size;
    this.sortBy = sortBy;
  }

  public Pageable toPageable() {
    if (Objects.isNull(sortBy)) {
      return PageRequest.of(currentPage - 1, size);
    } else {
      return PageRequest.of(currentPage - 1, size, Sort.by(sortBy).descending());
    }
  }

  public Pageable toPageable(String sortBy) {
    return PageRequest.of(currentPage - 1, size, Sort.by(sortBy).descending());
  }
}