#!/bin/bash

./mvnw clean verify install sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=mvn -Dsonar.password=mvn

cd com.erichsteiger.eopt.bo
../mvnw sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=mvn -Dsonar.password=mvn
cd ../com.erichsteiger.eopt.dao
../mvnw sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=mvn -Dsonar.password=mvn
cd ../com.erichsteiger.eopt.app
../mvnw sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=mvn -Dsonar.password=mvn
