package models.service.Check;

import java.util.*;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import play.data.format.*;
import com.avaje.ebean.annotation.*;
import play.data.validation.Constraints.*;

import utils.OptionUtil;
import play.libs.F.*;

import models.entity.Check;
import models.service.Model.ModelService;

public class CheckModelService implements ModelService<Check> {

    //定数
    final private static Integer LIMIT = 5;  // １ページあたりの表示件数


    public static CheckModelService use() {
        return new CheckModelService();
    }


    // IDで検索
    @Override
    public Option<Check> findById(Long id) {
        Finder<Long, Check> find = new Finder<Long, Check>(Long.class, Check.class);
        return OptionUtil.apply(find.byId(id));
    }    

    // 保存
    @Override
    public Option<Check> save(Check entry) {
        Option<Check> entryOp = OptionUtil.apply(entry);
        if(entryOp.isDefined()) {
            entry.save();
            if(OptionUtil.apply(entry.id).isDefined()) {
                return OptionUtil.apply(entry);
            }
        }
        return new None<Check>();
    }


    // ページ番号で取得
    @Override
    public Option<List<Check>> findWithPage(Integer pageSource) {

        // Ebeanではページが0から始まるためページ番号調整
        Integer pageNum = (pageSource - 1 < 0)? 0 : pageSource - 1;

        // findPagingListを使用し, 指定したページ番号、指定ページ表示件数（LIMIT）、作成日昇順のOption<Check>のListを取得
        Finder<Long, Check> find = new Finder<Long, Check>(Long.class, Check.class);
        List<Check> list = find.orderBy("created desc").findPagingList(LIMIT).getPage(pageNum).getList();
        return OptionUtil.apply(list);

    } 

   // 最大ページ数を取得
    public Option<Integer> getMaxPage() {
        Finder<Long, Check> find = new Finder<Long, Check>(Long.class, Check.class);
        Integer maxPage = find.orderBy("created desc").findPagingList(LIMIT).getTotalPageCount();
        return OptionUtil.apply(maxPage);
    }       


}
