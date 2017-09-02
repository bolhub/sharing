<%--
  Created by IntelliJ IDEA.
  User: qi
  Date: 2017/8/3
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>联龙博通简历系统</title>
    <link href="<%=path%>js/bootstrap-3.3.5-dist/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="<%=path%>css/bootstrapValidator.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>webuploader-0.1.5/webuploader.css">
    <style>
        body {
            background: url("<%=basePath%>css/images/bg/bg.jpg") no-repeat;
            background-size: 100%;
        }
    </style>
</head>
<body>
<div class="login-contain col-lg-8 col-lg-offset-2">
    <div class="page-header">
        <h2>登录</h2>
    </div>
    <form action="<%=basePath%>home" class="form-horizontal" method="post">
        <div class="form-group"><label class="col-lg-3 control-label">登录帐号</label>
            <div class="col-lg-5"><input type="text" name="acct" class="form-control"></div>
        </div>
        <div class="form-group"><label class="col-lg-3 control-label">登录密码</label>
            <div class="col-lg-5"><input type="password" name="pwd" class="form-control"></div>
        </div>
        <div class="form-group">
            <div class="col-lg-9 col-lg-offset-4">
                <button type="submit" class="btn btn-primary">登录</button>
                <button type="button" class="btn btn-danger" name="goSignUp">注册</button>
            </div>
        </div>
    </form>
</div>
<div id="register-contain" class="register-contain col-lg-8 col-lg-offset-2" hidden="hidden">
    <div class="page-header">
        <h2>注册</h2>
    </div>
    <div class="form-horizontal">
        <div class="form-group"><label class="col-lg-3 control-label">帐号</label>
            <div class="col-lg-5"><input type="text" class="form-control" name="account"></div>
        </div>
        <div class="form-group"><label class="col-lg-3 control-label">姓名</label>
            <div class="col-lg-5"><input type="text" class="form-control" name="username"></div>
        </div>
        <div class="form-group"><label class="col-lg-3 control-label">密码</label>
            <div class="col-lg-5"><input type="text" class="form-control" name="password"></div>
        </div>
        <div class="form-group"><label class="col-lg-3 control-label">确认密码</label>
            <div class="col-lg-5"><input type="text" class="form-control" name="confirmPassword"></div>
        </div>
        <div class="form-group">
            <div class="col-lg-9 col-lg-offset-4">
                <button class="btn btn-primary" name="signUp">提交注册</button>
                <button class="btn btn-danger" name="backLogin">返回登录</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common.jsp"></jsp:include>
</body>
<script type="text/javascript" src="<%=basePath%>js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap-3.3.5-dist/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/login.js"></script>
<script>
    var host = '<%=basePath%>';

</script>
</html>
