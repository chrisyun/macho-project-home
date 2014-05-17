<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<nav id="nav" class="nav">
  <h2 id="gameajax" class="nav_cur" data-loc="wz">${__area.label}</h2>
</nav>
<div id="con_wz">
  <div class="common_block">
    <ul class="app_img app_img_line">
      <c:forEach var="item" items="${__area.items}">
        <li><a href="?op=navigationshow&id=${item.navigation.id}&rid=${item.id}&rpos=${__area.id}"><img
            src="${item.navigation.icon}" height="36" width="36"><span>${item.navigation.label}</span></a></li>
      </c:forEach>
    </ul>
    <p class="common_more">
      <a style="color: #fff; background: #0290FF" href="?op=showcategory&categoryId=259">点击获取更多好用的APP</a>
    </p>
  </div>
</div>
