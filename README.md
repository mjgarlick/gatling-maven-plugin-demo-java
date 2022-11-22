Gatling TestFramework Java
====
Clone repo, run from command line or within an ide, run the src/test/java/Engine.java file and follow / select options.
Only "DemoLoginSite" is going to run.
Don't forget to get the maven dependencies.

## Requirements
https://github.com/argodevops/nodejs-restapi-mongo

This will require the noddy nodejs/mongo app to be running in order to work (see DOMAIN system property). 
See that projects included readme to get it running (requires Docker install and internet access)

Assume Maven, Java and git installed (and relevant home paths set)

### Command line running tests with Maven:
The below command will use the gatling maven plugin to run the tests.
mvn gatling:test -Dgatling.simulationClass=demosite.DemoLoginSite
(without passing the simulationClass property, it will potentially claim more than 1 simulation to run and require param line parameter, or pom change to run multiple simulations)

#### To override some System properties, pass in the values - e.g.
mvn gatling:test -Dgatling.simulationClass=demosite.DemoLoginSite -DTYPE_OF_TEST=RAMP_USERS -DDURATION=60 -DUSERS=60 -DRAMP_DURATION=20

### System properties (default values in bracket):
- TYPE_OF_TEST  ("INSTANT_USER") *Options are INSTANT_USER or RAMP_USER*
- DOMAIN    ("http://localhost:3000/api") *This is the api endpoint it is hitting. See nodejs/mongo app requirement*
- RAMP_DURATION  ("15") *number of seconds to ramp up users to USERS over*
- DURATION ("30") *max time for users to action over, user journey end even if still not completed actions*
- USERS ("30") *Amount of users used in simulation*

Duration isn't the duration of the entire Test, no max duration has been set as of yet (i.e end test regardless after X)
"Duration" is relative to the individual users. If user action(s) take longer than Duration, they'll finish.

### Building a JAR with dependencies
The pom.xml is using the maven assembly plugin in order to create a jar with its dependencies. 
Additionally, maven surefire plugin for the test phase of the default build cycle, is set to be skipped.
Therefore mvn clean package will not run the tests.
The resultant fat jar being produced can be run at the command line, passing in parameters as required. Most notably is passing in the Simulation class to be run.
Example:
java -DUSERS=50 -jar target/Gatling-Tests-jar-with-dependencies.jar -s demosite.DemoLoginSite

To pass in further System Properties, extend the -DXXXX=YYY at the start of the command to include the available properties as above.
