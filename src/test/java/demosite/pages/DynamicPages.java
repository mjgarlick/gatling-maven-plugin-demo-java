package demosite.pages;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

// Name irrelevant, more example of driving a load of get pages from a csv file detailing them
// for code reuse.
public class DynamicPages {

    private static FeederBuilder.FileBased<String> linkFeeder =
            csv("demositedata/menulink.csv").circular();

    public static ChainBuilder visitPage =
            feed(linkFeeder)
                    .exec(http("Visit page #{menuitem}")
                            .get("#{link}")
                            .check(status().is(200)));
                           // .check(css("#CategoryName").isEL("#{pageTitle}"))
                           // .check(responseTimeInMillis().lte(500)));
}
