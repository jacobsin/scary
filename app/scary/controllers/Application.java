package scary.controllers;

import play.mvc.Controller;
import play.mvc.Result;
import scary.views.html.index;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
