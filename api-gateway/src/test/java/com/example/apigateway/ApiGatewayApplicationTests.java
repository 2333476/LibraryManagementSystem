server:
port: 8080

spring:
application:
name: api-gateway

cloud:
gateway:
routes:
		- id: book-service
uri: http://book-service:8083
predicates:
		- Path=/books/**

 - id: customer-service
 uri: http://customers-service:8081
 predicates:
 - Path=/customers/**

 - id: employee-service
 uri: http://employee-service:8082
 predicates:
 - Path=/employees/**

 app:
 book-service:
 host: book-service
 port: 8083
 customers-service:
 host: customers-service
 port: 8081
 employee-service:
 host: employee-service
 port: 8082

