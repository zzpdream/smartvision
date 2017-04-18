<%--
  Created by IntelliJ IDEA.
  User: whan
  Date: 10/10/15
  Time: 5:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/layouts/tag.jsp" %>

<c:set var="current" value="${page.number + 1}" />
<c:set var="begin"  value="${current-3}" />
<c:if test="${begin <= 0}">
    <c:set var="begin"  value="1" />
</c:if>
<c:set var="end"  value="${current+3}" />
<c:if test="${end > page.getTotalPages()}">
    <c:set var="end"  value="${page.getTotalPages()}" />
</c:if>

<nav class="pagination-wrapper">
    <span>第 <em class="text-primary">${current}</em> 页，共 <em class="text-primary">${page.totalPages}</em> 页</span>
    <ul class="pagination pagination-sm pull-right m0">
        <li class="${page.isFirst() ? "disabled" : ""}">
            <a href="javascript:Pagination.load(1);" aria-label="Previous">
                <span aria-hidden="true">首页</span>
            </a>
        </li>
        <li class="${page.hasPrevious() ? "" : "disabled"}">
            <a href="javascript:Pagination.load(${current-1});" aria-label="Previous">
                <span aria-hidden="true">上一页</span>
            </a>
        </li>


        <c:forEach var="i" begin="${begin}" end="${end}">
            <li class="${i == current ? 'active' : ''}"><a href="javascript:Pagination.load(${i});">${i}</a></li>
        </c:forEach>

        <li class="${page.hasNext() ? "" : "disabled"}">
            <a href="javascript:Pagination.load(${page.hasNext() ? current+1 : current});" aria-label="Next">
                <span aria-hidden="true">下一页</span>
            </a>
        </li>
        <li class="${page.isLast() ? "disabled" : ""}">
            <a href="javascript:Pagination.load(${page.getTotalPages()});" aria-label="Previous">
                <span aria-hidden="true">尾页</span>
            </a>
        </li>
    </ul>
</nav>
