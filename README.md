# cleanhub-tracker
## About

Service emits cleanhub public api and persists it to db periodically to analyze data.

#### tech stack used
 -spring boot<br />
 -java-17<br />
 -h2 database(jdbc:h2:mem:testdb)<br />
 -open api /swagger

## How to:

#### building service:
Run "mvn package -DskipTests" under project root

#### run automatic tests:
Run "mvn test" under project root

#### run the service locally:
mvn package -DskipTests<br />
mvn spring-boot:run

#### API Documantation
	http://localhost:8080/swagger-ui/index.html
	
#### To Make it ready to Prod 
 -Proper db should be used. Mysql, MongoDb depends on requirements<br />
 -If application will run more than one instance, scheduler should be configured proparly<br />
 -Instead of polling periodically, would be more consistent to get data when it s updated by using webSocket<br />
 -If we have to call rest API, we should configure circuit breaker pattern<br />
  think about fallback scenarios 
  and try to act fast when remote server down to release waiting threads in application. 
 -More logging needed. 
 -Handling exceptions aspect oriented way 
