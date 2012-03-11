cd D:\Zhao\MyWorkspace\OTAS-SMS-Common\build
call ant
cd D:\Zhao\MyWorkspace\OTAS-SMS-Gateway\build
call ant -f build-client.xml
cd D:\Zhao\MyWorkspace\nWave-DM-Build
call ant package-all
