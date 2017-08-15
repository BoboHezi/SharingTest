package eli.per.sharingtest.activity;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import eli.per.sharingtest.inter.OnPlatformSelected;
import eli.per.sharingtest.shareentity.ShareCircleFriends;
import eli.per.sharingtest.shareentity.ShareQQ;
import eli.per.sharingtest.shareentity.ShareQZone;
import eli.per.sharingtest.shareentity.ShareSina;
import eli.per.sharingtest.shareentity.ShareWeChat;
import eli.per.sharingtest.view.PlatformSelectDialog;

public class ShareEntity implements OnPlatformSelected {

    public static final int PLATFORM_ID_SINA = 1;
    public static final int PLATFORM_ID_CIRCLEFRIENDS = 2;
    public static final int PLATFORM_ID_WENCAHT = 3;
    public static final int PLATFORM_ID_QQ = 4;
    public static final int PLATFORM_ID_QZONE = 5;
    public static final int PLATFORM_ID_CANCEL = 6;

    public static final int SHARE_TYPE_TEXT = 1;
    public static final int SHARE_TYPE_PHOTO = 2;
    public static final int SHARE_TYPE_VIDEO = 3;
    public static final int SHARE_TYPE_MULTIIMAGE = 4;
    public int shareType = SHARE_TYPE_TEXT;

    private PlatformSelectDialog platformDialog;

    private Context context;
    private Activity activity;

    private ShareSina shareSina;
    private ShareQQ shareQQ;
    private ShareQZone shareQZone;
    private ShareWeChat shareWeChat;

    public ShareEntity(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        platformDialog = new PlatformSelectDialog(context);
        platformDialog.setOnPlatformSelected(this);
        platformDialog.show();
    }

    /**
     * 设置分享内容的类别
     *
     * @param shareType
     */
    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    /**
     * 获取实体
     *
     * @return
     */
    public ShareQQ getShareQQ() {
        return this.shareQQ;
    }

    /**
     * 获取实体
     *
     * @return
     */
    public ShareSina getShareSina() {
        return this.shareSina;
    }

    /**
     * 获取实体
     *
     * @return
     */
    public ShareQZone getShareQZone() {
        return this.shareQZone;
    }

    /**
     * 分享到新浪微博
     */
    private void shareSina() {
        if (shareType == SHARE_TYPE_TEXT)
            return;

        shareSina = new ShareSina(context, activity);
        switch (shareType) {
            case SHARE_TYPE_PHOTO:
                //分享单张图片
                shareSina.shareImage(null);
                break;
            case SHARE_TYPE_VIDEO:
                //分享视频
                shareSina.shareVideo(null);
                break;
            case SHARE_TYPE_MULTIIMAGE:
                //分享多张图片
                shareSina.shareMultiImage(null);
                break;
        }
        shareType = SHARE_TYPE_TEXT;
    }

    /**
     * 分享到QQ好友
     */
    private void shareQQ() {
        if (shareType == SHARE_TYPE_TEXT)
            return;

        shareQQ = new ShareQQ(context, activity);
        switch (shareType) {
            case SHARE_TYPE_PHOTO:
                //分享单张图片
                shareQQ.shareImage(null);
                break;
            default:
                Toast.makeText(context, "No Way", Toast.LENGTH_SHORT).show();
                break;
        }
        shareType = SHARE_TYPE_TEXT;
    }

    /**
     * 分享到QQ空间
     */
    private void shareQZone() {
        if (shareType == SHARE_TYPE_TEXT)
            return;

        shareQZone = new ShareQZone(context, activity);
        switch (shareType) {
            case SHARE_TYPE_PHOTO:
                //分享单张图片
                shareQZone.shareImage(null);
                break;
            case SHARE_TYPE_VIDEO:
                //分享视频
                shareQZone.shareVideo(null);
                break;
            case SHARE_TYPE_MULTIIMAGE:
                //分享多张图片
                shareQZone.shareMultiImage(null);
                break;
        }
        shareType = SHARE_TYPE_TEXT;
    }

    /**
     * 发送至微信
     */
    private void shareWeChat() {
        if (shareType == SHARE_TYPE_TEXT)
            return;

        shareWeChat = new ShareWeChat(context, activity);
        switch (shareType) {
            case SHARE_TYPE_PHOTO:
                //分享图片
                shareWeChat.shareImage(null);
                break;
            case SHARE_TYPE_MULTIIMAGE:
                //分享多图
                shareWeChat.shareMultiImage(null);
                break;
            default:
                Toast.makeText(context, "No Way", Toast.LENGTH_SHORT).show();
        }
        shareType = SHARE_TYPE_TEXT;
    }

    /**
     * 分享到朋友圈
     */
    private void shareCircleFriends() {
        if (shareType == SHARE_TYPE_TEXT)
            return;

        ShareCircleFriends shareCircleFriends = new ShareCircleFriends(context, activity);
        switch (shareType) {
            case SHARE_TYPE_PHOTO:
                //分享图片
                shareCircleFriends.shareImage(null);
                break;
            case SHARE_TYPE_MULTIIMAGE:
                //分享多图
                shareCircleFriends.shareMultiImage(null);
                break;
            default:
                Toast.makeText(context, "No Way", Toast.LENGTH_SHORT).show();
        }
        shareType = SHARE_TYPE_TEXT;
    }

    /**
     * 平台选择接口
     *
     * @param platformID 对应平台的ID
     */
    @Override
    public void getSelectedPlatform(int platformID) {
        switch (platformID) {
            case PLATFORM_ID_SINA:
                shareSina();
                break;

            case PLATFORM_ID_QQ:
                shareQQ();
                break;

            case PLATFORM_ID_QZONE:
                shareQZone();
                break;

            case PLATFORM_ID_WENCAHT:
                shareWeChat();
                break;

            case PLATFORM_ID_CIRCLEFRIENDS:
                shareCircleFriends();
                break;
        }
    }
}