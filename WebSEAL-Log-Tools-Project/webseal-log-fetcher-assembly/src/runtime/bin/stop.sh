#!/bin/sh
ps -ef | grep java  | grep "com.ibm.tivoli.cars.fetcher.Launcher"| grep -v grep | awk '{printf("kill -9 %s\n",$2)}' | sh
