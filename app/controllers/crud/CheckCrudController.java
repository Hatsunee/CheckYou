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
import views.html.crud.Check.*;
import play.data.*;
import play.i18n.Messages;
import static play.libs.F.*;
import models.crud.DateUtil;
import models.crud.OptionUtil;

// Play 2.1 support
import static play.data.Form.*;

/**
 * CheckCrudController
 */
public class CheckCrudController extends Controller {      
  /**
   * 
   * @return
   */
  @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result index(Integer page) {
      List<Check> model = CheckService.page(page);
      Integer pageMax = CheckService.pageMax(page);
      Form<SearchEngine.Form> form = form(SearchEngine.Form.class);
      return ok(index.render("Check : index", model, page, pageMax, form.fill(new SearchEngine.Form())));
  }

  /**
   * 
   * @param id
   * @return
   */
  @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result view(Long id) {
      Check model = CheckService.first(id);
      if(model != null) {
        return ok(detail.render("Check : view : " + id, model));
      } else {
        flash("error", Messages.get("stylyts.form.request"));
        return redirect(routes.CheckCrudController.index(1));
      }
  }

  /**
   * 
   * @return
   */
  @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result create() {
      
      if(request().method().equals("POST")) {
          Form<Check> bindForm = form(Check.class).bindFromRequest();
          if (bindForm.hasErrors()) {
              flash("error", Messages.get("stylyts.form.error.all"));
              return badRequest(create.render("Title", bindForm));
          } else if(bindForm.value().isDefined()) {
              Check model = CheckService.create(bindForm.get(), bindForm);
              flash("success", model.id + " has been created");
              return redirect(routes.CheckCrudController.index(1));
          } else {
              flash("error", Messages.get("stylyts.form.request"));
              return redirect(routes.CheckCrudController.index(1));
          }
      } else {
          return ok(create.render("Check : create", form(Check.class).fill(new Check())));
      }
  }

  /**
   * 
   * @param id
   * @return
   */
  @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result update(Long id) {
      Form<Check> updateForm = form(Check.class).bindFromRequest();
      if(request().method().equals("POST")) {
          if (updateForm.hasErrors()) {
              flash("error", Messages.get("stylyts.form.error.all"));
              return badRequest(update.render("Title", updateForm, id));
          } else if(updateForm.value().isDefined()) {
              Check model = CheckService.update(updateForm.get(), id, updateForm);
              if(model != null) {
                  flash("success", model.id + " has been updated");
              } else {
                  flash("error", Messages.get("stylyts.form.error.update"));
              }
              return redirect(routes.CheckCrudController.index(1));
          } else {
              flash("error", Messages.get("stylyts.form.request"));
              return redirect(routes.CheckCrudController.index(1));
          }
      } else {
          return ok(update.render("Check : update : " + id, form(Check.class).fill(CheckService.first(id)), id));
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
          Option<Check> model = CheckService.delete(id);
          if(model.isDefined()) {
              flash("success", model.get().id + " has been deleted");
          } else {
              flash("error", id + "cant deleted");
          }

      }
      return redirect(routes.CheckCrudController.index(1));
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

        Option<Integer> pageMax = SearchEngine.getSearchMaxPage(Check.class, column, keyword);
        Option<List<Check>> model = SearchEngine.getSearchResult(Check.class, column, keyword, page, pageMax.getOrElse(1));
        Option<Integer> pageNumber = SearchEngine.toInt(page);
        if(model.isDefined() && pageMax.isDefined() && pageNumber.isDefined()) {
            return ok(search.render("Check : index", model.get(), pageNumber.get(), pageMax.get(), filledForm, column, keyword));
        }
        flash("error", Messages.get("stylyts.form.request"));
        return redirect(routes.CheckCrudController.index(1));
    }

}
