# Architecture Decision Record Template

The adr.vtl file contains a velocity template for the creation of architecture decision record files that is compatible
with the IntelliJ IDEA IDE.

## Setup

To set up the template in your IDE follow these steps:

1. Go to File - Settings
2. Select Editor - File and Code Templates
3. Click on the plus sign to create a new template
4. Enter the name e.g. "Architecture Decision Record"
5. Enter the extension "md"
6. Copy and paste the contents of `/templates/IDEA/adr/template.md` into the file content

## Creating a new Architecture Decision Record

You can now right-click on the directory `docs/adrs` and select the `Architecture Decision Record` template.

### Parameters

The parameter `ArchitectureDecisionID` should contain a running number with four digits.
If the next `ArchitectureDecisionID` is less than 1000, then the value should be filled up with leading zeros.
E.g. `42` would become `0042`

The parameter `ArchitectureDecisionName` should contain the human-readable name for the decision.
This should be unique and can be used by other developers to reference a specific decision.
E.g. `Introduce spring dependency injection`.

The parameter `File name` should start with the `ArchitectureDecisionID`, followed by a dash and a file name compatible
representation of the `ArchitectureDecisionName`.
E.g. when the `ArchitectureDecisionID` is `0042` and the `ArchitectureDecisionName`
is `Introduce spring dependency injection`, then the `File name` should
be `0042-introduce-spring-dependency-injection.md`.

## Caveats

As there currently is no convenient way to share these templates as part of the project, each developer needs to set up
these templates on their own.