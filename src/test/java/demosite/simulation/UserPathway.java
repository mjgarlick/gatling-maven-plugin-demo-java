package demosite.simulation;

import demosite.pages.*;
import io.gatling.javaapi.core.ChainBuilder;

import static demosite.session.Session.initSession;
import static io.gatling.javaapi.core.CoreDsl.*;

import java.time.Duration;

// Pattern of abstraction for various user pathways through application;
// grouping request chains to achieve objectives being tested.
// i.e login, or creating of X, or searching.
public class UserPathway {

    private static final Duration LOW_PAUSE = Duration.ofMillis(1000);
    private static final Duration HIGH_PAUSE = Duration.ofMillis(3000);

    public static ChainBuilder browseSite =
            exec(initSession)
                    .exec(StaticPages.homepage)
                    .pause(LOW_PAUSE)
                    .repeat(4).on(
                            exec(DynamicPages.visitPage)
                                    .pause(LOW_PAUSE)
                    );

    public static ChainBuilder loginOnSite =
            exec(initSession)
//                    .exec(StaticPages.homepage)
//                    .pause(HIGH_PAUSE)
                    .exec(Login.login)
//                    .pause(LOW_PAUSE)
//                    .repeat(4).on(
//                            exec(DynamicPages.visitPage)
//                                    .pause(LOW_PAUSE)
//                    )
                    .pause(LOW_PAUSE)
                    .exec(Login.logout);

    public static ChainBuilder searchComputers =
            exec(initSession)
                    .exec(Search.search)
                    .pause(LOW_PAUSE)
                    .repeat(2).on(
                            exec(Search.performSearch)
                                    .pause(HIGH_PAUSE)
                    ).pause(LOW_PAUSE)
                    .repeat(2).on(
                            exec(AddBook.addBook)
                                    .pause(HIGH_PAUSE)
                    );
                  //  .exec(AddComputer.addComputer);
}
