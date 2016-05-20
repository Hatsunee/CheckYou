package models.response.Check;

import java.util.List;

import models.service.api.Check.CheckResponseService;


// API用診断結果一覧のレスポンスモデル
public class CheckPagingResponse {

    public Integer code;
    public String status;
    public String message;
    public Integer maxPage;
    public List<CheckResponse> results;

    // BadRequestを取得
    public ChecksDefaultResponse badRequest(String message) {
        return CheckResponseService.use().getBadRequest(message);
    }
}