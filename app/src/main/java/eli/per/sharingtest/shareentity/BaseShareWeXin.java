package eli.per.sharingtest.shareentity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

public class BaseShareWeXin extends BaseShare {

    private static final String APP_ID = "";
    private IWXAPI wxApi;
    private int scene = SendMessageToWX.Req.WXSceneSession;

    public BaseShareWeXin(Context context, Activity activity) {
        super(context, activity);
        initSDK();
    }

    @Override
    public void initSDK() {
        wxApi = WXAPIFactory.createWXAPI(context, APP_ID, true);
        wxApi.registerApp(APP_ID);
    }

    @Override
    public void shareImage(File imageFile) {

        if (imageFile == null) {
            imageFile = new File(context.getExternalFilesDir(null) + "/photo/photo.jpg");
        }

        //初始化WXImageObject和WXMediaMessage对象
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath(), null);
        WXImageObject imageObject = new WXImageObject(bitmap);
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = imageObject;

        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        bitmap.recycle();
        mediaMessage.thumbData = eli.per.sharingtest.util.Util.bitmap2ByteArray(thumbBmp, true);

        //构造Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = eli.per.sharingtest.util.Util.buildTransaction("img");
        req.message = mediaMessage;
        req.scene = scene;

        wxApi.sendReq(req);
    }

    @Override
    public void shareVideo(File videoFile) {
        if (videoFile == null) {
            videoFile = new File(context.getExternalFilesDir(null) + "/video/VID_20170811_105225.mp4");
        }

        //初始化WXVideoObject，并设置视频路径
        WXVideoObject videoObject = new WXVideoObject();
        videoObject.videoUrl = videoFile.getPath();

        //创建一个WXMediaMessage对象，设置视频标题和描述
        WXMediaMessage mediaMessage = new WXMediaMessage(videoObject);
        mediaMessage.title = "标题";
        mediaMessage.description = "描述";

        //为视频设置缩略图
        Bitmap thumb = eli.per.sharingtest.util.Util.getVideoThumbnail(videoFile.getPath(), 500, 350, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
        mediaMessage.thumbData = eli.per.sharingtest.util.Util.bitmap2ByteArray(thumb, true);

        //构造Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = eli.per.sharingtest.util.Util.buildTransaction("video");
        req.message = mediaMessage;
        req.scene = scene;

        wxApi.sendReq(req);
    }

    @Override
    public void shareMultiImage(File[] imageFiles) {

    }

    /**
     * 设置分享的平台  好友/朋友圈
     *
     * @param scene
     */
    public void setScene(int scene) {
        this.scene = scene;
    }
}
