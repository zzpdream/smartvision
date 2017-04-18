<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="登录 - ${projectName}" />
    </c:import>
</head>

<body>
<div class="wrapper">
    <div class="block-center mt-xl wd-xl">
        <!-- START panel-->
        <div class="panel panel-dark panel-flat">
            <div class="panel-heading text-center" style="background-color:#EF8610">
                <h3>
                    ${projectName}
                </h3>
            </div>
            <div class="panel-body">
                <p class="text-center pv">系统登录</p>

                <form id="form-login" action="${ctx}/login" method="POST" role="form" class="mb-lg">
                    <div class="form-group has-feedback">
                        <input id="username" name="username" type="text" placeholder="账号" value="${username}" autocomplete="off" class="form-control">
                        <span class="fa fa-envelope form-control-feedback text-muted"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input id="password" name="password" type="password" placeholder="密码" class="form-control" value="987654321">
                        <span class="fa fa-lock form-control-feedback text-muted"></span>
                    </div>
                    <div class="clearfix">
                        <div class="checkbox c-checkbox pull-left mt0">
                            <label>
                                <input type="checkbox" value="true" name="rememberMe">
                                <span class="fa fa-check"></span>记住我</label>
                        </div>
                        <%--<div class="pull-right"><a href="recover.html" class="text-muted">忘记密码?</a>--%>
                        <%--</div>--%>
                    </div>
                    <button id="btn-login" type="submit" class="btn btn-block btn-primary mt-lg" data-loading-text="登陆中...">登 陆</button>
                </form>
                <%--<p class="pt-lg text-center">Need to Signup?</p>--%>
                <%--<a href="register.html" class="btn btn-block btn-default">闪电注册</a>--%>
            </div>
        </div>
        <div class="p-lg text-center">
            <span>&copy;Copyright</span>
            <span>2015</span>
            <span>-2016</span>
            <span>All Rights Reserved.</span>
            <br>
            <span>Yingdiandian Support</span>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/layouts/footer.jsp" />
<script src="${ctx}/static/third/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/js/login.js"></script>
</body>

</html>