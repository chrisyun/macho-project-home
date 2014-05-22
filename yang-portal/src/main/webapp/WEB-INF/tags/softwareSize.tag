<%@tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="value" required="true" rtexprvalue="true" description="name of text element"%>
<%
String v = (String)jspContext.getAttribute("value");
if (v != null) {
   long d = java.lang.Long.parseLong(v);
   if (d > 1024 * 1024) {
      out.print(String.format("%sM", d/1024/1024));
   } else {
     out.print(String.format("%sK", d/1024));
   }
}
%>