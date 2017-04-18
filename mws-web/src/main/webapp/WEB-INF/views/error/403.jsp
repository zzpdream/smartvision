<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<c:import url="/WEB-INF/layouts/header.jsp">
		<c:param name="title" value="权限不足" />
	</c:import>
</head>

<body>
	<div class="wrapper">
		<div class="abs-center wd-xl">
			<!-- START panel-->
			<div class="text-center mb-xl">
				<div class="text-lg mb-lg">403</div>
				<p class="lead m0">权限不足</p>
				<p>您没有权限浏览您访问的地址</p>
			</div>
			<%--<div class="input-group mb-xl">--%>
				<%--<input type="text" placeholder="Try with a search" class="form-control">--%>
            <%--<span class="input-group-btn">--%>
               <%--<button type="button" class="btn btn-default">--%>
				   <%--<em class="fa fa-search"></em>--%>
			   <%--</button>--%>
            <%--</span>--%>
			<%--</div>--%>
			<ul class="list-inline text-center text-sm mb-xl">
				<li><a href="${ctx}/users" class="text-muted">主页</a>
				</li>
				<li class="text-muted">|</li>
				<li><a href="${ctx}/login" class="text-muted">登陆</a>
				</li>
			</ul>
			<div class="p-lg text-center">
				<span>&copy;</span>
				<span>2015</span>
				<span>-</span>
				<span></span>
				<br>
				<span></span>
			</div>
		</div>
	</div>
<c:import url="/WEB-INF/layouts/footer.jsp" />

</body>

</html>