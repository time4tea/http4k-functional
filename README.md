
# http4k-functional

A re-implementation of https://github.com/sdeleuze/spring-kotlin-functional but using http4k

I was interested after Sebastien's talk at KotlinConf to see how app start up time compared. I have attempted to
make a faithful translation, any differences are not intentional.

According to the talk, the Spring Boot version starts up in:

`CPU 0.25/RAM 0.5   - JVM 21 seconds, Native 0.106 seconds`

`CPU 2.00/RAM 4.0   - JVM  2 seconds, Native 0.032 seconds`


GraalVM 22.3.1 Java 17 CE

My test:

`CPU 8.00/RAM 64.0  - JVM 1.23 seconds, Native  0.051 seconds (my desktop)`

I've not tested the same configuration on Azure yet...

`CPU 8.00/RAM 64.0  - JVM 0.35 seconds, Native  0.026 seconds (my desktop)`

