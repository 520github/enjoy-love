/**
 * Created with IntelliJ IDEA.
 * User: wangyi9
 * Date: 15-10-13
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
define("project/uploadPic", function(require, exports, module) {
    function uploadPic(upload,pti,projectThumImage){
        'use strict';
        // Change this to the location of your server-side upload handler:
        var url = 'http://'+window.location.host+'/file/upload/servletFileUpload';
        var dataType='json';
        if ($.NV('name')=="firefox" || $.NV('name')=="chrome"){
            //dataType='';
        }
        console.log("upload-->" + upload);
        console.log("pti-->" + pti);
        console.log("projectThumImage-->" + projectThumImage);
        console.log("dataType-->" + dataType);
        $('#'+upload).fileupload({
            dataType:dataType,
            done: function (e, data) {
                var imageUrl = data.result.imageUrl;
                console.log(imageUrl);
                $("#"+pti).attr("src",imageUrl);
                $("#"+projectThumImage).val(imageUrl);
                alert("上传成功!");
               /**         
                var json=data.result;
                if (data.result.files==undefined || data.result.files==null){
                    var jsonStr=data.result+"";
                    jsonStr=jsonStr.substring(0,jsonStr.length-64);
                    json=$.parseJSON(jsonStr);
                }
                $.each(json.files, function (index, file) {
                    if (file.result=="true"){
                        $("#"+pti).attr("src",file.url);
                        $("#"+projectThumImage).val(file.url);
                        alert("上传成功!");
                    }else{
                        alert("上传失败:"+file.errorMsg+"!");
                    }
                });
                **/
            },
            error:function(e,data){
                var json=data.result;
                try{
                    if (data.result.files==undefined || data.result.files==null){
                        var jsonStr=data.result+"";
                        jsonStr=jsonStr.substring(0,jsonStr.length-64);
                        json=$.parseJSON(jsonStr);
                    }
                }catch (err){
                    json= e.responseText;
                    if (json==null || json==undefined){
                        alert("上传失败.");
                        return;
                    }
                    json=json.substring(0,json.length-64);
                    json=$.parseJSON(json);
                }
                $.each(json.files, function (index, file) {
                    if (file.result=="true"){
                        $("#pti").attr("src",file.url);
                        $("#projectThumImage").val(file.url);
                        alert("上传成功!");
                    }else{
                        alert("上传失败:"+file.errorMsg+"!");
                    }
                });
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
            }
        }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
    }
    module.exports = uploadPic;
});