<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Welcome to Access App Login Report Page</title>  
    <link type="text/css" href="<%= request.getContextPath()%>/css/ui-lightness/jquery-ui-1.8.14.custom.css" rel="stylesheet" /> 
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-ui-1.8.14.custom.min.js"></script>
    <%
    
    java.util.Date now = new java.util.Date();
    int year = now.getYear() + 1900;
    int month = now.getMonth() + 1;
    int day = now.getDate() - 1;
    
    java.util.Date yestoday = new java.util.Date(now.getTime() - 24 * 3600 * 1000);
    java.util.Date tommorow = new java.util.Date(now.getTime() + 24 * 3600 * 1000);
    
    
    %>
    
    <script type="text/javascript">
      $(function(){
        var availableYear = [
          "2011",
          "2012",
          "2013"
        ];
        var availableMonth = [
          "1","2","3","4","5","6","7","8","9","10","11","12"
        ];

        var availiableApp = [
                             "%",
                             "EP(电子采购)",
                             "Universe Portal",
                             "PMS",
                             "DSM(Siebel)",
                             "RTPMS",
                             "CI SGM市场研究分析平台",
                             "QiLin Portal",
                             "Dfsmdms",
                             "PATAC ETS",
                             "PATAC SDAC",
                             "SMART(EDW)",
                             "ROL",
                             "DMSUC",
                             "Vitas QIP(pdi)",
                             "MyTrade",
                             "SGMPM",
                             "MyGod(CEM)",
                             "Partsplan(SVG)",
                             "VOTING(党建直选)",
                             "VAS(Vitas QIP subsys)",
                             "BPMA(EIT)",
                             "BPMA(bup土建)",
                             "BPMA(crdn)",
                             "BPMA(PQS)",
                             "BDW(品牌差异化)",
                             "SGMBRMWeb会议预定系统",
                             "EAM（刀具管理系统）",
                             "DMES",
                             "PkgmsSts",
                             "ITMS",
                             "ITIM",
                             "WebMail",
                             "authorwps",
                             "tdi",
                             "tim/itim_expi",
                             "Root",
                             "DSM(Siebel)",
                             "ITMS",
                             "MyGod(CEM)",
                             "WebMail",
                             "RTPMS",
                             "DMES",
                             "DMSUC",
                             "EAM（刀具管理系统）",
                             "BDW(品牌差异化)",
                             "SMART(EDW)",
                             "ROL",
                             "WWW",
                             "EP（电子采购）",
                             "308 出口车订单系统",
                             "GP50",
                             "RDS258",
                             "EBMS",
                             "HRMS",
                             "JQ PATAC webmail",
                             "DY webmail",
                             "SY webmail"
                             ];

            
        // Accordion
        $("#accordion").accordion({ header: "h3" });

        $( "#year4monthlyReport, #year4dailylyReport, #year4hourlyReport, #year4monthlyReportUnauth, #year4dailylyReportUnauth, #year4hourlyReportUnauth" ).autocomplete({
          source: availableYear,
          autoFocus: true
        });
        
        $( "#month4dailylyReport, #month4hourlyReport, #month4dailylyReportUnauth, #month4hourlyReportUnauth" ).autocomplete({
          source: availableMonth,
          autoFocus: true
        });

        $( "#application4detail, #application4detailUnauth" ).autocomplete({
            source: availiableApp,
            autoFocus: true
          });

        $( "input:submit, button").button();
            
        var dates = $( "#from, #to" ).datepicker({
          defaultDate: "+1w",
          changeMonth: true,
          numberOfMonths: 3,
          onSelect: function( selectedDate ) {
            var option = this.id == "from" ? "minDate" : "maxDate",
              instance = $( this ).data( "datepicker" ),
              date = $.datepicker.parseDate(
                instance.settings.dateFormat ||
                $.datepicker._defaults.dateFormat,
                selectedDate, instance.settings );
            dates.not( this ).datepicker( "option", option, date );
          }
        });
        
        var dates = $( "#fromUnauth, #toUnauth" ).datepicker({
          defaultDate: "+1w",
          changeMonth: true,
          numberOfMonths: 3,
          onSelect: function( selectedDate ) {
            var option = this.id == "fromUnauth" ? "minDate" : "maxDate",
              instance = $( this ).data( "datepicker" ),
              date = $.datepicker.parseDate(
                instance.settings.dateFormat ||
                $.datepicker._defaults.dateFormat,
                selectedDate, instance.settings );
            dates.not( this ).datepicker( "option", option, date );
          }
        });
        
        
      });
    </script>
    <style type="text/css">
      /*demo page css*/
      body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 10px;}
      ul#icons {margin: 0; padding: 0;}
      ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: none; float: left;  list-style: none;}
      ul#icons span.ui-icon {float: left; margin: 0 4px;}
    </style>  
  <%
    String javaVersion = System.getProperty("java.version");
    String viewerVersion = "3.7.0";
    String engineVersion = "3.7.0";
  %>
  </head>
  <body>
  <IMG src="sgm/ESSOlogo.jpg" alt="Logo" border=0>
  <h1>Welcome to Access App Login Report Page</h1>
  
    <!-- Accordion -->
    <div id="accordion">
      <div>
        <h3><a href="#">Application Login Yearly Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <ul id="icons">
              <li><button onclick="this.form.__report.value='app_login/year_report_by_net.rptdesign';this.form.submit();"/>By Network</button></li>
              <li><button onclick="this.form.__report.value='app_login/year_report_by_app.rptdesign';this.form.submit();"/>By Application</button></li>
              <li><button onclick="this.form.__report.value='app_login/year_report_by_webseal.rptdesign';this.form.submit();"/>By WebSEAL</button></li>
              <li><button onclick="this.form.__report.value='app_login/year_report.rptdesign';this.form.submit();"/>Detail</button></li>
            </ul>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">Application Login Monthly Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <label for="tags">Year: </label><input id="year4monthlyReport" type="text" name="Year" value="<%=year%>"/>
            <ul id="icons">
              <li><button onclick="this.form.__report.value='app_login/month_report_by_net.rptdesign';this.form.submit();"/>By Network</button></li>
              <li><button onclick="this.form.__report.value='app_login/month_report_by_app.rptdesign';this.form.submit();"/>By Application</button></li>
              <li><button onclick="this.form.__report.value='app_login/month_report_by_webseal.rptdesign';this.form.submit();"/>By WebSEAL</button></li>
              <li><button onclick="this.form.__report.value='app_login/month_report.rptdesign';this.form.submit();"/>Detail</button></li>
            </ul>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">Application Login Daily Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <label for="year4dailylyReport">Year: </label><input id="year4dailylyReport" type="text" name="Year" value="<%=year%>"/>&nbsp;&nbsp;
            <label for="month4dailylyReport">Month: </label><input id="month4dailylyReport" type="text" name="Month" value="<%=month%>"/>
            <ul id="icons">
              <li><button onclick="this.form.__report.value='app_login/day_of_month_report_by_net.rptdesign';this.form.submit();"/>By Network</button></li>
              <li><button onclick="this.form.__report.value='app_login/day_of_month_report_by_app.rptdesign';this.form.submit();"/>By Application</button></li>
              <li><button onclick="this.form.__report.value='app_login/day_of_month_report_by_webseal.rptdesign';this.form.submit();"/>By WebSEAL</button></li>
              <li><button onclick="this.form.__report.value='app_login/day_of_month_report.rptdesign';this.form.submit();"/>Detail</button></li>
            </ul>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">Application Login Hourly Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <label for="year4hourlyReport">Year: </label><input id="year4hourlyReport" type="text" name="Year" value="<%=year%>"/>&nbsp;&nbsp;
            <label for="month4hourlyReport">Day: </label><input id="month4hourlyReport" type="text" name="Month" value="<%=month%>"/>&nbsp;&nbsp;
            <label for="hour4hourlyReport">Day: </label><input id="hour4hourlyReport" type="text" name="DAY_OF_MONTH" value="<%=day%>"/>
            <ul id="icons">
              <li><button onclick="this.form.__report.value='app_login/hour_report_by_net.rptdesign';this.form.submit();"/>By Network</button></li>
              <li><button onclick="this.form.__report.value='app_login/hour_report_by_app.rptdesign';this.form.submit();"/>By Application</button></li>
              <li><button onclick="this.form.__report.value='app_login/hour_report_by_webseal.rptdesign';this.form.submit();"/>By WebSEAL</button></li>
              <li><button onclick="this.form.__report.value='app_login/hour_report.rptdesign';this.form.submit();"/>Detail</button></li>
            </ul>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">Application Login Detail Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <label for="application4detail">Application: </label><input id="application4detail" type="text" name="Application" value="%"/><br/>
            <label for="uid4detail">Username: &nbsp;&nbsp;</label><input id="uid4detail" type="text" name="UserID" value="%"/><br/>
            <label for="from">Start Date: </label>
            <input type="text" id="from" name="StartTime" value="<%=yestoday.getMonth() +1%>/<%=yestoday.getDate() + 1%>/<%=yestoday.getYear() + 1900%>"/>
            <label for="to">End Date: </label>
            <input type="text" id="to" name="EndTime" value="<%=tommorow.getMonth() +1%>/<%=tommorow.getDate() + 1%>/<%=tommorow.getYear() + 1900%>"/>
            <br/><br/>
            <button onclick="this.form.__report.value='app_login/app_login_detail_report.rptdesign';this.form.submit();"/>View Report</button>
          </form>
        </div>
      </div>


      <div>
        <h3><a href="#">Application Unauth Yearly Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <ul id="icons">
              <li><button onclick="this.form.__report.value='app_unauth/year_report_by_net.rptdesign';this.form.submit();"/>By Network</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/year_report_by_app.rptdesign';this.form.submit();"/>By Application</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/year_report_by_webseal.rptdesign';this.form.submit();"/>By WebSEAL</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/year_report.rptdesign';this.form.submit();"/>Detail</button></li>
            </ul>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">Application Unauth Monthly Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <label for="tags">Year: </label><input id="year4monthlyReportUnauth" type="text" name="Year" value="<%=year%>"/>
            <ul id="icons">
              <li><button onclick="this.form.__report.value='app_unauth/month_report_by_net.rptdesign';this.form.submit();"/>By Network</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/month_report_by_app.rptdesign';this.form.submit();"/>By Application</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/month_report_by_webseal.rptdesign';this.form.submit();"/>By WebSEAL</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/month_report.rptdesign';this.form.submit();"/>Detail</button></li>
            </ul>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">Application Unauth Daily Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <label for="year4dailylyReport">Year: </label><input id="year4dailylyReportUnauth" type="text" name="Year" value="<%=year%>"/>&nbsp;&nbsp;
            <label for="month4dailylyReport">Month: </label><input id="month4dailylyReportUnauth" type="text" name="Month" value="<%=month%>"/>
            <ul id="icons">
              <li><button onclick="this.form.__report.value='app_unauth/day_of_month_report_by_net.rptdesign';this.form.submit();"/>By Network</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/day_of_month_report_by_app.rptdesign';this.form.submit();"/>By Application</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/day_of_month_report_by_webseal.rptdesign';this.form.submit();"/>By WebSEAL</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/day_of_month_report.rptdesign';this.form.submit();"/>Detail</button></li>
            </ul>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">Application Unauth Hourly Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <label for="year4hourlyReport">Year: </label><input id="year4hourlyReportUnauth" type="text" name="Year" value="<%=year%>"/>&nbsp;&nbsp;
            <label for="month4hourlyReport">Day: </label><input id="month4hourlyReportUnauth" type="text" name="Month" value="<%=month%>"/>&nbsp;&nbsp;
            <label for="hour4hourlyReport">Day: </label><input id="hour4hourlyReportUnauth" type="text" name="DAY_OF_MONTH" value="<%=day%>"/>
            <ul id="icons">
              <li><button onclick="this.form.__report.value='app_unauth/hour_report_by_net.rptdesign';this.form.submit();"/>By Network</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/hour_report_by_app.rptdesign';this.form.submit();"/>By Application</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/hour_report_by_webseal.rptdesign';this.form.submit();"/>By WebSEAL</button></li>
              <li><button onclick="this.form.__report.value='app_unauth/hour_report.rptdesign';this.form.submit();"/>Detail</button></li>
            </ul>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">Application Unauth Detail Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <label for="application4detail">Application: </label><input id="application4detailUnauth" type="text" name="Application" value="%"/><br/>
            <label for="uid4detail">Username: &nbsp;&nbsp;</label><input id="uid4detailUnauth" type="text" name="UserID" value="%"/><br/>
            <label for="from">Start Date: </label>
            <input type="text" id="fromUnauth" name="StartTime" value="<%=yestoday.getMonth() +1%>/<%=yestoday.getDate() + 1%>/<%=yestoday.getYear() + 1900%>"/>
            <label for="to">End Date: </label>
            <input type="text" id="toUnauth" name="EndTime" value="<%=tommorow.getMonth() +1%>/<%=tommorow.getDate() + 1%>/<%=tommorow.getYear() + 1900%>"/>
            <br/><br/>
            <button onclick="this.form.__report.value='app_unauth/app_login_detail_report.rptdesign';this.form.submit();"/>View Report</button>
          </form>
        </div>
      </div>

      <div>
        <h3><a href="#">User Last Login Time Detail Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <label for="uid4detail">Username: &nbsp;&nbsp;</label><input id="uid4detail" type="text" name="UserID" value="%"/><br/>
            <br/><br/>
            <button onclick="this.form.__report.value='user_login/user_login_last_time.rptdesign';this.form.submit();"/>View Report</button>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">User Password Expired Time Detail Report</a></h3>
        <div>
          <form method="get" target="_blank" action="frameset?__format=html&__overwrite=true&__locale=en_US&__svg=false&__designer=false&__pageoverflow=0&__masterpage=true">
            <input type="hidden" name="__report" value=""/>
            <input type="hidden" name="__title" value="SGM UIM Report Service"/>
            <br/><br/>
            <button onclick="this.form.__report.value='pwd_policy/pwd_expired_time.rptdesign';this.form.submit();"/>View Report</button>
          </form>
        </div>
      </div>
      <div>
        <h3><a href="#">About ...</a></h3>
        <div>
            <%
              String javaVersionMessage = javaVersion;
              
              // check Java version
              String[] versionParts = javaVersion.split("\\.");
              int majorVersion = 0;
              int minorVersion = 0;
              try
              {
                majorVersion = Integer.parseInt(versionParts[0]);   
                minorVersion = Integer.parseInt(versionParts[1]);
                if ( majorVersion < 1 || ( majorVersion == 1 && minorVersion < 5 ) )
                {
                  javaVersionMessage = "<span class=\"warningMessage\">" + javaVersion + " (WARNING: BIRT " + viewerVersion + " only supports JRE versions >= 1.5)</span>";
                }
              }
              catch (NumberFormatException e)
              {
              
              }
            %>
            <p>Report Viewer Version : <%= viewerVersion %></p>
            <p>Report Engine Version: <%= engineVersion %></p>
            <p>JRE version: <%= javaVersionMessage  %></p>
        </div>
      </div>
    </div>

  </body>
</html>


