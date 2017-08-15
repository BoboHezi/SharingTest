package eli.per.sharingtest.shareentity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.open.utils.Util;

import java.io.File;
import java.util.ArrayList;

public class BaseShareWeXin extends BaseShare {

    public static final String APP_ID = "wx8e34af50432b0010";
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

    /**
     * 分享图片
     * @param imageFile
     */
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

    /**
     * 分享视频
     * @param videoFile
     */
    @Override
    public void shareVideo(File videoFile) {
        if (videoFile == null) {
            videoFile = new File(context.getExternalFilesDir(null) + "/video/VID_20170811_105225.mp4");
        }
        //初始化WXVideoObject，设置视频的URL
        WXVideoObject videoObject = new WXVideoObject();
        videoObject.videoLowBandUrl = videoFile.getPath();

        //创建WXMediaMessage，设置视频的标题和描述
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = videoObject;
        mediaMessage.title = "";
        mediaMessage.description = "";

        //设置视频的缩略图
        Bitmap thumb = eli.per.sharingtest.util.Util.getVideoThumbnail(videoFile.getPath(), 100, 100, MediaStore.Images.Thumbnails.MINI_KIND);
        mediaMessage.thumbData = eli.per.sharingtest.util.Util.bitmap2ByteArray(thumb, true);

        //创建一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = eli.per.sharingtest.util.Util.buildTransaction("video");
        req.message = mediaMessage;
        req.scene = scene;
        wxApi.sendReq(req);
    }

    /**
     * 分享多图
     * @param imageFiles
     */
    @Override
    public void shareMultiImage(File[] imageFiles) {
        if (imageFiles == null) {
            imageFiles = new File(context.getExternalFilesDir(null) + "/photo/").listFiles();
        }
        //设置启动页面的Activity
        String wxPackage = "com.tencent.mm.ui.tools.ShareImgUI";
        if (scene == SendMessageToWX.Req.WXSceneTimeline) {
            wxPackage = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
        }
        //设置Intent
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", wxPackage);
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putExtra("Kdescription", "");
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (File file : imageFiles) {
            imageUris.add(Uri.fromFile(file));
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    /**
     * 设置分享的平台  好友/朋友圈
     * @param scene
     */
    public void setScene(int scene) {
        this.scene = scene;
    }
}
