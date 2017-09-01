<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/31
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="<%=basePath%>css/jquery.searchableSelect.css" type="text/css">

<style>
    #modify .modal-body .form-group .glyphicon-pencil {
        position: relative;
        top: -25px;
        right: -20px;
    }

    #modify .modal-body .form-group .glyphicon-pencil:hover {
        animation: move-action 0.5s infinite;
    }

    @-webkit-keyframes move-action {
        0% {
            transform: rotate(0deg);
            color: #000;
        }
        50% {
            transform: rotate(-5deg);
        }
        100% {
            transform: rotate(0deg);
            color: #ac2925;
        }
    }
</style>

<div class="modal fade" id="modify" role="dialog" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <div class="modal-title">简历信息</div>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">
                    <div class="form-group"><label class="control-label col-lg-3">姓名</label>
                        <div class="col-lg-5"><input type="text" class="form-control"><span
                                class="glyphicon glyphicon-pencil pull-right"></span></div>
                        <div class="col-lg-5 update-tar" hidden><input type="text" class="form-control"></div>
                    </div>
                    <div class="form-group"><label class="control-label col-lg-3">学历</label>
                        <div class="col-lg-5">
                            <input type="text" class="form-control">
                            <span class="glyphicon glyphicon-pencil pull-right"></span>
                        </div>
                        <div class="col-lg-5 update-tar" hidden>
                            <select>
                                <option value="本科">本科</option>
                                <option value="大专">大专</option>
                                <option value="高中">高中</option>
                                <option value="中专">中专</option>
                                <option value="初中">初中</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group"><label class="control-label col-lg-3">专业</label>
                        <div class="col-lg-5">
                            <input type="text" class="form-control">
                            <span class="glyphicon glyphicon-pencil pull-right"></span>
                        </div>
                        <div class="col-lg-5 update-tar" hidden>
                            <div class="radio" readonly>
                                <label><input type="radio" name="major" value="计算机">计算机</label>
                            </div>
                            <div class="radio">
                                <label><input type="radio" name="major" value="非计算机理工">非计算机理工</label>
                            </div>
                            <div class="radio">
                                <label><input type="radio" name="major" value="其它">其它</label>
                            </div>
                            <%--<span class="glyphicon glyphicon-pencil pull-right"></span>--%>
                        </div>
                    </div>
                    <div class="form-group"><label class="control-label col-lg-3">毕业时间</label>
                        <div class="col-lg-5"><input type="date" class="form-control"><span
                                class="glyphicon glyphicon-pencil pull-right"></span></div>
                        <div class="col-lg-5 update-tar" hidden><input type="date" class="form-control"></div>
                    </div>
                    <div class="form-group"><label class="control-label col-lg-3">单位编制</label>
                        <div class="col-lg-5"><input type="text" class="form-control"><span
                                class="glyphicon glyphicon-pencil pull-right"></span></div>
                        <div class="col-lg-5 update-tar" hidden>
                            <select>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">取消</button>
                <button class="btn btn-primary">确定</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=basePath%>js/jquery.searchableSelect.js"></script>
