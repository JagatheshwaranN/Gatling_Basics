package api;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class APIDemoTest extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://reqres.in/api");

    private final ScenarioBuilder getUser = scenario("Get API Demo")
            .exec(
                    http("Get User Detail")
                            .get("/users/2")
                            .check(status().is(200))
                            .check(jsonPath("$.data.first_name").is("Janet")
                            )
            );

    private final ScenarioBuilder addUser = scenario("Post API Demo")
            .exec(
                    http("Add User Detail")
                            .post("/users")
                            .asJson()
                            .body(StringBody("{\n" +
                                    "    \"name\": \"John\",\n" +
                                    "    \"job\": \"leader\"\n" +
                                    "}"))
                            .check(status().is(201)
                            )
            );

    private final ScenarioBuilder addUserFromFile = scenario("Post API Demo")
            .exec(
                    http("Add User Detail")
                            .post("/users")
                            .body(RawFileBody("data/add_user.json"))
                            .check(status().is(201)
                            )
            );

    private final ScenarioBuilder updateUser = scenario("Put API Demo")
            .exec(
                    http("Update User Detail")
                            .put("/users/2")
                            .body(RawFileBody("data/update_user.json"))
                            .check(status().is(200)
                            )
            );

    private final ScenarioBuilder deleteUser = scenario("Delete API Demo")
            .exec(
                    http("Delete User Detail")
                            .delete("/users/2")
                            .check(status().is(204)
                            )
            );


    {

        setUp(addUserFromFile.injectOpen(atOnceUsers(5)),
                getUser.injectOpen(atOnceUsers(10)),
                updateUser.injectOpen(atOnceUsers(5)),
                deleteUser.injectOpen(atOnceUsers(5))
        ).protocols(httpProtocol);

    }

}
