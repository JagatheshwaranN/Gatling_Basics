package demo;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class ComputerDatabaseSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://computer-database.gatling.io")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9")
    .contentTypeHeader("application/x-www-form-urlencoded")
    .originHeader("https://computer-database.gatling.io")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
  
//  private Map<CharSequence, String> headers_0 = Map.ofEntries(
//    Map.entry("priority", "u=0, i"),
//    Map.entry("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126"),
//    Map.entry("sec-ch-ua-mobile", "?0"),
//    Map.entry("sec-ch-ua-platform", "Windows"),
//    Map.entry("sec-fetch-dest", "document"),
//    Map.entry("sec-fetch-mode", "navigate"),
//    Map.entry("sec-fetch-site", "same-origin"),
//    Map.entry("sec-fetch-user", "?1")
//  );


  private ScenarioBuilder scn = scenario("ComputerDatabaseSimulation")
    .exec(
      http("Add Computer")
        .post("/computers")
        //.headers(headers_0)
        .formParam("name", "Dell Inspiron V")
        .formParam("introduced", "2020-10-24")
        .formParam("discontinued", "2023-10-20")
        .formParam("company", "4")
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
