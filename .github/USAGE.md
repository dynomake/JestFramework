## | `JestFramework - Usage:`
*You can found full example-code in:* [LINK](https://github.com/suuft/JestFramework/tree/master/src/test/java/net/jest/test)

First, lets create an application class that ll run the JVM. E.i. - Main:
```java
public class Main {

    public static void main(String[] args) {
    }
}
```
Okay, this class needs to be marked with the @JestBootstrap annotation, in which ll say the ip and port to run a Web server on them:
```java
@JestBootstrap(port = 8080, hostName = "localhost")
public class Main {

    public static void main(String[] args) {
    }
}
```
Next, we will create a controller that ll be marked with the @Controller annotation, for example, ll make a metric controller that ll add and subtract, and return new number on request.
```java
@Controller(path = "/api/metric")
public class MetricController {

    private int count;

    @Method(name = "/add", type = "POST") // address: localhost:8080/api/metric/add?count=NUMBER
    public Response add(@Parameter("count") int toAdd) {
        count+=toAdd;
        return ResponseUtil.createResponse(ResponseUtil.OK, "new count = " + count);
    }

    @Method(name = "/subtract", type = "POST") // address: localhost:8080/api/metric/subtract?count=NUMBER
    public Response subtract(@Parameter("count") int toAdd) {
        count-=toAdd;
        return ResponseUtil.createResponse(ResponseUtil.OK, "new count = " + count);
    }
}
```
It would be necessary to register it in Main class:
```java
@JestBootstrap(port = 8080, hostName = "localhost")
public class Main {

    public static void main(String[] args) {
        JestServer jest = JestInitializer.bootServer(Main.class);
        jest.registerRestController(MetricController.class);
    }
}
```
Okay, just check it out.