<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="${projectName}" />
    </c:import>
<body>
  <div class="wrapper">
      <c:import url="/WEB-INF/layouts/nav.jsp" />
      <section>
          <div class="content-wrapper">
              欢迎使用${projectName}
          </div>
      </section>
      <c:import url="/WEB-INF/layouts/content_footer.jsp" />
  </div>
<c:import url="/WEB-INF/layouts/footer.jsp" />
</body>
</html>