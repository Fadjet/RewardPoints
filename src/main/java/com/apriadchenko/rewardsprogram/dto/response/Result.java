package com.apriadchenko.rewardsprogram.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Result implements Serializable {

	@Serial
	private static final long serialVersionUID = -7548215117985581389L;

	private Boolean success;

	private Integer returnCode;

	private String errorCode;

	private String errorMessage;
}
