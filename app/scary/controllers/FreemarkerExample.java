package scary.controllers;

import play.mvc.Controller;
import play.mvc.Result;

import static scary.views.freemarker.Arg.arg;
import static scary.views.freemarker.Freemarker.view;

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
