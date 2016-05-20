package controllers.crud;

import java.util.List;

// import libs.controller.action.AdminAction;
// import libs.controller.action.BeforeAction;

import controllers.crud.routes;

import play.*;
import play.mvc.*;
import models.*;
import models.crud.*;
import models.entity.*;
import views.html.crud.Admin.*;
import play.data.*;
import play.i18n.Messages;
import static play.libs.F.*;
import models.crud.DateUtil;
import models.crud.OptionUtil;

// Play 2.1 support
import static play.data.Form.*;

/**
 * AdminCrudController
 */
public class AdminCrudController extends Controller {      
  /**
   * 
   * @return
   */
  @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result index(Integer page) {
      List<Admin> model = AdminService.page(page);
      Integer pageMax = AdminService.pageMax(page);
      Form<SearchEngine.Form> form = form(SearchEngine.Form.class);
      return ok(index.render("Admin : index", model, page, pageMax, form.fill(new SearchEngine.Form())));
  }

  /**
   * 
   * @param id
   * @return
   */
  @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result view(Long id) {
      Admin model = AdminService.first(id);
      if(model != null) {
        return ok(detail.render("Admin : view : " + id, model));
      } else {
        flash("error", Messages.get("stylyts.form.request"));
        return redirect(routes.AdminCrudController.index(1));
      }
  }

  /**
   * 
   * @return
   */
  // @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result create() {
      
      if(request().method().equals("POST")) {
          Form<Admin> bindForm = form(Admin.class).bindFromRequest();
          if (bindForm.hasErrors()) {
              flash("error", Messages.get("stylyts.form.error.all"));
              return badRequest(create.render("Title", bindForm));
          } else if(bindForm.value().isDefined()) {
              Admin model = AdminService.create(bindForm.get(), bindForm);
              flash("success", model.id + " has been created");
              return redirect(routes.AdminCrudController.index(1));
          } else {
              flash("error", Messages.get("stylyts.form.request"));
              return redirect(routes.AdminCrudController.index(1));
          }
      } else {
          return ok(create.render("Admin : create", form(Admin.class).fill(new Admin())));
      }
  }

  /**
   * 
   * @param id
   * @return
   */
  @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result update(Long id) {
      Form<Admin> updateForm = form(Admin.class).bindFromRequest();
      if(request().method().equals("POST")) {
          if (updateForm.hasErrors()) {
              flash("error", Messages.get("stylyts.form.error.all"));
              return badRequest(update.render("Title", updateForm, id));
          } else if(updateForm.value().isDefined()) {
              Admin model = AdminService.update(updateForm.get(), id, updateForm);
              if(model != null) {
                  flash("success", model.id + " has been updated");
              } else {
                  flash("error", Messages.get("stylyts.form.error.update"));
              }
              return redirect(routes.AdminCrudController.index(1));
          } else {
              flash("error", Messages.get("stylyts.form.request"));
              return redirect(routes.AdminCrudController.index(1));
          }
      } else {
          return ok(update.render("Admin : update : " + id, form(Admin.class).fill(AdminService.first(id)), id));
      }
  }

  /**
   * 
   * @param id
   * @return
   */
  @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result delete(Long id) {
      if (id != null) {
          Option<Admin> model = AdminService.delete(id);
          if(model.isDefined()) {
              flash("success", model.get().id + " has been deleted");
          } else {
              flash("error", id + "cant deleted");
          }

      }
      return redirect(routes.AdminCrudController.index(1));
  }

    /**
     *
     * @return
     */
    @play.mvc.Security.Authenticated(models.crud.Secured.class)
    public static Result search() {
        Form<SearchEngine.Form> form = form(SearchEngine.Form.class);
        Form<SearchEngine.Form> filledForm = form.bindFromRequest();
        String column = filledForm.field("column").valueOr("");
        String keyword = filledForm.field("keyword").valueOr("");
        String page = filledForm.field("page").valueOr("");

        Option<Integer> pageMax = SearchEngine.getSearchMaxPage(Admin.class, column, keyword);
        Option<List<Admin>> model = SearchEngine.getSearchResult(Admin.class, column, keyword, page, pageMax.getOrElse(1));
        Option<Integer> pageNumber = SearchEngine.toInt(page);
        if(model.isDefined() && pageMax.isDefined() && pageNumber.isDefined()) {
            return ok(search.render("Admin : index", model.get(), pageNumber.get(), pageMax.get(), filledForm, column, keyword));
        }
        flash("error", Messages.get("stylyts.form.request"));
        return redirect(routes.AdminCrudController.index(1));
    }


    /**
     * Login page.
     */
    public static Result login() {
        return ok(views.html.crud.login_crud.render(form(Login.class)));
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(views.html.crud.login_crud.render(loginForm));
        } else {
            session("username", loginForm.get().username);
            String returnUrl = ctx().session().get("returnUrl");
            if(returnUrl == null || returnUrl.equals("") || returnUrl.equals(routes.AdminCrudController.login().absoluteURL(request()))) {
                returnUrl = routes.AdminCrudController.index(1).url();
            }
            return redirect(returnUrl);
        }
    }

    /**
     * Logout and clean the session.
     */
    @play.mvc.Security.Authenticated(models.crud.Secured.class)
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.AdminCrudController.login());
    }

}





