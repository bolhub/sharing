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
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <div class="resume-del">
        <div class="input-group input-group-lg col-lg-4">
            <input type="text" class="form-control" placeholder="简历信息">
            <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span></span>
        </div>
    </div>
</div>
<table class="table table-striped table-hover table-condensed">
    <thead>
    <tr>
        <td class="col-md-1">编号</td>
        <td class="col-md-2">姓名</td>
        <td class="col-md-2">学历</td>
        <td class="col-md-2">毕业时间</td>
        <td class="col-md-2">上传时间</td>
        <td class="col-md-2">入场项目</td>
        <td class="col-md-1">操作</td>
    </tr>
    </thead>
    <tbody class="resume-list">
    <%--<tr>--%>
        <%--<td></td>--%>
        <%--<td></td>--%>
        <%--<td></td>--%>
        <%--<td></td>--%>
        <%--<td></td>--%>
        <%--<td></td>--%>
    <%--</tr>--%>
    </tbody>
</table>
<ul class="box"></ul>
</body>
<script src="<%=basePath%>js/modifyResume.js"></script>
</html>
