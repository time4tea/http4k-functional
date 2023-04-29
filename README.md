
# http4k-functional

A re-implementation of https://github.com/sdeleuze/spring-kotlin-functional but using http4k

I was interested after Sebastien's talk at KotlinConf to see how app start up time compared. I have attempted to
make a faithful translation, any differences are not intentional.

The http4k version is not optimised - I tried to just make the simplest version that would work.

The results are summarised in the Observable Notebook at: https://observablehq.com/d/53eb5df0da9b77fa

According to the talk, the Spring Boot version starts up in:

`Azure Container CPU 0.25/RAM 0.5   - JVM 21 seconds, Native 0.106 seconds (Seb)`

`Azure Container CPU 2.00/RAM 4.0   - JVM  2 seconds, Native 0.032 seconds (Seb)`

for comparison on my 2016 desktop:

`Desktop CPU 8.00/RAM 64.0  - JVM 1.23 seconds, Native  0.051 seconds (James)`

My test for http4k-functional:

`Azure Container CPU 0.25/RAM 0.5   - JVM 4.29 seconds, Native  0.011 seconds (James)`

`Azure Container CPU 2.00/RAM 4.0   - JVM 0.52 seconds, Native 0.008 seconds (James)`

`Desktop         CPU 8.00/RAM 64.0  - JVM 0.35 seconds, Native  0.026 seconds (James)`



## Images

To try the images:

https://hub.docker.com/repository/docker/time4tea/http4k-functional - Native

https://hub.docker.com/repository/docker/time4tea/http4k-functional-jib - JVM Version using Jib



