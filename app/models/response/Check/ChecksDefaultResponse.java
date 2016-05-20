package models.response.Check;

import models.entity.Check;
import static play.libs.F.*;

import models.entity.Check;

import utils.ConfigUtil;
import utils.OptionUtil;
import play.libs.F.*;

import models.service.api.Check.CheckResponseService;


public class ChecksDefaultResponse {
    public Integer code;
    public String status;
    public String message;
    public CheckResponse result;

    public ChecksDefaultResponse(){}

    // レスポンスDTOを取得
    public Option<CheckResponse> response(Check response) {
        return CheckResponseService.use().getCheckResponse(response);
    }

    // BadRequestを取得
    public ChecksDefaultResponse badRequest(String message) {
        return CheckResponseService.use().getBadRequest(message);
    }
}