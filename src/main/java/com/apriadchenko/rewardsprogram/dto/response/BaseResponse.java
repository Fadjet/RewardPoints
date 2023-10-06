package com.apriadchenko.rewardsprogram.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
public class BaseResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -8896162154585443998L;
    private Result result;
}
