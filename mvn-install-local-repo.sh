#!/bin/bash

mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file  -Dfile=target/sort-experiments.jar \
                                                                              -DgroupId=pvto.sort-experiments \
                                                                              -DartifactId=sort-experiments \
                                                                              -Dversion=0.1 \
                                                                              -Dpackaging=jar
