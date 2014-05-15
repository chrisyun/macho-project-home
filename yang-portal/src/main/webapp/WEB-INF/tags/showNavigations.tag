<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%@ attribute name="categoryId" required="true" rtexprvalue="true" description="name of text element"%>
<%@ attribute name="template" required="true" rtexprvalue="true" description="Bind path of text element"%>

<%
String categoryId = (String)jspContext.getAttribute("categoryId");
com.tutucha.model.service.DataService dataService = (com.tutucha.model.service.DataService)application.getAttribute("dataService");
request.setAttribute("__navigations", dataService.getNavigationsByCategory(categoryId));
request.setAttribute("__navigationCategory", dataService.getCategoryById(categoryId));
%>
<jsp:include page="/WEB-INF/templates/category/${template}.jsp"></jsp:include>
