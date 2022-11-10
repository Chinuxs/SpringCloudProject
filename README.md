# SpringCloudProject
Microservicios con Spring Boot y Spring Cloud Netflix Eureka

* Spring-Data/JPA
* Rest Repositories : CRUD Automaticos

# Compile as Jars File the common projects and added the dependecies:

*  SpringBoot-servicio-commons
*  SpringBoot-servicio-usuarios-commons


```
<dependency>
  <groupId>com.formaciondbi.springboot.app.usuarios.commons</groupId>
  <artifactId>springboot-servicio-usuarios-commons</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

> run Maven install to build the common projects

 ```
 ..\SpringCloudProject\springboot-servicio-usuarios-commons>mvnw clean install
 ```

# How to start Springboot Service OAuth

1. First start Eureka Server Microservices
2. start Config Server Microservices

# How to setup a API Gateway Server using JJWT

This is the site for get the Maven Dependencies [about Java JSON Web Token] (https://github.com/jwtk/jjwt#install-jdk-maven)



