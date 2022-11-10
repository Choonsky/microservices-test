In this test Spring project I have implemented two microservices - Review service and Product service.
They are being discovered by Eureka server, exchanging messages with Kafka, containerized in Docker container with MongoDB and TestContainers.

What do they do:

First, Review service is making some entries in MongoDB for test products.
Via exposed port 8081 we can access this data by HTTP Requests, while GET requests are allowed for everyone, 
POST and PATCH for members of USER group (tester:password), and DELETE for ADMIN group only (admin:password, Basic HTTP authorization by Spring Security).

Second service is Product service on port 8082 that allows only GET access which collects data from live API provided and aggregating that data with 
Review service data on that product. First, I requested Review service by finding it with Eureks service, but now it is done by Kafka messaging.

There are some integration tests as well, though in production I'd make more tests, especially unit tests.

Also, in Review service I've implemented a custom exception.
