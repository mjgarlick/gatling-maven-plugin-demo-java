package demosite.pages;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Search {

    // CSV contains name and number of results for a couple of searches.
    // as currently no prior knowledge of dynamic created items to search on.
    private static FeederBuilder.FileBased<String> linkFeeder =
            csv("demositedata/books.csv").circular();


    // base page returns 200.
    public static ChainBuilder search =
            exec(
                    http("Search page load")
                            .get("/books/")
                            .check(status().is(200))
            );

    // search by author, expecting more than 1 result. No precise "equals", could use gte
    // and the value from the books.csv, but it's only example.
    public static ChainBuilder performSearch =
            feed(linkFeeder)
                    .exec(
                            http("Search for #{author}")
                                    .get("/books/#{author}")
                                    .check(status().is(200))
                                    .check(jmesPath("length([])").gt("1"))
                    );
}
