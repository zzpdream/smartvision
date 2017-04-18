<%@ page import="com.mws.web.common.bo.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp" %>
<div class="table-responsive">
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>
                <div class="checkbox c-checkbox">
                    <label>
                        <input type="checkbox" class="checkbox-global">
                        <span class="fa fa-check"></span>
                    </label>
                </div>
            </th>
            <th>ID</th>
            <th>软件升级版本号</th>
            <th>软件版本号</th>
            <th>地址</th>
            <th>备注</th>
            <th>创建时间</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty page.content}">
            <tr>
                <td colspan="8" class="text-center">您还未添加过数据！</td>
            </tr>
        </c:if>
        <c:forEach items="${page.content}" var="appUpgrade" varStatus="status">
            <tr>
               <td>
                    <div class="checkbox c-checkbox">
                        <label>
                            <input type="checkbox" class="checkbox-item" name="id" value="${appUpgrade.id}">
                            <span class="fa fa-check"></span>
                        </label>
                    </div>
                </td>
                <td>
                    ${appUpgrade.id}
                </td>
                <td>${appUpgrade.versionCode}</td>
                <td>${appUpgrade.appVersion}</td>
                <td><c:if test="${not empty appUpgrade.url}">
						<div><a class="filename" target="_blank" href="${image_url }${appUpgrade.url}">下载</a>
						</div>
					</c:if>
				</td>
                <td>${appUpgrade.remark}</td>
                <td><fmt:formatDate value="${appUpgrade.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:import url="/WEB-INF/layouts/pagination.jsp" />
</div>
