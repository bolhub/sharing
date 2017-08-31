<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/24
  Time: 14:14
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
    <style>
        .roadmap-item {
            display: inline-block;
            width: calc((100% - 70px) / 9 - 6px);
            height: 100px;
            border: 3px solid transparent;
            text-align: center;
        }

        .roadmap-title {
            display: inline-block;
            width: 100%;
            height: 20px;
            overflow: hidden;
        }

        .roadmap-ico {
            display: inline-block;
            width: 72px;
            height: 72px;
            background: url("<%=basePath%>css/images/png/goverment_72px.png") no-repeat;
            background-size: 72px 72px;
        }

        #pro-center .roadmap-ico {
            background: url("<%=basePath%>css/images/png/task_x72.png") no-repeat;
        }

        #worker-center .roadmap-ico {
            background: url("<%=basePath%>css/images/png/man_72px.png") no-repeat;
        }

        .roadmap-item:hover {
            animation: border-state 0.5s linear;
            border: 3px solid rgba(23, 44, 22, 0.2);
            box-shadow: 0px 0px 7px rgba(0, 0, 0, 0.4) inset, 0px 0px 4px rgba(225, 225, 225, 0.1);
        }

        @-webkit-keyframes border-state {
            from {
                border: 3px solid transparent;
                /*box-shadow: 0px 0px 7px rgba(225, 225, 225, 0.1);*/
                box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.2) inset, 0px 0px 6px rgba(225, 225, 225, 0.3);
            }
            to {
                border: 3px solid rgba(23, 44, 22, 0.2);
                /*box-shadow: 0px 0px 4px;*/
                box-shadow: 0px 0px 7px rgba(0, 0, 0, 0.4) inset, 0px 0px 4px rgba(225, 225, 225, 0.1);
            }
        }

        .roadmap-item.active {
            background: rgba(23, 44, 22, 0.2);
        }

        #search {
            position: fixed;
            top: 100px;
        }

        .tar-center .panel-heading .glyphicon {
            padding: 0 10px;
        }

        .tar-center .panel-heading .glyphicon:hover {
            color: rgba(99, 3, 22, 0.8);
            cursor: pointer;
        }

        .tar-center {
            position: relative;
        }

        .tar-center .panel-body div {
            z-index: 100;
        }

        .tar-center .panel-body div:first-child {
            margin-left: 35px;
        }

        .ctr-box {
            position: relative;
        }

        .tar-center .prev, .tar-center .next {
            list-style: none;
            position: absolute;
            bottom: 30px;
            height: 80px;
            border-radius: 25px;
            line-height: 80px;
            display: none;
            text-align: center;
            width: 20px;
            font-size: 20px;
            font-weight: 900;
            background: rgba(33, 33, 33, 0.2);
            color: white;
        }

        .tar-center:hover .prev, .tar-center:hover .next {
            display: inline-block;
        }

        .tar-center .prev:hover, .tar-center .next:hover {
            background: rgba(33, 33, 33, 0.5);
        }

        .tar-center .prev {
            left: 5px;
        }

        .tar-center .next {
            right: 5px;
        }

        strong .badge {
            position: relative;
            bottom: 4px;
        }

        .worker-list-item {
            display: inline-block;
            width: calc(12.5% - 12px);
            margin: 5px;
            padding: 5px;
            border: 1px solid rgba(7, 8, 8, 0.18);
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
        }

        .worker-list-item p span:first-child {
            margin-left: 5px;
        }

        .worker-list-item p span:last-child {
            display: inline-block;
            width: calc(100% - 20px);
            text-align: center;
            padding-left: 3px;
            overflow: hidden;
        }

        .worker-list-item:hover {
            border: 1px solid cadetblue;
        }

        .worker-list-item.select {
            border: 1px solid cadetblue;
            background: rgba(53, 157, 160, 0.39);
        }

        .box.worker-page-box {
            position: relative;
            left: calc(100% - 300px);
        }

        .roadmap-item{
            position: relative;
        }
        .roadmap-item .glyphicon-edit {
            display: none;
            font-size: xx-small;
        }

        .roadmap-item .glyphicon-edit:hover {
            color: #995048;
        }

        .roadmap-item:hover .glyphicon-edit {
            display: inline-block;
            animation: del-action 0.5s;
        }

        @-webkit-keyframes del-action {
            from {
                transform: scale(0.5);
            }
            to {
                transform: scale(1);
            }
        }

        @-moz-keyframes del-action {
            from {
                transform: scale(0.5);
            }
            to {
                transform: scale(1);
            }
        }

        @-ms-keyframes del-action {
            from {
                transform: scale(0.5);
            }
            to {
                transform: scale(1);
            }
        }

        @-o-keyframes del-action {
            from {
                transform: scale(0.5);
            }
            to {
                transform: scale(1);
            }
        }

        #wkAddMod .modal-dialog{
            width: 80%;
        }

        #wkAddMod .glyphicon{
            color: #00a2d4;
        }

    </style>
