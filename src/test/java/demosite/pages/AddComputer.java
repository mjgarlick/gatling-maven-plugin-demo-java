package demosite.pages;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AddComputer {
    private static FeederBuilder.FileBased<String> addComputerFeeder =
            csv("demositedata/addbook.csv").circular();
    public static ChainBuilder addComputer =
            feed(addComputerFeeder)
                    .exec(
                            http("Add computer #{name}")
                                    .post("https://computer-database.gatling.io/computers/")
                                    .formParam("name", "#{name}")
                                    .formParam("introduced", "#{introduced}")
                                    .formParam("discontinued", "#{discontinued}")
                                    .formParam("company", "#{company}")
                                    .check(status().is(200))
                                    .check(css(".alert-message").exists())
            );
}
