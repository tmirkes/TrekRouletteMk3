# Trek Roulette

---
This is the documentation for the Trek Roulette web application for Enterprise Java, Spring 2023.

### Problem Statement

Star Trek debuted in 1966, and has continued to release new TV episodes across multiple series in the decades since.  With so much content out there to view, many fans will binge-watch an entire series, beginning with the pilot episode and proceeding through in order until the series finale.  Other fans, however, have a more chaotic approach to the oeuvre of Star Trek episodes.

While it is possible to pick "random" episodes from the series one has access to through various streaming services and physical media, it can be overwhelming to make that choice consciously.  Perhaps time is limited, so wasting precious minutes choosing an episode can be the difference between fitting in an episode and needing to do something else.  Perhaps work stress leaves one with a fried brain and little desire to make any decisions, making the prospect of choosing from among dozens or hundreds of episodes a daunting proposition.

Trek Roulette intends to make that random selection process less stressful, requiring minimal human input to select an episode and indicate what media will be used to view the show.
### Project Technologies/Techniques

* **Security/Authentication**
  * Amazon Cognito
    * Managed on User Pools
* **Database**
  * Amazon RDS
  * MySQL 8.0.22
* **ORM Framework**
  * Hibernate 5.4.4
* **Dependency Management**
  * Maven
* **Web Services consumed using Java**
  * [Star Trek API (STAPI)](https://mvnrepository.com/artifact/com.cezarykluczynski.stapi/stapi-client)
  * Accessed via STAPI Java client
* **CSS**
  * Built on The LCARS template, with modifications
  * [The LCARS](https://www.thelcars.com/)
* **Data Validation**
  * Amazon Congito for user authentication data
* **Logging**
  * Log4J2
* **Hosting**
  * Amazon Web Services
* **Independent Research Topic/s**
  * Mocking in unit tests with Mockito Framework
    * [Mockito Framework Site](https://site.mockito.org/)
* **Unit Testing**
  * JUnit/Jupiter
  * Squaretest
    * Test skeleton builder
* **IDE**
  * IntelliJ IDEA

### Design Documents

[User Stories](designDocs/userStories.md)


[Project Plan](designDocs/projectPlan.md)


[Application Flow](designDocs/appFlow.md)


[Screen Design](screenDesign/screenDesign.md)

### TimeLog

[Time Log](designDocs/timeLog.md) 