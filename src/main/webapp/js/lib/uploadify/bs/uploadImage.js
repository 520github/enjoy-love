var uploadHost = "";//http://localhost:8080

$(document).ready(function(){
     $('#myUpload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传图片',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/js/lib/uploadify/uploadify.swf',
		uploader : uploadHost+'/file/upload/springMultipartRequest',
		fileSizeLimit : '3MB',
		successTimeout : 180000,
		onUploadStart:function(){
			alert("上传开始");
		},
		onUploadSuccess : function(file, data, response) {
		    console.log("data-->" + data);
		    console.log("file--->" + file);
		},
		onUploadError : function(file, errorCode, errorMsg, errorString) {
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			alert("已取消！");
		}
	});
});
