<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>SGM WebSEAL Report List</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<LINK href="styles/iv/index.css" type=text/css rel=stylesheet>
		<LINK href="http://www.eclipse.org/images/eclipse.ico" type=image/x-icon rel="shortcut icon">
		<STYLE>
			.warningMessage { color:red; }
		</STYLE>
	<%
		String javaVersion = System.getProperty("java.version");
		String viewerVersion = "3.7.0";
		String engineVersion = "3.7.0";
	%>
	</HEAD>
	<BODY>
		<!-- Page banner -->
		<TABLE class=banner-area cellSpacing=0 cellPadding=0 width="100%" border=0>
			<TBODY>
				<TR>
					<TD width="100%">
						<IMG src="sgm/ESSOlogo.jpg" alt="Logo" border=0>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!-- Table with menu & content -->
		<TABLE cellSpacing=0 cols=2 cellPadding=0 border=0 width="100%">
			<TBODY>
				<TR>
					<TD class=content style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px" >
            <hr/>
						<!-- Content area -->
						<p><a href="<%= request.getContextPath( ) + "/frameset?__report=app_login/year_report.rptdesign&__title=SGM+WebSEAL+Report" %>" target="_blank">View [Application Login Report By Year]</a>
            <p><a href="<%= request.getContextPath( ) + "/frameset?__report=app_login/month_report.rptdesign&__title=SGM+WebSEAL+Report" %>" target="_blank">View [Application Login Report By Month]</a>
            <p><a href="<%= request.getContextPath( ) + "/frameset?__report=app_login/day_of_month_report.rptdesign&__title=SGM+WebSEAL+Report" %>" target="_blank">View [Application Login Report By Day]</a>
            <p><a href="<%= request.getContextPath( ) + "/frameset?__report=app_login/hour_report.rptdesign&__title=SGM+WebSEAL+Report" %>" target="_blank">View [Application Login Report By Hour]</a>
            <p><a href="<%= request.getContextPath( ) + "/frameset?__report=app_login/app_login_detail_report.rptdesign&__title=SGM+WebSEAL+Report" %>" target="_blank">View [Application Login Detail]</a>
            <hr/>
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
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</BODY>
</HTML>