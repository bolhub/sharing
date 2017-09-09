<%--
  Created by IntelliJ IDEA.
  User: bolom
  Date: 2017/8/4
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>Title</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" href="<%=basePath%>css/jquery.searchableSelect.css" type="text/css">
<%--<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,700,900|Roboto+Condensed:400,300,700' rel='stylesheet' type='text/css'>--%>
<%--<link rel="stylesheet" type="text/css" href="<%=basePath%>css/normalize.css" />--%>
<%--<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">--%>
<%--<link rel="stylesheet" href="<%=basePath%>css/demo.css">--%>
<link rel="stylesheet" href="<%=basePath%>css/dropify.min.css">
<!--[if IE]>
<script src="http://cdn.bootcss.com/html5shiv/3.7/html5shiv.min.js"></script>
<![endif]-->

<link rel="stylesheet" href="<%=basePath%>css/upload.css" type="text/css">

<style>
    .doc-icon {
        display: inline-block;
        background: url("/css/images/png/doc_72px.png") no-repeat;
        background-size: 58px 71px;
        width: 58px;
        height: 71px;
    }

    .uploader-list {
        height: 150px;
        z-index: -4;
    }

    .searchable-select {
        z-index: 1;
    }

    .searchable-select-items {
        max-height: 100px;
    }

    .dpt-contain {
        z-index: 1000;
    }

    .file-contain {
        z-index: 100;
    }

    .dpt-contain .searchable-select{
        z-index: 900;
    }

    .edu-contain .searchable-select {
        z-index: 1000;
    }

</style>
<section>
    <div class="col-lg-8 col-lg-offset-2">
        <div class="page-header">
            <h2>简历信息</h2>
        </div>
    </div>
    <%--<div id="resume-upload" class="form-horizontal">--%>
    <%--<div class="form-group"><label class="col-lg-3 control-label">姓名</label>--%>
    <%--<div class="col-lg-5"><input name="resumeName" type="text" class="form-control"></div>--%>
    <%--</div>--%>
    <%--<div class="form-group"><label class="col-lg-3 control-label">学历</label>--%>
    <%--<div class="col-lg-5"><input name="resumeEducation" type="text" class="form-control"></div>--%>
    <%--</div>--%>
    <%--<div class="form-group"><label class="col-lg-3 control-label">专业</label>--%>
    <%--<div class="col-lg-2"><input name="resumeMajor" type="text" class="form-control"></div>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
    <%--<label class="col-lg-3 control-label">毕业时间</label>--%>
    <%--<div class="col-lg-2"><input name="resumeGraduate" type="date" class="form-control"></div>--%>
    <%--</div>--%>
    <%--<div class="form-group"><label class="col-lg-3 control-label">单位编制</label>--%>
    <%--<div class="col-lg-5">--%>
    <%--<select name="resumeDpt">--%>
    <%--<option>测试</option>--%>
    <%--</select>--%>
    <%--&lt;%&ndash;<input name="resumeCompany" type="" class="form-control">&ndash;%&gt;</div>--%>
    <%--</div>--%>
    <%--<div class="form-group"><label class="col-lg-3 control-label">简历附件</label>--%>
    <%--<div class="col-lg-4"><label id="thelist" type="" class="form-control uploader-list"></label></div>--%>
    <%--<div class="col-lg-2"><span id="picker">浏览</span></div>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
    <%--<div class="col-lg-9  col-lg-offset-4">--%>
    <%--<button class="btn btn-primary" type="button" id="ctlBtn">上传简历</button>--%>
    <%--<button class="btn btn-info" type="reset" id="reset">重置</button>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <form id="resumeForm" class="form-horizontal" action="resume/uploadRes" enctype="multipart/form-data" method="post">
        <div class="form-group"><label for="" class="col-lg-3 control-label">姓名</label>
            <div class="col-lg-5"><input name="resName" type="text" class="form-control"></div>
        </div>
        <div class="form-group">
            <label for="" class="col-lg-3 control-label">姓别</label>
            <div class="col-lg-5">
                <div class="radio col-lg-3">
                    <label for=""><input type="radio" name="gender" value="MALE" checked>男</label>
                </div>
                <div class="radio col-lg-3">
                    <label for=""><input type="radio" name="gender" value="FEMALE">女</label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-lg-3 control-label">公开简历</label>
            <div class="col-lg-5">
                <div class="radio col-lg-3">
                    <label for=""><input type="radio" name="privilege" value="COMMONABLE" checked>是</label>
                </div>
                <div class="radio col-lg-3">
                    <label for=""><input type="radio" name="privilege" value="PERSONAL">否</label>
                </div>
            </div>
        </div>

        <div class="form-group"><label for="" class="col-lg-3 control-label">专业</label>
            <div class="col-lg-5">
                <div class="radio col-lg-3" readonly>
                    <label><input type="radio" name="major" value="计算机" checked>计算机</label>
                </div>
                <div class="radio col-lg-5">
                    <label><input type="radio" name="major" value="非计算机理工">非计算机理工</label>
                </div>
                <div class="radio col-lg-3">
                    <label><input type="radio" name="major" value="其它">其它</label>
                </div>
            </div>
        </div>
        <div class="form-group"><label for="" class="col-lg-3 control-label">毕业时间</label>
            <div class="col-lg-4"><input name="graduateTime" type="date" class="form-control"></div>
        </div>
        <div class="form-group edu-contain"><label for="" class="col-lg-3 control-label">学历</label>
            <div class="col-lg-5">
                <select name="education">
                    <option value="本科">本科</option>
                    <option value="大专">大专</option>
                    <option value="高中">高中</option>
                    <option value="中专">中专</option>
                    <option value="初中">初中</option>
                </select>
            </div>
        </div>
        <div class="form-group dpt-contain"><label for="" class="col-lg-3 control-label">单位编制</label>
            <div class="col-lg-5">
                <select name="resumeDpt">
                    <option>测试</option>
                </select>
            </div>
        </div>
        <div class="form-group file-contain">
            <label for="" class="col-lg-3 control-label">上传简历</label>
            <div class="col-lg-5">
                <input type="file" name="resumeFile" id="input-file-now" class="dropify" data-default-file="" />
            </div>
        </div>
        <%--<div class="form-group">--%>
            <%--<label for="" class="col-lg-3 control-label"></label>--%>
            <%--<div class="col-lg-5"><input type="file" name="file_upload" id="file_upload"></div>--%>
        <%--</div>--%>
        <div class="form-group">
            <div class="col-lg-9  col-lg-offset-4">
                <button class="btn btn-primary" type="button" id="ctlBtn">上传简历</button>
                <button class="btn btn-info" type="reset" id="reset">重置</button>
            </div>
        </div>


    </form>
</section>


<script type="text/javascript" src="<%=basePath%>webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.uploadify.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.searchableSelect.js"></script>
<script type="text/javascript" src="<%=basePath%>js/addResume.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>js/flexpaper_flash.js"></script>--%>
<%--<script type="text/javascript" src="<%=basePath%>js/flexpaper_flash_debug.js"></script>--%>
<%--<script type="text/javascript" src="<%=basePath%>js/swfobject.js"></script>--%>
<script type="text/javascript" src="<%=basePath%>js/dropify.min.js"></script>
