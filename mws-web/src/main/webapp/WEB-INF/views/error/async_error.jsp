<%@page pageEncoding="UTF-8" %>
<%
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Pragma", "no-cache");
    response.addHeader("Expires", "-1");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("content-type", "application/json;charset=utf-8");
    response.setStatus(500);
    String error = (String)request.getAttribute("error");
    out.print(error);
%>
