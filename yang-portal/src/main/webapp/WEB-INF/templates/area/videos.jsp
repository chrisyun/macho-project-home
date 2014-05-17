<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<nav id="nav" class="nav">
  <h2 id="gameajax" class="nav_cur" data-loc="wz">${__area.label}</h2>
</nav>
<div id="con_wz">
  <div class="tbv_box" id="tbv_yingshi_movie">
    <div class="common_block">
      <div class="tbv_body">
        <div class="tbv_list">
          <ul>
      <c:forEach var="item" items="${__area.items}">
            <li><a href="?op=navigationshow&id=${item.navigation.id}&rid=${item.id}&rpos=${__area.id}"><img
                src="${item.navigation.icon}" height="110" width="80"><em> 86分</em></a>
              <p>${item.navigation.label}</p></li>
      </c:forEach>
          </ul>
        </div>
      </div>
      <p class="common_more">
        <a style="color: #fff; background: #0290FF" href="?op=showcategory&categoryId=459">点击获取更多好看的电影</a>
      </p>
    </div>
  </div>
</div>