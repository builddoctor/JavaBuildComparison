#!/usr/bin/env bash
ant -version
time ant clean packaged-code
mvn -version
time mvn clean install
gradle -version
time gradle clean build
rake -V
time rake clean package
bundle exec buildr -v
time bundle exec buildr clean package
