<%--
  Created by IntelliJ IDEA.
  User: bolom
  Date: 2017/9/2
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    #tipBox {
        position: fixed;
        bottom: 20px;
        right: 30px;
        min-width: 300px;
        min-height: 120px;
    }
</style>
<!-- 消信提示框-->
<div id="tipBox" class="panel panel-default" hidden="hidden">
    <div class="panel-heading">
        <div class="panel-title">
            <span class="glyphicon glyphicon-info-sign"></span>
            <strong>提示</strong>
            <span class="close">&times;</span>
        </div>
    </div>
    <div class="panel-body"></div>
</div>
<!-- 模态框 -->
<div class="modal fade noticeModal" id="tipMod" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Notice</h4>
            </div>
            <div class="modal-body">text</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="sure-btn">确定</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=basePath%>js/common.js"></script>

