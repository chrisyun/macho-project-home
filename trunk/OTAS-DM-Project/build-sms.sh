#!/bin/sh
CVSRoot=:pserver:zhao@cvs.otas.cn:/home/master
cvs -d "$CVSRoot" co OTAS-SMS-Common
cd OTAS-SMS-Common/build
ant

cd ../..

cvs -d "$CVSRoot" co OTAS-SMS-Gateway
cd OTAS-SMS-Gateway/build
ant -f build-client.xml
cd ..

