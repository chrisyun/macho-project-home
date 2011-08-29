set REPORT_HOME=E:\IBM\WebSphere\AppServer\profiles\IMS\installedApps\imsCell01\webseal-report_war.ear\webseal-report.war
set REPORT_OUTPUT=E:\Temp\report

REM -----------------------------------------------------------------
set REPORT_FORMAT=pdf
set BIRT_HOME=%REPORT_HOME%\WEB-INF\platform

set YEAR=%date:~,4%
set MONTH=%date:~5,2%
set DAY_OF_MONTH=%date:~8,2%

call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/pwd_exipired_time.%REPORT_FORMAT% -F %REPORT_HOME%/pwd_policy/pwd_expired_time.rptdesign

call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/pwd_exipired_time.%REPORT_FORMAT% -F %REPORT_HOME%/user_login/user_login_last_time.rptdesign

call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/year_report_by_app.%REPORT_FORMAT% -F %REPORT_HOME%/app_login/year_report_by_app.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/year_report_by_network.%REPORT_FORMAT% -F %REPORT_HOME%/app_login/year_report_by_net.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/year_report_by_webseal.%REPORT_FORMAT% -F %REPORT_HOME%/app_login/year_report_by_webseal.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/year_report.%REPORT_FORMAT% -F %REPORT_HOME%/app_login/year_report.rptdesign

call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/month_report_by_app_%YEAR%.%REPORT_FORMAT% -p "Year=%YEAR%" -F %REPORT_HOME%/app_login/month_report_by_app.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/month_report_by_network_%YEAR%.%REPORT_FORMAT% -p "Year=%YEAR%" -F %REPORT_HOME%/app_login/month_report_by_net.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/month_report_by_webseal_%YEAR%.%REPORT_FORMAT% -p "Year=%YEAR%" -F %REPORT_HOME%/app_login/month_report_by_webseal.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/month_report_%YEAR%.%REPORT_FORMAT% -p "Year=%YEAR%" -F %REPORT_HOME%/app_login/month_report.rptdesign

call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/day_report_by_app_%YEAR%%MONTH%.%REPORT_FORMAT% -p "Year=%YEAR%" -p "Month=%MONTH%" -F %REPORT_HOME%/app_login/day_of_month_report_by_app.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/day_report_by_network_%YEAR%%MONTH%.%REPORT_FORMAT% -p "Year=%YEAR%" -p "Month=%MONTH%" -F %REPORT_HOME%/app_login/day_of_month_report_by_net.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/day_report_by_webseal_%YEAR%%MONTH%.%REPORT_FORMAT% -p "Year=%YEAR%" -p "Month=%MONTH%" -F %REPORT_HOME%/app_login/day_of_month_report_by_webseal.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/day_report_%YEAR%%MONTH%.%REPORT_FORMAT% -p "Year=%YEAR%" -p "Month=%MONTH%" -F %REPORT_HOME%/app_login/day_of_month_report.rptdesign

call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/hour_report_by_app_%YEAR%%MONTH%%DAY_OF_MONTH%.%REPORT_FORMAT% -p "Year=%YEAR%" -p "Month=%MONTH%" -p "DAY_OF_MONTH=%DAY_OF_MONTH%" -F %REPORT_HOME%/app_login/hour_report_by_app.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/hour_report_by_network_%YEAR%%MONTH%%DAY_OF_MONTH%.%REPORT_FORMAT% -p "Year=%YEAR%" -p "Month=%MONTH%" -p "DAY_OF_MONTH=%DAY_OF_MONTH%" -F %REPORT_HOME%/app_login/hour_report_by_net.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/hour_report_by_webseal_%YEAR%%MONTH%%DAY_OF_MONTH%.%REPORT_FORMAT% -p "Year=%YEAR%" -p "Month=%MONTH%" -p "DAY_OF_MONTH=%DAY_OF_MONTH%" -F %REPORT_HOME%/app_login/hour_report_by_webseal.rptdesign
call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/hour_report_%YEAR%%MONTH%%DAY_OF_MONTH%.%REPORT_FORMAT% -p "Year=%YEAR%" -p "Month=%MONTH%" -p "DAY_OF_MONTH=%DAY_OF_MONTH%" -F %REPORT_HOME%/app_login/hour_report.rptdesign

call %REPORT_HOME%\genReport -m runrender -f %REPORT_FORMAT% -o %REPORT_OUTPUT%/app_login_detail_report_%YEAR%%MONTH%%DAY_OF_MONTH%.%REPORT_FORMAT% -F %REPORT_HOME%/app_login/app_login_detail_report.rptdesign


