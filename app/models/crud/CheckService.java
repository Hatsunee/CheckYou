package models.crud;

import com.avaje.ebean.Ebean;
import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;
import models.entity.*;
import static play.libs.F.*;
import models.crud.DateUtil;
import models.crud.OptionUtil;


/**
 * CheckService
 * 
 * @author harakazuhiro
 */
public class CheckService {

    /**
     * find all by list
     * @return
     */
    public static List<Check> page(Integer pageSource) {
        Integer page = (pageSource - 1 < 0) ? 0 : pageSource - 1;
        Model.Finder<Long, Check> find = new Model.Finder<Long, Check>(Long.class, Check.class);
        // return find.all();
        return find.order().asc("created")
                .findPagingList(models.crud.PagingSetting.LIMIT).getPage(page)
                .getList();
    }

    /**
     * find all by list
     * @return
     */
    public static Integer pageMax(Integer pageSource) {
        Integer page = (pageSource - 1 < 0) ? 0 : pageSource - 1;
        Model.Finder<Long, Check> find = new Model.Finder<Long, Check>(Long.class, Check.class);
        // return find.all();
        return find.order().asc("created")
                .findPagingList(models.crud.PagingSetting.LIMIT).getPage(page)
                .getTotalPageCount();
    }


    /**
     * find all by list
     * @return
     */
    public static List<Check> all() {
        Model.Finder<Long, Check> find = new Model.Finder<Long, Check>(Long.class, Check.class);
        // return find.all();
        return find.
            findList();
    }

    /**
     * find one
     * @param id
     * @return
     */
    public static Check first(Long id) {
        Model.Finder<Long, Check> find = new Model.Finder<Long, Check>(Long.class, Check.class);
        // return find.byId(id);
        return find.
            where().
            eq("id", id).
            findUnique();
    }

    /**
     * create record
     * @param model
     * @param bindForm
     * @return
     */
    public static Check create(Check model, play.data.Form<Check> bindForm) {
        model.setCreated(DateUtil.getCurrentDate());
        model.save();

        if(bindForm != null) {

        }

        return model;
    }


    /**
     * update record
     * @param model
     * @param id
     * @param bindForm
     * @return
     */
    public static Check update(Check model, Long id, play.data.Form<Check> bindForm) {
        Check entry;
        if(id != null) {
            entry = first(id);
        } else {
            entry = null;
        }
        if(entry != null) {
            model.setId(id);
            //
            model.update();

            if(bindForm != null) {

            }

            return model;
        }
        return null;
    }

    /**
     * delete record
     * @param id
     * @return
     */
    public static Option<Check> delete(Long id) {
        Option<Check> model = OptionUtil.apply(first(id));
        if(model.isDefined()) {
            model.get().delete();
        }
        return model;
    }


}


