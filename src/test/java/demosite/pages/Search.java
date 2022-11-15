package demosite.pages;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Search {

    private static FeederBuilder.FileBased<String> linkFeeder =
            csv("demositedata/books.csv").circular();



    public static ChainBuilder search =
            exec(
                    http("Search page load")
                            .get("/books/")
                            .check(status().is(200))
            );

    public static ChainBuilder performSearch =
            feed(linkFeeder)
                    .exec(
                            http("Search for #{author}")
                                    .get("/books/#{author}")
                                    .check(status().is(200))
                                    .check(jmesPath("length([])").gt("1"))
                    );
}
