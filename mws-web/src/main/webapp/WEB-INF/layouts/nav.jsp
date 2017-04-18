<%--
  Created by IntelliJ IDEA.
  User: whan
  Date: 10/8/15
  Time: 4:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="tag.jsp" %>
<!-- top navbar-->
<header class="topnavbar-wrapper">
    <!-- START Top Navbar-->
    <nav role="navigation" class="navbar topnavbar">
        <!-- START navbar header-->
        <div class="navbar-header">
            <a href="${ctx}/main" class="navbar-brand">
                <div class="brand-logo" style="color: white">
                       <em class="fa fa-leaf"></em> ${projectName}
                    <%--<img src="${ctx}/static/img/tyl.png" alt="App Logo"  class="img-responsive" style="position: relative;left:10px;top: -10px;">--%>
                </div>
                <div class="brand-logo-collapsed">
                    <img src="${ctx}/static/img/logo-single.png" alt="App Logo" class="img-responsive">
                </div>
            </a>
        </div>
        <!-- END navbar header-->
        <!-- START Nav wrapper-->
        <div class="nav-wrapper">
            <!-- START Left navbar-->
            <ul class="nav navbar-nav">
                <li>
                    <!-- Button used to collapse the left sidebsed" class="ar. Only visible on tablet and desktops-->
                    <a href="#" data-toggle-state="aside-collaphidden-xs">
                        <em class="fa fa-navicon"></em>
                    </a>
                    <!-- Button to show/hide the sidebar on mobile. Visible on mobile only.-->
                    <a href="#" data-toggle-state="aside-toggled" data-no-persist="true"
                       class="visible-xs sidebar-toggle">
                        <em class="fa fa-navicon"></em>
                    </a>
                </li>
                <!-- START User avatar toggle-->
                <li>
                    <!-- Button used to collapse the left sidebar. Only visible on tablet and desktops-->
                    <a id="user-block-toggle" href="#user-block" data-toggle="collapse">
                        <em class="icon-user"></em>
                    </a>
                </li>
                <!-- END User avatar toggle-->

            </ul>
            <!-- END Left navbar-->
            <!-- START Right Navbar-->
            <ul class="nav navbar-nav navbar-right">
                <!-- 退出登陆-->
                <li>
                    <a href="${ctx}/logout" title="Lock screen">
                        <em class="icon-logout"></em>
                    </a>
                </li>
                <!-- 退出登陆-->
                <%--<!-- Search icon-->--%>
                <%--<li>--%>
                    <%--<a href="#" data-search-open="">--%>
                        <%--<em class="icon-magnifier"></em>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<!-- Fullscreen (only desktops)-->--%>
                <%--<li class="visible-lg">--%>
                    <%--<a href="#" data-toggle-fullscreen="">--%>
                        <%--<em class="fa fa-expand"></em>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<!-- START Alert menu-->--%>
                <%--<li class="dropdown dropdown-list">--%>
                    <%--<a href="#" data-toggle="dropdown">--%>
                        <%--<em class="icon-bell"></em>--%>

                        <%--<div class="label label-danger">11</div>--%>
                    <%--</a>--%>
                    <%--<!-- START Dropdown menu-->--%>
                    <%--<ul class="dropdown-menu animated flipInX">--%>
                        <%--<li>--%>
                            <%--<!-- START list group-->--%>
                            <%--<div class="list-group">--%>
                                <%--<!-- list item-->--%>
                                <%--<a href="#" class="list-group-item">--%>
                                    <%--<div class="media-box">--%>
                                        <%--<div class="pull-left">--%>
                                            <%--<em class="fa fa-twitter fa-2x text-info"></em>--%>
                                        <%--</div>--%>
                                        <%--<div class="media-box-body clearfix">--%>
                                            <%--<p class="m0">New followers</p>--%>

                                            <%--<p class="m0 text-muted">--%>
                                                <%--<small>1 new follower</small>--%>
                                            <%--</p>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</a>--%>
                                <%--<!-- list item-->--%>
                                <%--<a href="#" class="list-group-item">--%>
                                    <%--<div class="media-box">--%>
                                        <%--<div class="pull-left">--%>
                                            <%--<em class="fa fa-envelope fa-2x text-warning"></em>--%>
                                        <%--</div>--%>
                                        <%--<div class="media-box-body clearfix">--%>
                                            <%--<p class="m0">New e-mails</p>--%>

                                            <%--<p class="m0 text-muted">--%>
                                                <%--<small>You have 10 new emails</small>--%>
                                            <%--</p>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</a>--%>
                                <%--<!-- list item-->--%>
                                <%--<a href="#" class="list-group-item">--%>
                                    <%--<div class="media-box">--%>
                                        <%--<div class="pull-left">--%>
                                            <%--<em class="fa fa-tasks fa-2x text-success"></em>--%>
                                        <%--</div>--%>
                                        <%--<div class="media-box-body clearfix">--%>
                                            <%--<p class="m0">Pending Tasks</p>--%>

                                            <%--<p class="m0 text-muted">--%>
                                                <%--<small>11 pending task</small>--%>
                                            <%--</p>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</a>--%>
                                <%--<!-- last list item -->--%>
                                <%--<a href="#" class="list-group-item">--%>
                                    <%--<small>More notifications</small>--%>
                                    <%--<span class="label label-danger pull-right">14</span>--%>
                                <%--</a>--%>
                            <%--</div>--%>
                            <%--<!-- END list group-->--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                    <%--<!-- END Dropdown menu-->--%>
                <%--</li>--%>
                <%--<!-- END Alert menu-->--%>
                <%--<!-- START Contacts button-->--%>
                <%--<li>--%>
                    <%--<a href="#" data-toggle-state="offsidebar-open" data-no-persist="true">--%>
                        <%--<em class="icon-notebook"></em>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<!-- END Contacts menu-->--%>
            </ul>
            <!-- END Right Navbar-->
        </div>
        <!-- END Nav wrapper-->
        <!-- START Search form-->
        <form role="search" action="search.html" class="navbar-form">
            <div class="form-group has-feedback">
                <input type="text" placeholder="Type and hit enter ..." class="form-control">

                <div data-search-dismiss="" class="fa fa-times form-control-feedback"></div>
            </div>
            <button type="submit" class="hidden btn btn-default">Submit</button>
        </form>
        <!-- END Search form-->
    </nav>
    <!-- END Top Navbar-->
