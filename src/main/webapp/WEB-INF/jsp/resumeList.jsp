<%--
  Created by IntelliJ IDEA.
  User: bolom
  Date: 2017/8/8
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style type="text/css" rel="stylesheet">
        /*.box{*/
            /*position: absolute;*/
            /*right: 60px;*/
        /*}*/
        /*.box li{*/
            /*list-style: none;*/
            /*display: inline-block;*/
            /*padding: 5px 5px;*/
            /*margin: 5px 2px;*/
            /*border: 1px solid #cff6f4;*/
            /*border-radius: 5px;*/
        /*}*/
        /*.box li.active{*/
            /*border-color: #ebb822;*/
            /*background-color: blanchedalmond;*/
        /*}*/
        .resume-find div{
            margin-top: 5px;
        }
        /*.resume-find div:first-child input{*/
            /*width: 130px;*/
        /*}*/
        /*.find-op{*/
            /*display: inline-block;*/
            /*width: 80px;*/
        /*}*/
        .resume-search-btn span {
            margin-right: 5px;
        }
        .resume-find>div{
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div>
        <div class="resume-find">
            <div class="input-group input-group-lg">
                <input type="text" class="form-control keyword-input" placeholder="搜索简历信息(姓名、学历、专业、人员属地、项目名称、项目属地)">
                <span class="input-group-addon resume-search-btn">
                    <span class="glyphicon glyphicon-search"></span>
                </span>
            </div>
            <div class="input-group col-md-5"><span class="find-op input-group-addon">From</span><input type="date" class="from-input form-control"><span class="input-group-addon">To</span><input type="date" class="to-input form-control"></div>
            <%--<div><span class="find-op">学历</span><input type="text" class="education-input"></div>--%>
            <%--<div><span class="find-op">入场项目</span><input type="text" class="project-input"></div>--%>
            <%--<div><span class="find-op">姓名</span><input type="text" class="owner-input"></div>--%>
            <%--<div>--%>
                <%--<button type="button" class="btn btn-default resume-search-btn"><span class="glyphicon glyphicon-search"></span>简历搜索</button>--%>
            <%--</div>--%>
        </div>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <td width="4%">编号</td>
            <td width="7%">姓名</td>
            <td width="6%">学历</td>
            <td width="8%">专业</td>
            <td width="8%">毕业时间</td>
            <td width="8%">上传时间</td>
            <td width="10%">人员编制</td>
            <td width="10%">项目名称</td>
            <td width="10%">项目编制</td>
            <td width="5%">操作</td>
        </tr>
        </thead>
        <tbody class="resume-list">
        </tbody>
    </table>
    <ul class="box"></ul>
</body>

<script type="text/javascript" src="<%=basePath%>js/jqPaginator.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>js/jquery.pagination.js"></script>--%>
<script type="text/javascript" src="<%=basePath%>js/resumeList.js"></script>
<script>
</script>
</html>
