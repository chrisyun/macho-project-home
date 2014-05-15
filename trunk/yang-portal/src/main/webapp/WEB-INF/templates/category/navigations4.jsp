<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div class="fenlei">
  <p class="fenlei_p">
    <a class="fenlei_a" href="?op=showCategory&id=${__navigationCategory.id}">${__navigationCategory.label}</a><i class="fenlei_i"> 
    <c:forEach var="navigation" items="${__navigations}"><a href="?op=navshow&id=${navigation.id}">${navigation.label}</a></c:forEach>
    </i>
  </p>
</div>
