<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%@ attribute name="pageId" required="true" rtexprvalue="true" description="name of text element"%>
<%@ attribute name="areaId" required="false" rtexprvalue="true" description="Label of text element"%>
<%@ attribute name="template" required="true" rtexprvalue="true" description="Bind path of text element"%>

<%
String pageId = (String)jspContext.getAttribute("pageId");
String areaId = (String)jspContext.getAttribute("areaId");
com.tutucha.model.service.DataService dataService = (com.tutucha.model.service.DataService)application.getAttribute("dataService");
com.tutucha.model.entity.Area area = dataService.getAreaById(pageId, areaId);
request.setAttribute("__area", area);
%>
<jsp:include page="/WEB-INF/templates/area/${template}.jsp"></jsp:include>
