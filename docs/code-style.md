# Code Style

This project uses checkstyle to enforce the code style.

The configuration can be found in the root directory in the `checkstyle.xml` file.
It is based on the default google code style configuration.

## IntelliJ configuration

To enable proper formatting in the IntelliJ IDE you can follow these steps:

1. Click on File - Settings
2. Select Editor - Code Style
3. Select Scheme - Project
4. Click on the cogwheel next to the dropdown
5. Click on import scheme - Checkstyle configuration
6. Select the checkstyle.xml file in the root directory

It's also recommended to go to "File - Settings" then "Tools - Actions on Save" and enabling both "Reformat Code" and "
Optimize imports" to make working with the code a little easier.