package demosite.pages;

import io.gatling.javaapi.core.ChainBuilder;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class AddBook {

    private static Random r = new Random();

    public static LocalDate randomDate() {
        int yearVariance = 30 * 365;
        return LocalDate.ofEpochDay(r.nextInt(-yearVariance, yearVariance));
    }

    public static int gameId() {
        return (int) (100000 + (r.nextFloat() * 900000));
    }

    private static Iterator<Map<String, Object>> bookFeeder =
        Stream.generate((Supplier<Map<String, Object>>) () -> {
            String author = RandomStringUtils.randomAlphabetic(5,10);
            String isbn = String.valueOf(100000 + r.nextFloat() * 900000);
            String title = RandomStringUtils.randomAlphabetic(5,10);
            String publishDate = randomDate().toString();
            //String publishDate = "1991-02-01";
            HashMap<String,Object> map = new HashMap<>();
            map.put("id", gameId());
            map.put("author", author);
            map.put("isbn", isbn);
            map.put("title", title);
            map.put("publishDate", publishDate);
            return map;
        }).iterator();

    public static ChainBuilder addBook =
            feed(bookFeeder)
                    .exec(
                            http("Add book #{title}")
                                    .post("/book")
//                                    .formParam("author", "#{author}")
//                                    .formParam("publishedDate", "#{publishedDate}")
//                                    .formParam("title", "#{title}")
//                                    .formParam("isbn", "#{isbn}")
                                    .body(ElFileBody("bodies/BookTemplate.json")).asJson()
                                    .check(status().is(200))
                    );


}
