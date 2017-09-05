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
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>webuploader-0.1.5/webuploader.css">
    <link rel="stylesheet" href="<%=basePath%>css/jquery.searchableSelect.css" type="text/css">
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
        .searchable-select{
            z-index: 1;
        }
        .searchable-select-items{
            max-height: 150px;
        }

    </style>
    <%--<style type="text/css" media="screen">--%>
        <%--#flashContent {--%>
            <%--display: none;--%>
        <%--}--%>
    <%--</style>--%>
</head>
<body>
<%--<div>上传简历</div>--%>
<section>
    <div class="col-lg-8 col-lg-offset-2">
        <div class="page-header">
            <h2>简历信息</h2>
        </div>
    </div>
    <div id="resume-upload" class="form-horizontal">
        <div class="form-group"><label class="col-lg-3 control-label">姓名</label>
            <div class="col-lg-5"><input name="resumeName" type="text" class="form-control"></div>
        </div>
        <div class="form-group"><label class="col-lg-3 control-label">学历</label>
            <div class="col-lg-5"><input name="resumeEducation" type="text" class="form-control"></div>
        </div>
        <div class="form-group"><label class="col-lg-3 control-label">专业</label>
            <div class="col-lg-2"><input name="resumeMajor" type="text" class="form-control"></div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">毕业时间</label>
            <div class="col-lg-2"><input name="resumeGraduate" type="date" class="form-control"></div>
        </div>
        <div class="form-group"><label class="col-lg-3 control-label">单位编制</label>
            <div class="col-lg-5">
                <select name="resumeDpt">
                    <option>测试</option>
                </select>
                <%--<input name="resumeCompany" type="" class="form-control">--%></div>
        </div>
        <div class="form-group"><label class="col-lg-3 control-label" >简历附件</label>
            <div class="col-lg-4"><label id="thelist" type="" class="form-control uploader-list"></label></div>
            <div class="col-lg-2"><span id="picker">浏览</span></div>
        </div>
        <div class="form-group">
            <div class="col-lg-9  col-lg-offset-4">
                <button class="btn btn-primary" type="button" id="ctlBtn">上传简历</button>
                <button class="btn btn-info" type="reset" id="reset">重置</button>
            </div>
        </div>
    </div>

</section>

<%--<div class="resume-options">--%>
<%--<div><span>姓名</span><input class="resume-name form-control" type="text"></div>--%>
<%--<div><span>学历</span><input class="resume-education form-control" type="text"></div>--%>
<%--<div><span>毕业时间</span><input class="resume-graduate form-control" type="date"></div>--%>
<%--&lt;%&ndash;<div><span>中心</span><input class="resume-center form-control" type="text"></div>&ndash;%&gt;--%>
<%--<div><span>中心</span>--%>
<%--<select name="" class="form-control">--%>
<%--<option value="">1</option>--%>
<%--<option value="">2</option>--%>
<%--<option value="">3</option>--%>
<%--<option value="">4</option>--%>
<%--<option value="">5</option>--%>
<%--</select>--%>
<%--</div>--%>
<%--<div><span>入场项目</span>--%>
<%--<select name="" class="form-control">--%>
<%--<option value="">1</option>--%>
<%--<option value="">2</option>--%>
<%--<option value="">3</option>--%>
<%--<option value="">4</option>--%>
<%--<option value="">5</option>--%>
<%--</select>--%>
<%--&lt;%&ndash;<input class="resume-project form-control" type="text">&ndash;%&gt;--%>
<%--</div>--%>
<%--</div>--%>
<%--<div id="uploader" class="wu-example">--%>
<%--<!--用来存放文件信息-->--%>
<%--<div id="thelist" class="uploader-list"></div>--%>
<%--<div class="btns">--%>
<%--<div id="picker"><span class="glyphicon glyphicon-folder-open"></span>选择简历</div>--%>
<%--<button id="ctlBtn" class="btn btn-default"><span class="glyphicon glyphicon-cloud-upload"></span>上传提交</button>--%>
<%--</div>--%>

<%--</div>--%>

