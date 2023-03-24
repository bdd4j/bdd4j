package org.bdd4j;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

public class BDD4jExtension implements Extension, BeforeEachCallback
{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        
        context.publishReportEntry("before ", context.getDisplayName());
    }


}
