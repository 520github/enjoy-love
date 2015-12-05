/**
 * Created with IntelliJ IDEA.
 * User: wangyi9
 * Date: 15-10-13
 * Time: 上午9:59
 * To change this template use File | Settings | File Templates.
 */
//上传图片
seajs.use("project/uploadPic", function(uploadPic) {
    //上传首屏图片
    //uploadPic('upload','pti','projectThumImage');
    //上传预热图片
    //uploadPic('upload1','pti1','projectThumImage1');
    //上传列表页图片
    //uploadPic('uploadProjectListImg','pli','ProjectListImg');
    //上传2.0首页图片
   // uploadPic('uploadProjectFirstImg','pfi','ProjectFirstImg');
    //上传项目图片
    uploadPic('upload2','pi','projectImage');
    //上传移动端项目图片
    //uploadPic('upload3','pti2','projectImageMobile');
});
