package org.bdd4j.example.camel;

import org.apache.camel.impl.DefaultCamelContext;
import org.bdd4j.*;

import static org.bdd4j.StepDSL.*;

public class CamelRoutingSteps implements BDD4jSteps<CamelObject>
{
    public Given<CamelObject> givenACamelContextWithARouteDefinition()
    {
        return given("given a camel context with a route definition").step(camelObject ->
                                                                           {
                                                                               camelObject.initialiseCamelContext();
                                                                               return TestState.state(camelObject);
                                                                           });
    }

    @Override
    public TestState<CamelObject> init()
    {
        return TestState.state(new CamelObject(new DefaultCamelContext()));
    }

    public Then<CamelObject> thenTheMessageIsRoutedToBobsInbox()
    {
        return then("then the message is routed to Bobs inbox").step(state -> {
            state.state()
                 .verifyMessageInBobsInbox();
            return state;
        });
    }

    public Then<CamelObject> thenTheMessageIsRoutedToDeadLetterBox()
    {
        return then("then the message is routed to dead letter box").step(state -> {
            state.state()
                 .verifyMessageInDeadLetterBox();
            return state;
        });
    }

    public Then<CamelObject> thenTheMessageIsRoutedToMarysInbox()
    {
        return then("then the message is routed to Marys inbox").step(state -> {
            state.state()
                 .verifyMessageInMarysInbox();
            return state;
        });
    }


    public When<CamelObject> whenAMessageAddressingBobIsReceivedInTheDropBox()
    {
        return when("when a message addressing Bob is received in the DropBox").step(
                camelObject -> {
                    camelObject.sendMessageAddressingBobToDropBox();
                    return TestState.state(camelObject);
                });
    }

    public When<CamelObject> whenAMessageAddressingMaryIsReceivedInTheDropBox()
    {
        return when("when a message addressing Mary is received in the DropBox").step(
                camelObject -> {
                    camelObject.sendMessageAddressingMaryToDropBox();
                    return TestState.state(camelObject);
                });
    }

    public When<CamelObject> whenAMessageAddressingUnknownIsReceivedInTheDropBox()
    {
        return when("when a message addressing Unknown is received in the DropBox").step(
                camelObject -> {
                    camelObject.sendMessageAddressingUnknownToDropBox();
                    return TestState.state(camelObject);
                });
    }
}
