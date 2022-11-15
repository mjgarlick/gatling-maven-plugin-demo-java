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
        return (int) (1000000 + (r.nextFloat() * 9000000));
    }

    // custom feeder that generates randomised data for use instead of file driven.
    // useful for large testing volumes but requires clearing down (drop db).
    private static Iterator<Map<String, Object>> bookFeeder =
        Stream.generate((Supplier<Map<String, Object>>) () -> {
            String author = RandomStringUtils.randomAlphabetic(6,10);
            String isbn = String.valueOf(1000000 + r.nextFloat() * 9000000);
            String title = RandomStringUtils.randomAlphabetic(6,10);
            String publishDate = randomDate().toString();
            HashMap<String,Object> map = new HashMap<>();
            map.put("id", gameId());
            map.put("author", author);
            map.put("isbn", isbn);
            map.put("title", title);
            map.put("publishDate", publishDate);
            return map;
        }).iterator();

    // driven by generated data and a template instead of from csv/json.
    public static ChainBuilder addBook =
            feed(bookFeeder)
                    .exec(
                            http("Add book")
                                    .post("/book")
                                    .body(ElFileBody("bodies/BookTemplate.json")).asJson()
                                    .check(status().is(200))
                    );


}
