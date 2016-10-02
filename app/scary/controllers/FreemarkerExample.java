package scary.controllers;

import play.mvc.Controller;
import play.mvc.Result;

import static scary.views.Freemarker.arg;
import static scary.views.Freemarker.view;

public class FreemarkerExample extends Controller {

    public Result index() {
        return ok(
            view("index.ftl",
                    arg("user", "user"),
                    arg("product", "product")
            )
        );
    }
}
