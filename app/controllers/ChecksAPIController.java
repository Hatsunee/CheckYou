package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import models.entity.Check;
import models.response.Check.ChecksDefaultResponse;
import models.response.Check.CheckPagingResponse;
import models.setting.CheckYouStatusSetting;
import models.response.Check.CheckResponse;
import models.service.Check.CheckModelService;
import models.service.api.Check.CheckResponseService;


import utils.ConfigUtil;
import utils.OptionUtil;
import play.libs.F.*;

import java.util.*;

import play.libs.Json;


public class ChecksAPIController extends Controller {

  // 診断結果取得
  public static Result checks(Long id) {
    ChecksDefaultResponse result = new ChecksDefaultResponse();
    Option<Check> checkOps = new Check(id).unique();
    if(checkOps.isDefined()) {
      //checkを取り出して、CheckResponseに変換
      Check check = checkOps.getOrElse(new Check());
      System.out.println("check:"+check.id);
      CheckResponse checkresponse = result.response(check).get();
      System.out.println("checkresponse:"+checkresponse.id);

      // resultのプロパティに値セット
      CheckYouStatusSetting status = CheckYouStatusSetting.OK;
      result.code = status.code;
      result.status = status.message;
      result.message = "test";
      result.result = checkresponse;

      // JSON形式に変換して返す
      return ok(Json.toJson(result));
    }
    return ok(Json.toJson(result.badRequest(ConfigUtil.get("checkyou.setting.message.error.noResult").getOrElse(""))));
  }

  // 診断結果の一覧取得
  public static Result getList(Integer page) {
    CheckPagingResponse result = new CheckPagingResponse();

    //entryList取得
    CheckModelService cms = new CheckModelService();
    Option<List<Check>> entryListOpt = cms.findWithPage(page);
    if(entryListOpt.get().size() != 0) {

        List<Check> entryList = entryListOpt.get();
        List<CheckResponse> checkresponse = new ArrayList<CheckResponse>();
        CheckResponseService crs = new CheckResponseService();        
        for(Check check: entryList){
            checkresponse.add(crs.getCheckResponse(check).get());
        }

        // resultのプロパティに値セット
        CheckYouStatusSetting status = CheckYouStatusSetting.OK;
        result.code = status.code;
        result.status = status.message;
        result.message = "test";
        result.maxPage = cms.getMaxPage().get();
        result.results = checkresponse;      

        return ok(Json.toJson(result));
    }
    return ok(Json.toJson(result.badRequest(ConfigUtil.get("checkyou.setting.message.error.noResult").getOrElse(""))));
  }


}