</header>
<!-- sidebar-->
<aside class="aside">
    <!-- START Sidebar (left)-->
    <div class="aside-inner">
        <nav data-sidebar-anyclick-close="" class="sidebar">
            <!-- START sidebar nav-->
            <ul class="nav">
                <!-- START user info-->
                <li class="has-user-block">
                    <div id="user-block" class="collapse">
                        <div class="item user-block">
                            <!-- User picture-->
                            <div class="user-block-picture">
                                <div class="user-block-status">
                                    <img src="${ctx}/static/img/user/02.jpg" alt="Avatar" width="60" height="60"
                                         class="img-thumbnail img-circle">

                                    <div class="circle circle-success circle-lg"></div>
                                </div>
                            </div>
                            <!-- Name and Job-->
                            <div class="user-block-info">
                                <span class="user-block-name">您好,${ sessionScope.session_login_user_key.nickname }</span>
                                <span class="user-block-role">Designer</span>
                            </div>
                        </div>
                    </div>
                </li>
                <!-- END user info-->
                <!-- Iterates over all sidebar items-->
                <li class="nav-heading ">
                    <span data-localize="sidebar.heading.COMPONENTS">系统菜单</span>
                </li>
                <c:forEach items="${menus}" var="menu" varStatus="status">
                    <li class="${menu.id eq activeRootMenuId ? 'active' : ''}">
                        <a href="#parent_menu_${status.index}" title="Forms" data-toggle="collapse">
                            <em class="${menu.icon}"></em>
                            <span data-localize="sidebar.nav.form.FORM">${menu.name}</span>
                        </a>
                        <ul id="parent_menu_${status.index}" class="nav sidebar-subnav collapse">
                            <li class="sidebar-subnav-header">${menu.name}</li>
                            <c:forEach items="${childMenus}" var="childMenu">
                                <c:if test="${childMenu.parentMenu.id eq menu.id}">
                                    <li class="${childMenu.id eq activeMenuId ? 'active' : ''}">
                                        <a href="${ctx}/${childMenu.url}" title="${childMenu.name}">
                                            <span data-localize="sidebar.nav.form.STANDARD">${childMenu.name}</span>
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </c:forEach>
            </ul>
            <!-- END sidebar nav-->
        </nav>
    </div>
    <!-- END Sidebar (left)-->
