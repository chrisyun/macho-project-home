<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="common_block changyong">
  <ul>
    <c:forEach var="item" items="${__area.items}">
      <li><a href='?op=navshow&id=${item.navigation.id}&rid=${item.id}&rpos=${__area.id}' data-openinapp='cydh'><span
          ${item.navigation.icon}></span> ${item.navigation.label} </a></li>
    </c:forEach>
  </ul>
</div>
