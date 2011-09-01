#!/usr/bin/env bash
time ant clean packaged-code
time mvn clean install
time gradle clean build
