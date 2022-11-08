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
 ..\SpringCloudProject\springboot-servicio-usuarios-commons>mvnw install
 ```
