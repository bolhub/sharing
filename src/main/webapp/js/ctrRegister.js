// var ctrFn = {
//     url: {
//         dptCheck: host + 'organize/dpt/checkExist',
//         proCheck: host + 'organize/pro/checkExist',
//         dptAdd: host + 'organize/dpt/add',
//         proAdd: host + 'organize/pro/add'
//     },
//     initMsgBox: function (init) {
//         $("#alertModal .help-block").text('');
//         $("#alertModal [name='checkName']").val('');
//         if (init) {
//             if (init.title)
//                 $("#alertModal .modal-title").html(init.title);
//             if (init.text)
//                 $("#alertModal .modal-body").html(init.text);
//             if (init.notice)
//                 $("#alertModal .help-block").html(init.notice);
//         }
//     },
//     addOpt: function (options, callback) {
//         options.dom.unbind().bind("click", function () {
//             ctrFn.initMsgBox(options.init);
//             // $("#alertModal").modal();
//             tip.tipMod({mod:$("#alertModal")}, function () {
//                 tip.mod.find("[name='checkName']").unbind().bind("blur change", function () {
//                     if (!$(this).val()){
//                         // ctrFn.initMsgBox({
//                         //     notice: "数据为空"
//                         // })
//                         tip.mod.find(".help-block").html("数据为空");
//                         return;
//                     }
//                     util.post({
//                         url: options.ajax.url,
//                         async: false,
//                         data: {checkName: options.checkCtx.val()},
//                         success: options.ajax.success,
//                     },callback)
//                 })
//             })
//             // options.checkCtx.unbind().bind("blur change", function () {
//             //         if (!options.checkCtx.val()){
//             //             ctrFn.initMsgBox({
//             //                 notice: "数据为空"
//             //             })
//             //             return;
//             //         }
//             //     util.post({
//             //         url: options.ajax.url,
//             //         async: false,
//             //         data: {checkName: options.checkCtx.val()},
//             //         success: options.ajax.success,
//             //     },callback)
//             // })
//         })
//     }
//
// }
//
// $(function () {
//     ctrFn.addOpt({
//         dom: $(".add-dom span:eq(0)"),
//         checkCtx: $("#alertModal [name='checkName']"),
//         init: {
//             title: '新增中心'
//         },
//         ajax: {
//             url: ctrFn.url.dptCheck,
//             success: function (data) {
//                 var result = JSON.parse(data);
//                 $("#alertModal .help-block").text(result.msg);
//                 if (result.success) {
//                     $("#alertModal .commit-btn").unbind().bind("click", function () {
//                         $.ajax({
//                             url: host + 'organize/dpt/add',
//                             type: 'POST',
//                             data: {saveName: $("#alertModal [name='checkName']").val()},
//                             success: function (result) {
//                                 var data = JSON.parse(result)
//                                 if (data && data.success == true) {
//                                     $("#alertModal").modal('hide');
//                                 }
//                             },
//                             error: function () {
//                                 $("#alertModal").modal('hide');
//                             }
//                         })
//                     })
//                 }
//
//             }
//         }
//     })
//
//
//     /*$(".add-dom span:eq(0)").unbind().bind("click", function () {
//         $("#alertModal").modal();
//         var used = false;
//         $("[name='checkName']").unbind().bind("blur change",function () {
//             $("#alertModal .help-block").text('');
//             $.ajax({
//                 url: host + 'organize/dpt/checkExist',
//                 async: false,
//                 type: 'POST',
//                 data: {checkName: $("#alertModal [name='checkName']").val()},
//                 success: function (data) {
//                     var result = JSON.parse(data);
//                     $("#alertModal .help-block").text(result.msg);
//                     if (result.success){
//                         $("#alertModal .commit-btn").unbind().bind("click", function () {
//                             // $("#newCtr").bootstrapValidator('validate');
//                             $.ajax({
//                                 url: host + 'organize/dpt/add',
//                                 type: 'POST',
//                                 data: {saveName: $("#alertModal [name='checkName']").val()},
//                                 success: function (data) {
//                                     if (data && data.success == true) {
//
//                                         setTimeout(function () {
//                                             $("#alertModal").modal('hide');
//                                         },1000)
//                                     }
//                                 }
//                             })
//                         })
//                     }
//                 }
//             })
//         })
//     })*/
//
//
//     $(".add-dom span:eq(1)").unbind().bind("click", function () {
//         $("#alertModal").modal();
//         var used = false;
//         $("[name='checkName']").change(function () {
//             $("#alertModal .help-block").text('');
//             $.ajax({
//                 url: host + 'organize/pro/checkExist',
//                 async: false,
//                 type: 'POST',
//                 data: {checkName: $("#alertModal [name='checkName']").val()},
//                 success: function (data) {
//                     var result = JSON.parse(data);
//                     $("#alertModal .help-block").text(result.msg);
//                     if (result.success) {
//                         $("#alertModal .commit-btn").unbind().bind("click", function () {
//                             // $("#newCtr").bootstrapValidator('validate');
//                             $.ajax({
//                                 url: host + 'organize/pro/add',
//                                 type: 'POST',
//                                 data: {saveName: $("#alertModal [name='checkName']").val()},
//                                 success: function (data) {
//                                     if (data && data.success == true) {
//
//                                         setTimeout(function () {
//                                             $("#alertModal").modal('hide');
//                                         }, 1000)
//                                     }
//                                 }
//                             })
//                         })
//                     }
//                 }
//             })
//         })
//     })
//
// })