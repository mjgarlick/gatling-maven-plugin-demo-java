package demosite.pages;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

// logical collection of static pages for simplicity. We don't have any.
public class StaticPages {

    public static ChainBuilder homepage =
            exec(
                    http("Load ALL BOOKS page")
                            .get("/books")
                            .check(status().is(200))
                           // .check(css("#_csrf", "content").saveAs("csrfToken"))
            );

    public static ChainBuilder about =
            exec(
                    http("Load search page")
                            .get("/about")
                            .check(status().is(200))
            );
}
