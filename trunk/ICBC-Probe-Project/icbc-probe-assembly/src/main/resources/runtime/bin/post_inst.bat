echo "Stop service ..."
rem call net stop Tomcat6 >> c:\icbc-probe\logs\install.log
REM Waiting 20 seconds
rem ping -n 20 127.0>nul

rem set current=%date:~,10%
rem set hh=%time:~,2%
rem set mm=%time:~3,2%
rem set ss=%time:~6,2%
rem set suffix=%current%.%hh%%mm%%ss%
rem echo %suffix%
rem ren C:\icbc-probe icbc-probe.%current%.%hh%%mm%%ss%

regedit /s c:\icbc-probe\bin\apache.reg >> c:\icbc-probe\logs\install.log
regedit /s c:\icbc-probe\bin\tomcat.reg >> c:\icbc-probe\logs\install.log
