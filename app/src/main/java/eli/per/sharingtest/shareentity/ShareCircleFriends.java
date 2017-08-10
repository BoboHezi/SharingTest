package eli.per.sharingtest.shareentity;

import android.app.Activity;
import android.content.Context;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

public class ShareCircleFriends extends BaseShareWeXin{

    public ShareCircleFriends(Context context, Activity activity) {
        super(context, activity);
        super.setScene(SendMessageToWX.Req.WXSceneTimeline);
    }
}
