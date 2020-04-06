package com.challenge.application.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value=Include.NON_EMPTY)
public class TweetDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String user;
	private String text;
	private String location;
	private boolean valid;
}
