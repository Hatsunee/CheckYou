package models.crud;

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
 * AdminService
 * 
 * @author harakazuhiro
 */
public class AdminService {

    /**
     * find all by list
     * @return
     */
    public static List<Admin> page(Integer pageSource) {
        Integer page = (pageSource - 1 < 0) ? 0 : pageSource - 1;
        Model.Finder<Long, Admin> find = new Model.Finder<Long, Admin>(Long.class, Admin.class);
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
        Model.Finder<Long, Admin> find = new Model.Finder<Long, Admin>(Long.class, Admin.class);
        // return find.all();
        return find.order().asc("created")
                .findPagingList(models.crud.PagingSetting.LIMIT).getPage(page)
                .getTotalPageCount();
    }


    /**
     * find all by list
     * @return
     */
    public static List<Admin> all() {
        Model.Finder<Long, Admin> find = new Model.Finder<Long, Admin>(Long.class, Admin.class);
        // return find.all();
        return find.
            findList();
    }

    /**
     * find one
     * @param id
     * @return
     */
    public static Admin first(Long id) {
        Model.Finder<Long, Admin> find = new Model.Finder<Long, Admin>(Long.class, Admin.class);
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
    public static Admin create(Admin model, play.data.Form<Admin> bindForm) {
        model.setCreated(DateUtil.getCurrentDate());
        try {
            model.password = sha512(model.password);
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
    public static Admin update(Admin model, Long id, play.data.Form<Admin> bindForm) {
        Admin entry;
        if(id != null) {
            entry = first(id);
        } else {
            entry = null;
        }
        if(entry != null) {
            model.setId(id);
            //
            try {
                model.password = sha512(model.password);
            } catch (java.security.NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
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
    public static Option<Admin> delete(Long id) {
        Option<Admin> model = OptionUtil.apply(first(id));
        if(model.isDefined()) {
            model.get().delete();
        }
        return model;
    }


    /**
     * hash util for password
     * @param message
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String sha512(String message) throws  java.security.NoSuchAlgorithmException {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-512");
        String out = "";

        md.update(message.getBytes());
        byte[] mb = md.digest();
        for (int i = 0; i < mb.length; i++) {
            byte temp = mb[i];
            String s = Integer.toHexString(new Byte(temp));
            while (s.length() < 2) {
                s = "0" + s;
            }
            s = s.substring(s.length() - 2);
            out += s;
        }
        return out;
    }

    /**
     * Authenticate a User.
     */
    public static Admin authenticate(String username, String password) throws java.security.NoSuchAlgorithmException{
        Model.Finder<Long,Admin> find = new Model.Finder<Long, Admin>(
                Long.class, Admin.class
        );
        String hashedPassword = "";
        if(password != null) {
            hashedPassword = sha512(password);
        }
        return find.where()
                .eq("username", username)
                .eq("password", hashedPassword)
                .findUnique();
    }



}


