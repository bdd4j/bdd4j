package org.bdd4j.example.camel;

import org.bdd4j.BDD4jRunner;
import org.bdd4j.Feature;
import org.bdd4j.Scenario;
import org.bdd4j.UserStory;

@Feature("Route incoming message based on content.")
@UserStory("""
        As a user with a name
        I want received messages, addressed to me, routed to my personal inbox
        I want received messages, addressed to unknown persons, routed to the dead letter box
        """)
class CamelDropBoxRouteTest
{

    @Scenario("Route message to Bob")
    public void routeMessagesToBob(CamelRoutingSteps steps)
    {
        BDD4jRunner.scenario(steps,
                             steps.givenACamelContextWithARouteDefinition(),
                             steps.whenAMessageAddressingBobIsReceivedInTheDropBox(),
                             steps.thenTheMessageIsRoutedToBobsInbox());
    }

    @Scenario("Route message of unknown recipient to dead letter box.")
    public void routeMessagesToDeadLetterBox(CamelRoutingSteps steps)
    {
        BDD4jRunner.scenario(steps,
                             steps.givenACamelContextWithARouteDefinition(),
                             steps.whenAMessageAddressingUnknownIsReceivedInTheDropBox(),
                             steps.thenTheMessageIsRoutedToDeadLetterBox());
    }

    @Scenario("Route message to Mary.")
    public void routeMessagesToMary(CamelRoutingSteps steps)
    {
        BDD4jRunner.scenario(steps,
                             steps.givenACamelContextWithARouteDefinition(),
                             steps.whenAMessageAddressingMaryIsReceivedInTheDropBox(),
                             steps.thenTheMessageIsRoutedToMarysInbox());
    }
}