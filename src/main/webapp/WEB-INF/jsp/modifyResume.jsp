<%--
  Created by IntelliJ IDEA.
  User: bolom
  Date: 2017/8/8
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<%=basePath%>css/common.css" rel="stylesheet" type="text/css">
<div>
    <div class="resume-modify">
        <div class="input-group input-group-lg col-lg-4">
            <input type="text" class="form-control keyword" placeholder="简历信息">
            <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span></span>
        </div>
    </div>
</div>
<div class="modify-contain">
</div>
<ul class="box"></ul>

<script src="<%=basePath%>js/modifyResume.js"></script>
