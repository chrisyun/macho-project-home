#!/bin/sh
DM_BUILD_HOME=/home/zhao/assemble/OTAS-DM

target=$1
if [ "$target" = "" ]; then
   target="package-all"
fi
echo $target

rm -rf $DM_BUILD_HOME/nWave-DM-Build/build* $DM_BUILD_HOME/nWave-DM-Common $DM_BUILD_HOME/nWave-DM-Framework $DM_BUILD_HOME/nWave-DM-Server $DM_BUILD_HOME/nWave-DM-Web $DM_BUILD_HOME/nWave-DM-Library $DM_BUILD_HOME/OTAS-SMS-Common $DM_BUILD_HOME/OTAS-SMS-Gateway $DM_BUILD_HOME/OTAS-Setup-Common $DM_BUILD_HOME/OTAS-DM-ReportV2 $DM_BUILD_HOME/OTAS-DM-MyPortal $DM_BUILD_HOME/OTAS-DM-Portal $DM_BUILD_HOME/OTAS-DM-Tools

cd $DM_BUILD_HOME
cvs -d ":pserver:zhao@cvs.otas.cn:/home/master" co nWave-DM-Build

cp $DM_BUILD_HOME/otas.build.properties $DM_BUILD_HOME/nWave-DM-Build/build.local.properties

cd $DM_BUILD_HOME/nWave-DM-Build
ant cvs
ant $target
