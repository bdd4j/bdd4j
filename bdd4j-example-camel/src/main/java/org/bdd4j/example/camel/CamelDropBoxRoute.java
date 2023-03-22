package org.bdd4j.example.camel;

import org.apache.camel.builder.RouteBuilder;

public class CamelDropBoxRoute extends RouteBuilder
{
    @Override
    public void configure()
    {
        from("direct:dropBox")
                .choice()
                .when(exchange -> exchange.getMessage()
                                          .getBody(String.class)
                                          .startsWith("Hello Bob"))
                .to("mock:bobsInbox")
                .when(exchange -> exchange.getMessage()
                                          .getBody(String.class)
                                          .startsWith("Hello Mary"))
                .to("mock:marysInbox")
                .otherwise()
                .to("mock:deadLetterBox");
    }
}
