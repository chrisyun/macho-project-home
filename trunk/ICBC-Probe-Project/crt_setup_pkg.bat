cd C:\Users\IBM_ADMIN\workspace\ICBC-Probe-Project
call mvn -Dmaven.test.skip package
del /S /F /Q C:\Users\IBM_ADMIN\workspace\ICBC-Probe-Project\icbc-probe-assembly\target\icbc-probe

unzip C:\Users\IBM_ADMIN\workspace\ICBC-Probe-Project\icbc-probe-assembly\target\icbc-probe-2.0.0-SNAPSHOT-bin.zip -d C:\Users\IBM_ADMIN\workspace\ICBC-Probe-Project\icbc-probe-assembly\target

"C:\Program Files (x86)\NSIS\makensis" C:\Users\IBM_ADMIN\workspace\ICBC-Probe-Project\icbc-probe-assembly\src\main\resources\nsis\setup_v2.nsi
