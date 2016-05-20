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
import views.html.crud.*;
import play.data.*;
import play.i18n.Messages;
import static play.libs.F.*;
import models.crud.DateUtil;
import models.crud.OptionUtil;

/**
 * TopCrudController
 */
public class TopCrudController extends Controller {
  /**
   * 
   * @return
   */
  @play.mvc.Security.Authenticated(models.crud.Secured.class)
  public static Result index() {
      return ok(top_crud.render("Top page"));
  }
}