<div style="position: absolute; left: 50px; top: 10px;">
    <a id="viewerPlaceHolder" style="width: 820px; height: 650px; display: block"></a>
    <div id="documentViewer" style="width: 820px; height: 650px; display: block"></div>
    <script type="text/javascript" src="<%=basePath%>js/flexpaper_flash.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/flexpaper_flash_debug.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/swfobject.js"></script>
    <script type="text/javascript">


        $.ajax({
            url: host + 'resume/previewDoc',
            type: 'GET',
            success : function (result) {
                if (!result)
                    return;
                var data = JSON.parse(result);
                console.log(data.msg.replace("//","/"))
                var fp = new FlexPaperViewer(
                    'FlexPaperViewer',
                    'viewerPlaceHolder', { config : {
//                SwfFile : escape(host + "js/swf/test.swf"),//编码设置
                        SwfFile : host + data.msg.replace("//","/"),//编码设置
                        Scale : 0.6,
                        ZoomTransition : 'easeOut',//变焦过渡
                        ZoomTime : 0.5,
                        ZoomInterval : 0.2,//缩放滑块-移动的缩放基础[工具栏]
                        FitPageOnLoad : true,//自适应页面
                        FitWidthOnLoad : true,//自适应宽度
                        FullScreenAsMaxWindow : false,//全屏按钮-新页面全屏[工具栏]
                        ProgressiveLoading : false,//分割加载
                        MinZoomSize : 0.2,//最小缩放
                        MaxZoomSize : 3,//最大缩放
                        SearchMatchAll : true,
                        InitViewMode : 'Portrait',//初始显示模式(SinglePage,TwoPage,Portrait)

                        ViewModeToolsVisible : true,//显示模式工具栏是否显示
                        ZoomToolsVisible : true,//缩放工具栏是否显示
                        NavToolsVisible : true,//跳页工具栏
                        CursorToolsVisible : false,
                        SearchToolsVisible : true,
                        PrintPaperAsBitmap:false,
                        localeChain: 'en_US'
                    }});
            }
        })
        var fp = new FlexPaperViewer(
            'FlexPaperViewer',
            'viewerPlaceHolder', { config : {
//                SwfFile : escape(host + "js/swf/test.swf"),//编码设置
                SwfFile : host + "test.swf",//编码设置
                Scale : 0.6,
                ZoomTransition : 'easeOut',//变焦过渡
                ZoomTime : 0.5,
                ZoomInterval : 0.2,//缩放滑块-移动的缩放基础[工具栏]
                FitPageOnLoad : true,//自适应页面
                FitWidthOnLoad : true,//自适应宽度
                FullScreenAsMaxWindow : false,//全屏按钮-新页面全屏[工具栏]
                ProgressiveLoading : false,//分割加载
                MinZoomSize : 0.2,//最小缩放
                MaxZoomSize : 3,//最大缩放
                SearchMatchAll : true,
                InitViewMode : 'Portrait',//初始显示模式(SinglePage,TwoPage,Portrait)

                ViewModeToolsVisible : true,//显示模式工具栏是否显示
                ZoomToolsVisible : true,//缩放工具栏是否显示
                NavToolsVisible : true,//跳页工具栏
                CursorToolsVisible : false,
                SearchToolsVisible : true,
                PrintPaperAsBitmap:false,
                localeChain: 'en_US'
            }});

//$('#documentViewer').FlowPaperViewer(
//    { config : {
//        SwfFile : host +"js/swf/test.swf",
//        IMGFiles : "Paper.pdf_{page}.png",
//        JSONFile : "Paper.pdf.js",
//        PDFFile : "Paper.pdf",
//        Scale : 0.6,
//        ZoomTransition : "easeOut",
//        ZoomTime : 0.5,
//        ZoomInterval : 0.1,
//        FitPageOnLoad : false,
//        FitWidthOnLoad : false,
//        FullScreenAsMaxWindow : true,
//        ProgressiveLoading : true,
//        MinZoomSize : 0.2,
//        MaxZoomSize : 5,
//        SearchMatchAll : false,
//        InitViewMode : 'Portrait',
//
//        ViewModeToolsVisible : true,
//        ZoomToolsVisible : true,
//        NavToolsVisible : true,
//        CursorToolsVisible : true,
//        SearchToolsVisible : true,
//
//        localeChain : "en_US"
//    }});
    </script>
</div>

</body>
<script type="text/javascript" src="<%=basePath%>webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.searchableSelect.js"></script>
<script type="text/javascript" src="<%=basePath%>js/addResume.js"></script>

<%--<script>--%>
    <%--$(function(){--%>
        <%--$('select').searchableSelect(function () {--%>
            <%--$("[name='resumeDpt']").append('<option>hello</option>')--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>

<script type="text/javascript" src="<%=basePath%>js/flexpaper_flash.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexpaper_flash_debug.js"></script>
<script type="text/javascript" src="<%=basePath%>js/swfobject.js"></script>
<%--</html>--%>
