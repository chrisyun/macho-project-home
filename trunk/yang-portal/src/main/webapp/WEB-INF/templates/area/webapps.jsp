<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

  <div id="classtitle" class="clearfix">
    <div id="cttop">
      <h1>${__area.label}</h1>
    </div>
  </div>
  <div class="toollist wid clearfix">
    <ul style="background: #fff; float: left; margin: 6px; border-radius: 4px; padding-top: 8px;">
    <c:forEach var="item" items="${__area.items}">
      <li><a href="${item.navigation.url}"><i></i><img src="${item.navigation.icon}" height="57" width="57" /><br />${item.navigation.label}</a></li>
    </c:forEach>
    </ul>
  </div>
