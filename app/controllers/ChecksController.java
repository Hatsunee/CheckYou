package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import java.util.*;

import play.data.Form;
import static play.data.Form.*;

import models.entity.Check;
import models.request.Check.ResultPostRequest;
import models.service.Check.CheckModelService;


import utils.ConfigUtil;
import utils.OptionUtil;
import play.libs.F.*;

import java.net.URLEncoder;
import java.net.URLDecoder;


public class ChecksController extends Application {

  public static Result index() {
    //文字列は、application.confで設定したcheckyou.setting.message.title、checkyou.setting.message.questionを渡す
    Option<String> titleOpt = ConfigUtil.get("checkyou.setting.message.title");
    Option<String> questionOpt = ConfigUtil.get("checkyou.setting.message.question");
    String title = titleOpt.getOrElse("confの読み込みエラーです");
    String question = questionOpt.getOrElse("confの読み込みエラーです");

    //セッション保存
    session("sessionText", "せっしょん");
    //クッキーも保存
    String cookie = "";
    try{
      cookie = URLEncoder.encode("くっきー","UTF-8");
    }catch(Exception e){
    }
    response().setCookie("myName", cookie);



    return ok(index.render(title,question,new Form(ResultPostRequest.class)));
  }
  
  public static Result result() {
    //フォーム受け取り
    Form<ResultPostRequest> form = form(ResultPostRequest.class).bindFromRequest();

    //バリデーションチェック
    // error.required
    if(form.error("name") != null && form.error("name").message().contains("error.required")) {
        return validationErrorIndexResult("名前欄が空です", form);
    }

    // error.pattern
    if(form.error("name") != null && form.error("name").message().contains("error.pattern")) {
        return validationErrorIndexResult("Twitter id形式ではありません", form);
    }

    // error.maxLength
    if(form.error("name") != null && form.error("name").message().contains("error.maxLength")) {
        return validationErrorIndexResult("文字数が15文字を超えています", form);
    }    

    //エラーでないか判定
    if(!form.hasErrors()){

      //フォームから名前取り出し
      ResultPostRequest rpr = form.get();
      String name = rpr.getName();

      //タイトル作成
      Option<String> titleOpt = ConfigUtil.get("checkyou.setting.message.resultTitle");
      String title = titleOpt.getOrElse("confの読み込みエラーです");
      title = name + title;

      //checkをインスタンス化してDBに保存
      Check check = new Check(name);
      check.result();
      check.store();
      System.out.println("name:"+name +" id:"+check.id);

      //クッキーを取得
      String myCookie = request().cookie("myName").value();
      try{
        myCookie = URLDecoder.decode(myCookie,"UTF-8");
      }catch(Exception e){
      }      
      title += myCookie;

      return ok(result.render(title,check));

    }else{
      Application.fail(routes.ChecksController.index(),"error","診断エラーです");
      return redirect("/");      
    }

  }

  // 診断結果ページシェア用
  public static Result resultId(Long id) {
      Option<Check> check        = new Check(id).unique();
      return check.isDefined() ? ok(result.render(check.get().name + ConfigUtil.get("checkyou.setting.message.resultTitle").getOrElse(""), check.get())) : Application.fail(routes.ChecksController.index(), "error", "診断エラーです");
  }

  public static Result recent(Integer page) {
    //クッキー削除
    response().discardCookie("myName");    

    //title取得
    Option<String> titleOpt = ConfigUtil.get("checkyou.setting.message.recentTitle");
    String title = titleOpt.getOrElse("confの読み込みエラーです");    

    //message取得
    Option<String> messageOpt = ConfigUtil.get("checkyou.setting.message.recentDescription");
    String message = messageOpt.getOrElse("confの読み込みエラーです");
   
    //entryList取得
    CheckModelService cms = new CheckModelService();
    Option<List<Check>> entryListOpt = cms.findWithPage(page);
    List<Check> entryList = entryListOpt.get();

    //entry人数がゼロだった場合の処理
    if(entryList.size() == 0){
        titleOpt = ConfigUtil.get("checkyou.setting.message.recentDescriptionNone");
        title = titleOpt.getOrElse("confの読み込みエラーです"); 
        messageOpt = ConfigUtil.get("checkyou.setting.message.error.noResultList");
        message = messageOpt.getOrElse("confの読み込みエラーです");           
      return ok(recentEmpty.render(title,message));      
    }

    //maxPage取得（最大ページが取得できない場合は1）
    Integer maxPage = new Check().entitiesMaxPage(1);   
    System.out.println("page:"+page);

    return ok(recent.render(title,message,entryList,page,maxPage));
  }

  // バリデーションエラーメッセージを表示し、トップページへ戻る
  private static Result validationErrorIndexResult(String message, Form<ResultPostRequest> form) {
    flash("error", message);
    return badRequest(index.render(
        ConfigUtil.get("checkyou.setting.message.title").getOrElse(""),
        ConfigUtil.get("checkyou.setting.message.question").getOrElse(""),
        form));
  }  
}