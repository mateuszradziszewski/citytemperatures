# Getting Started
This is City Temperatures application created to satisfy recruitment task from Kyotu Technology.

In order to build the application execute:
``mvn clean install``

In order to see test coverage report see the [report file](./target/site/jacoco/index.html).

In order to run the application execute:
``mvn spring-boot:run``

# Things to notice
Below you can find list of features of the project setup that are worth to highlight:
1. Hexagonal architecture - application is implemented in hexagonal architecture. Use case does not justify the architecture choice but the point was to showcase it.
2. JMolecules - there are unit tests that verify direction of dependencies between classes in the project in order to protect application architecture from degrading.
3. OWASP dependency check - checking for vulnerabilities in dependencies is part of build process
4. Code coverage report - it is generated as part of the build.

# Improvement ideas:
1. Caching - if underlying file does not change often then a possible improvement would be storing calculated average yearly temperatures in memory. Cache could be invalidated on detected file change. Tracking file change could be implemented using WatchService.
2. Tailing the file - if the file is changed in a way that new measurements are appended at the end of file then a possible improvement would be to only read new portion of data when the file changes and using this to update the averages in memory.
3. Converting measurement local date times to UTC instants - currently measurement times do not have zone offset. This suggest that they are local to where the measurement was taken. An improvement would be to convert them to UTC instants based on time zone of the city before aggregating the to averages.
4. Validating measurements read from the file - current implementation assumes the data is always valid
5. Code coverage - only some sample unit tests and integration tests were added as examples.