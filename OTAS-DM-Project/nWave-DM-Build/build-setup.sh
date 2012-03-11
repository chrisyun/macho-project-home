#!/bin/sh
CVSRoot=:pserver:zhao@cvs.otas.cn:/home/master
cvs -d "$CVSRoot" co OTAS-Setup-Common
cd OTAS-Setup-Common
ant release

cd ..

