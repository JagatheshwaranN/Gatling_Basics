package concepts;

import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class FeedersTest extends Simulation {

    private final FeederBuilder.Batchable<String> csvFeeder = csv("data/user.csv").circular();

    private final ScenarioBuilder feeder = scenario("Feeders Demo")
            .feed(csvFeeder)
            .exec(session ->
                    {
                        System.out.println("Name : " + session.get("name"));
                        System.out.println("Designation : " + session.get("designation"));
                        return session;
                    }
            );

    private final ScenarioBuilder feederWithRepeat = scenario("Feeders Demo")
            .repeat(3).on(
                    feed(csvFeeder)
                            .exec(session ->
                                    {
                                        System.out.println("Name : " + session.get("name"));
                                        System.out.println("Designation : " + session.get("designation"));
                                        return session;
                                    }
                            ));

    {

        setUp(feeder.injectOpen(atOnceUsers(1)),
                feederWithRepeat.injectOpen(atOnceUsers(1)));
    }

}

