package eli.per.sharingtest.shareentity;

import android.app.Activity;
import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

public class ShareWeChat extends BaseShareWeXin {

    public ShareWeChat(Context context, Activity activity) {
        super(context, activity);
        super.setScene(SendMessageToWX.Req.WXSceneSession);
    }
}
