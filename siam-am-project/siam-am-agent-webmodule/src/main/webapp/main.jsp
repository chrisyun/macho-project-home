<%@page import="com.ibm.siam.agent.common.SSOPrincipal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome, <%=session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) %></title>
</head>
<body>
Welcome, <%=((SSOPrincipal)session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR)).getUid() %> [<%=((SSOPrincipal)session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR)).getAuthenMethod() %>]!
<br/><br/>
<h3><a href="./SSO/SLO/Redirect">Logout</a></h3>
</body>
</html>