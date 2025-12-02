package com.hardnets.coop.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.hardnets.coop.model.dto.views.AppViews;

import lombok.Data;

@Data
public class PageResponse<T> {
	@JsonView(AppViews.Public.class)
	private long totalElements;
	@JsonView(AppViews.Public.class)
	private List<T> content;
}
