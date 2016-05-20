package models.entity;

import java.util.*;

import javax.persistence.*;
import play.db.ebean.*;

import play.data.format.*;
import com.avaje.ebean.annotation.*;
import play.data.validation.Constraints.*;

import models.service.Check.CheckModelService;
import models.service.Check.CheckService;

import utils.OptionUtil;
import play.libs.F.*;


@Entity
@Table(name = "checks")
public class Check extends Model {

    @Id
    public Long id;

    @Required
    public String name;

    @Required
    public String result;

    @Formats.DateTime(pattern="yyyy/MM/dd")
    public Date created;

    @Formats.DateTime(pattern="yyyy/MM/dd")
    public Date modified;

    @Transient  // 永続化しないフィールドを定義。Transient付けないとDBのフィールドとして処理されようとしてエラーになる
    private CheckModelService checkModelService = new CheckModelService();

    @Transient
    private CheckService checkService = new CheckService();    


    // コンストラクタ設定
    public Check() {}

    public Check(String name) {
        this.name = name;
        this.created = new Date();
        this.modified = new Date();                     
    }

    // 検索用オブジェクト生成のためのコンストラクタ
    /*
      使用例. id=10のデータを取り出す
      Check check = new Check(10).method();
    */
    public Check(Long id) {
        this.id = id;
        this.created = new Date();  
        this.modified = new Date();                                   
    }

    public Check(String name, String result) {
        this.name = name;
        this.result = result;
        this.created = new Date();
        this.modified = new Date();
    }

   // 結果を取得
    // 診断結果を取得,DBに保存
    public Option<String> result() {
        Option<String> resultOpt = checkService.getResult(name);
        String result = resultOpt.getOrElse("読み込みエラーです");
        this.result = result;
        return resultOpt;
    }

    // 結果を保存
    public Option<Check> store() {
        return checkModelService.save(this);
    }    

    // idに該当するものを検索
    public Option<Check> unique() {
        return checkModelService.findById(id);
    }

    // 指定ページの一覧
    public Option<List<Check>> entitiesList(Integer page) {
        // CheckModelServiceクラスのメソッド呼び出し
        return checkModelService.findWithPage(page);
    }

    // ページ結果を取得
    public Integer entitiesMaxPage(Integer value) {
        Option<Integer> maxPageOpt = checkModelService.getMaxPage();
        Integer maxPage = maxPageOpt.getOrElse(value);
        System.out.println("最大ページは"+maxPage);
        return maxPage;
    }


    //getter,setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
    

}