var UploadifyFileCommon = {
    defaultConfig: {
        height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '上传文件',
		multi : false,
		swf : '/js/lib/uploadify-zc/uploadify.swf',
		uploader : '/file/upload/springMultipartRequest',
		fileSizeLimit : '2MB',
		successTimeout : 180000,
		onUploadStart:function(){
			//layer.msg("文件开始上传中",1);
		},
		onUploadError : function(file, errorCode, errorMsg, errorString) {
			layer.msg("图片上传失败," + errorString);
		},
		onCancel : function(file) {
			layer.msg("已取消文件上传");
		}
    },
    /**
                    参数说明:
            FileDivId--文件上传的divId
            showUrlId--上传后显示的imageId
            saveUrlId--需要把url保存到隐藏域的id
            buttonShowText--上传按钮显示内容   
    **/
    uploadOneImage: function(FileDivId,showUrlId,saveUrlId,buttonShowText,pixelType) {
        var url = '/file/upload/springMultipartRequest';
        if($.trim(pixelType).length > 0) {
            url = url + "?pixelType=" + pixelType;
        }
        var uploadOneImageConfig = {
            uploader:url,
            fileTypeDesc:'请选择jpg、jpeg、png、gif格式图片',
            fileTypeExts:'*.jpg;*.jpeg;*.png;*.gif',
            onSelect: function(file) {
            
            },
            onUploadSuccess: function(file, data, response) {
                console.log("data-->" + data);
                console.log("showUrlId-->" + showUrlId);
                var jsonData =$.parseJSON(data)
                if($.trim(jsonData.imageUrl).length > 0) {
                    $('#'+showUrlId).attr("src", jsonData.imageUrl);
                    $('#'+saveUrlId).val(jsonData.imageUrl);
                }
                else {
                    //layer.msg("图片上传失败," + jsonData.message);
                    console.log("图片上传失败," + jsonData.message);
                }              
            },
            buttonText:buttonShowText
        };
        var config = $.extend(this.defaultConfig, uploadOneImageConfig);
        $('#'+FileDivId).uploadify(config);
    }
};
