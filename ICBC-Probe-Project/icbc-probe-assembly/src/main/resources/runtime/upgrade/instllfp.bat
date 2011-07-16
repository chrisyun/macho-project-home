set UPGRADE_FILE=.\icbc-probe-1.0.0-SNAPSHOT-bin.zip
set WORK_DIR=.
set TMP_DIR=%WORK_DIR%

set TARGET_DIR=C:\icbc-probe

del /S /Q %TMP_DIR%\probe-tmp
rmdir %TMP_DIR%\probe-tmp
mkdir %TMP_DIR%\probe-tmp
%WORK_DIR%\unzip -o -d %TMP_DIR%\probe-tmp %UPGRADE_FILE%

net stop "ICBC Probe Client"

del /S /Q %TARGET_DIR%\upgrade
xcopy /Y /E %TMP_DIR%\probe-tmp\icbc-probe %TARGET_DIR%

net start "ICBC Probe Client"

REM 设置AT调度,每月1日运行一次
AT 00:00 /every:1 "C:\icbc-probe\upgrade\upgrade.bat"
