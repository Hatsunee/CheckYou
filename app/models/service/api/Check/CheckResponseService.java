package models.service.api.Check;

import models.entity.Check;
import models.response.Check.CheckResponse;
import models.response.Check.ChecksDefaultResponse;
import models.setting.CheckYouStatusSetting;
import utils.OptionUtil;
import static play.libs.F.*;

// レスポンス用サービスクラス
public class CheckResponseService {

    public static CheckResponseService use() {
        return new CheckResponseService();
    }

    // BadRequestを受け取る
    public ChecksDefaultResponse getBadRequest(String message) {
        ChecksDefaultResponse result = new ChecksDefaultResponse();
        CheckYouStatusSetting status = CheckYouStatusSetting.NO_RESULT;
        result.code    = status.code;
        result.status  = status.message;
        result.message = message;
        return result;
    }


    // CheckからCheckResponseレスポンスへの変換クラス
    public Option<CheckResponse> getCheckResponse(Check response) {
        Option<Check> checkOps = OptionUtil.apply(response);
        Check check = checkOps.getOrElse(new Check());
        CheckResponse checkresponse = new CheckResponse(check.id,check.name,check.result,check.created,check.modified);
        return OptionUtil.apply(checkresponse);
    }
}