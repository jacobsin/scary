package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import static views.Freemarker._;
import static views.Freemarker.view;

public class FreemarkerExample extends Controller {

    public static Result index() {
        return ok(
            view("index.ftl",
                    _("user", "user moo"),
                    _("product", "product moo")
            )
        );
    }
}