</aside>
<!-- offsidebar-->
<aside class="offsidebar" style="display: none">
    <!-- START Off Sidebar (right)-->
    <nav>
        <div role="tabpanel">
            <!-- Nav tabs-->
            <ul role="tablist" class="nav nav-tabs nav-justified">
                <li role="presentation" class="active">
                    <a href="#app-settings" aria-controls="app-settings" role="tab" data-toggle="tab">
                        <em class="icon-equalizer fa-lg"></em>
                    </a>
                </li>
                <li role="presentation">
                    <a href="#app-chat" aria-controls="app-chat" role="tab" data-toggle="tab">
                        <em class="icon-users fa-lg"></em>
                    </a>
                </li>
            </ul>
            <!-- Tab panes-->
            <div class="tab-content">
                <div id="app-settings" role="tabpanel" class="tab-pane fade in active">
                    <h3 class="text-center text-thin">Settings</h3>

                    <div class="p">
                        <h4 class="text-muted text-thin">Themes</h4>

                        <div class="table-grid mb">
                            <div class="col mb">
                                <div class="setting-color">
                                    <label data-load-css="${ctx}/static/css/theme-a.css">
                                        <input type="radio" name="setting-theme" checked="checked">
                                        <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-info"></span>
                                       <span class="color bg-info-light"></span>
                                    </span>
                                        <span class="color bg-white"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col mb">
                                <div class="setting-color">
                                    <label data-load-css="${ctx}/static/css/theme-b.css">
                                        <input type="radio" name="setting-theme">
                                        <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-green"></span>
                                       <span class="color bg-green-light"></span>
                                    </span>
                                        <span class="color bg-white"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col mb">
                                <div class="setting-color">
                                    <label data-load-css="${ctx}/static/css/theme-c.css">
                                        <input type="radio" name="setting-theme">
                                        <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-purple"></span>
                                       <span class="color bg-purple-light"></span>
                                    </span>
                                        <span class="color bg-white"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col mb">
                                <div class="setting-color">
                                    <label data-load-css="${ctx}/static/css/theme-d.css">
                                        <input type="radio" name="setting-theme">
                                        <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-danger"></span>
                                       <span class="color bg-danger-light"></span>
                                    </span>
                                        <span class="color bg-white"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="table-grid mb">
                            <div class="col mb">
                                <div class="setting-color">
                                    <label data-load-css="${ctx}/static/css/theme-e.css">
                                        <input type="radio" name="setting-theme">
                                        <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-info-dark"></span>
                                       <span class="color bg-info"></span>
                                    </span>
                                        <span class="color bg-gray-dark"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col mb">
                                <div class="setting-color">
                                    <label data-load-css="${ctx}/static/css/theme-f.css">
                                        <input type="radio" name="setting-theme">
                                        <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-green-dark"></span>
                                       <span class="color bg-green"></span>
                                    </span>
                                        <span class="color bg-gray-dark"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col mb">
                                <div class="setting-color">
                                    <label data-load-css="${ctx}/static/css/theme-g.css">
                                        <input type="radio" name="setting-theme">
                                        <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-purple-dark"></span>
                                       <span class="color bg-purple"></span>
                                    </span>
                                        <span class="color bg-gray-dark"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col mb">
                                <div class="setting-color">
                                    <label data-load-css="${ctx}/static/css/theme-h.css">
                                        <input type="radio" name="setting-theme">
                                        <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-danger-dark"></span>
                                       <span class="color bg-danger"></span>
                                    </span>
                                        <span class="color bg-gray-dark"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="p">
                        <h4 class="text-muted text-thin">Layout</h4>

                        <div class="clearfix">
                            <p class="pull-left">Fixed</p>

                            <div class="pull-right">
                                <label class="switch">
                                    <input id="chk-fixed" type="checkbox" data-toggle-state="layout-fixed">
                                    <span></span>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix">
                            <p class="pull-left">Boxed</p>

                            <div class="pull-right">
                                <label class="switch">
                                    <input id="chk-boxed" type="checkbox" data-toggle-state="layout-boxed">
                                    <span></span>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix">
                            <p class="pull-left">RTL</p>

                            <div class="pull-right">
                                <label class="switch">
                                    <input id="chk-rtl" type="checkbox">
                                    <span></span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="p">
                        <h4 class="text-muted text-thin">Aside</h4>

                        <div class="clearfix">
                            <p class="pull-left">Collapsed</p>

                            <div class="pull-right">
                                <label class="switch">
                                    <input id="chk-collapsed" type="checkbox" data-toggle-state="aside-collapsed">
                                    <span></span>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix">
                            <p class="pull-left">Float</p>

                            <div class="pull-right">
                                <label class="switch">
                                    <input id="chk-float" type="checkbox" data-toggle-state="aside-float">
                                    <span></span>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix">
                            <p class="pull-left">Hover</p>

                            <div class="pull-right">
                                <label class="switch">
                                    <input id="chk-hover" type="checkbox" data-toggle-state="aside-hover">
                                    <span></span>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="app-chat" role="tabpanel" class="tab-pane fade">
                    <h3 class="text-center text-thin">Connections</h3>
                    <ul class="nav">
                        <!-- START list title-->
                        <li class="p">
                            <small class="text-muted">ONLINE</small>
                        </li>
                        <!-- END list title-->
                        <li>
                            <!-- START User status-->
                            <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-success circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="${ctx}/static/img/user/05.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                                <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Juan Sims</strong>
                                    <br>
                                    <small class="text-muted">Designeer</small>
                                 </span>
                              </span>
                            </a>
                            <!-- END User status-->
                            <!-- START User status-->
                            <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-success circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="${ctx}/static/img/user/06.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                                <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Maureen Jenkins</strong>
                                    <br>
                                    <small class="text-muted">Designeer</small>
                                 </span>
                              </span>
                            </a>
                            <!-- END User status-->
                            <!-- START User status-->
                            <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-danger circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="${ctx}/static/img/user/07.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                                <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Billie Dunn</strong>
                                    <br>
                                    <small class="text-muted">Designeer</small>
                                 </span>
                              </span>
                            </a>
                            <!-- END User status-->
                            <!-- START User status-->
                            <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-warning circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="${ctx}/static/img/user/08.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                                <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Tomothy Roberts</strong>
                                    <br>
                                    <small class="text-muted">Designer</small>
                                 </span>
                              </span>
                            </a>
                            <!-- END User status-->
                        </li>
                        <!-- START list title-->
                        <li class="p">
                            <small class="text-muted">OFFLINE</small>
                        </li>
                        <!-- END list title-->
                        <li>
                            <!-- START User status-->
                            <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="${ctx}/static/img/user/09.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                                <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Lawrence Robinson</strong>
                                    <br>
                                    <small class="text-muted">Developer</small>
                                 </span>
                              </span>
                            </a>
                            <!-- END User status-->
                            <!-- START User status-->
                            <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="${ctx}/static/img/user/10.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                                <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Tyrone Owens</strong>
                                    <br>
                                    <small class="text-muted">Designer</small>
                                 </span>
                              </span>
                            </a>
                            <!-- END User status-->
                        </li>
                        <li>
                            <div class="p-lg text-center">
                                <!-- Optional link to list more users-->
                                <a href="#" title="See more contacts" class="btn btn-purple btn-sm">
                                    <strong>Load more..</strong>
                                </a>
                            </div>
                        </li>
                    </ul>
                    <!-- Extra items-->
                    <div class="p">
                        <p>
                            <small class="text-muted">Tasks completion</small>
                        </p>
                        <div class="progress progress-xs m0">
                            <div role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
                                 class="progress-bar progress-bar-success progress-80">
                                <span class="sr-only">80% Complete</span>
                            </div>
                        </div>
                    </div>
                    <div class="p">
                        <p>
                            <small class="text-muted">Upload quota</small>
                        </p>
                        <div class="progress progress-xs m0">
                            <div role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
                                 class="progress-bar progress-bar-warning progress-40">
                                <span class="sr-only">40% Complete</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <!-- END Off Sidebar (right)-->
</aside>
<!-- Main section-->