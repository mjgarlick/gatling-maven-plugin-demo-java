package demosite.session;


import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

// May be unnecessary, but example abstraction of setup/store of variables per user session.
// can just use the session get/set direct in code.
public class Session {
    public static ChainBuilder initSession =
            exec(flushCookieJar())
                    .exec(session -> session.set("loggedIn", false));
}
