<%@tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="value" required="true" rtexprvalue="true" description="name of text element"%>
<%
String v = (String)jspContext.getAttribute("value");
if (v != null) {
   long d = java.lang.Long.parseLong(v);
   if (d > 999999) {
      out.print(String.format("%s百万", d/1000000));
   } else if (d > 9999) {
      out.print(String.format("%s万", d/10000));
   } else {
     out.print(d);
   }
}
%>