</head>
<body>
<div class="panel panel-default tar-center" id="ctr-center">
    <div class="panel-heading">
        <h3 class="panel-title">
            <strong>中心<span class="badge"></span></strong>
            <span class="pull-right glyphicon glyphicon-plus"></span>
            <span class="pull-right glyphicon glyphicon-search" search-content="中心"></span>
        </h3>
    </div>
    <div class="panel-body">
        <h5>未选择中心</h5>
    </div>
    <div class="ctr-box"></div>
</div>

<div class="panel panel-default tar-center" id="pro-center">
    <div class="panel-heading">
        <h3 class="panel-title">
            <strong>项目<span class="badge"></span></strong>
            <span class="process"></span>
            <span class="pull-right glyphicon glyphicon-plus"></span>
            <span class="pull-right glyphicon glyphicon-search" search-content="项目"></span>
        </h3>
    </div>
    <div class="panel-body">
        <h5>未选择中心</h5>
    </div>
    <div class="pro-box"></div>
</div>

<div class="panel panel-default tar-center" id="worker-center">
    <div class="panel-heading">
        <h3 class="panel-title">
            <strong>人员<span class="badge"></span></strong>
            <span class="process"></span><span class="process"></span>
            <span class="pull-right glyphicon glyphicon-plus"></span>
            <span class="pull-right glyphicon glyphicon-search" search-content="人员"></span>
        </h3>
    </div>
    <div class="panel-body">
        <h5>未选择项目</h5>
    </div>
    <div class="res-box"></div>
</div>

<div class="input-group col-lg-5 col-lg-offset-2" id="search">
    <span class="input-group-addon"><strong>中心</strong></span>
    <input class="form-control input-lg" type="search" placeholder="搜索关键字">
    <span class="input-group-addon btn btn-default"><span class="glyphicon glyphicon-search"></span></span>
</div>

<div id="alertModal" class="modal fade" tabindex="-1" role="alertdialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">添加中心</h4>
            </div>
            <div class="modal-body">
                <div class="input-group">
                    <label class="input-group-addon label-name">中心名称</label>
                    <input type="text" class="form-control" name="checkName">
                </div>
                <small class="help-block"></small>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal">关闭</button>
                <button class="btn btn-primary commit-btn" type="button">提交</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" role="dialog" tabindex="-1" id="wkAddMod">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">选择人员</h4>
            </div>
            <div class="modal-body">
                <div class="resume-find">
                    <div class="input-group input-group-lg">
                        <input type="text" class="form-control keyword-input"
                               placeholder="姓名、学历、专业、人员属地">
                        <span class="input-group-addon resume-search-btn">
                    <span class="glyphicon glyphicon-search"></span>
                </span>
                    </div>
                </div>
                <div class="worker-list">
                </div>
                <div class="box worker-page-box"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary">添加</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="./modify.jsp"></jsp:include>

</body>
<script type="text/javascript" src="<%=basePath%>js/jqPaginator.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ctrMange.js"></script>

</html>
