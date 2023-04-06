package org.bdd4j.example.camel;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelObject implements AutoCloseable
{
    private final DefaultCamelContext context;
    private MockEndpoint bobsInbox;
    private MockEndpoint deadLetterBox;
    private MockEndpoint marysInbox;
    private ProducerTemplate producerTemplate;

    public CamelObject(DefaultCamelContext context)
    {
        this.context = context;
    }

    @Override
    public void close() throws Exception
    {
        context.close();
    }

    public void initialiseCamelContext()
    {
        try
        {
            this.context.addRoutes(new CamelDropBoxRoute());
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        bobsInbox = context.getEndpoint("mock:bobsInbox", MockEndpoint.class);
        marysInbox = context.getEndpoint("mock:marysInbox", MockEndpoint.class);
        deadLetterBox = context.getEndpoint("mock:deadLetterBox", MockEndpoint.class);
        producerTemplate = context.createProducerTemplate();
        context.start();
    }

    public void sendMessageAddressingBobToDropBox()
    {
        bobsInbox.expectedBodiesReceived("Hello Bob");
        sendToDropBox("Hello Bob");
    }

    public void sendMessageAddressingMaryToDropBox()
    {
        marysInbox.expectedBodiesReceived("Hello Mary");
        sendToDropBox("Hello Mary");
    }

    public void sendMessageAddressingUnknownToDropBox()
    {
        deadLetterBox.expectedBodiesReceived("Hello ???");
        sendToDropBox("Hello ???");
    }

    private void sendToDropBox(String body)
    {
        var endpoint = context.getEndpoint("direct:dropBox");
        producerTemplate.sendBody(endpoint, body);
    }

    public void verifyMessageInBobsInbox()
    {
        try
        {
            bobsInbox.assertIsSatisfied();
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void verifyMessageInDeadLetterBox()
    {
        try
        {
            deadLetterBox.assertIsSatisfied();
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void verifyMessageInMarysInbox()
    {
        try
        {
            marysInbox.assertIsSatisfied();
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
