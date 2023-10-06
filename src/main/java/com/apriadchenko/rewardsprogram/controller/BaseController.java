package com.apriadchenko.rewardsprogram.controller;

import com.apriadchenko.rewardsprogram.dto.response.BaseResponse;
import com.apriadchenko.rewardsprogram.dto.response.Result;

public abstract class BaseController {

    protected <T extends BaseResponse> T sendResponse(T output) {
        output.setResult(Result.builder().returnCode(200).success(true).build());
        return output;
    }
}
