package eli.per.sharingtest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import eli.per.sharingtest.R;

/**
 * Author Eli Chang
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    private AppCompatButton sharePhotoButton;
    private AppCompatButton shareVideoButton;
    private AppCompatButton shareMultiImageButton;

    private ShareEntity shareEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        sharePhotoButton = (AppCompatButton) findViewById(R.id.sharephoto);
        shareVideoButton = (AppCompatButton) findViewById(R.id.sharevideo);
        shareMultiImageButton = (AppCompatButton) findViewById(R.id.sharemultiimage);
        sharePhotoButton.setOnClickListener(this);
        shareVideoButton.setOnClickListener(this);
        shareMultiImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sharephoto:
                shareEntity = new ShareEntity(this, this);
                shareEntity.setShareType(ShareEntity.SHARE_TYPE_PHOTO);
                break;

            case R.id.sharevideo:
                shareEntity = new ShareEntity(this, this);
                shareEntity.setShareType(ShareEntity.SHARE_TYPE_VIDEO);
                break;

            case R.id.sharemultiimage:
                shareEntity = new ShareEntity(this, this);
                shareEntity.setShareType(ShareEntity.SHARE_TYPE_MULTIIMAGE);
                break;
        }
    }

    /**
     * 设置返回页面时产生的回调接口
     * 这里返回给了微博的回调接口
     * 可以根据具体情况更改
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (shareEntity.getShareSina() != null) {
            shareEntity.getShareSina().getShareHandler().doResultIntent(intent, shareEntity.getShareSina());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (shareEntity.getShareQQ() != null) {
            shareEntity.getShareQQ().tencent.onActivityResult(requestCode, resultCode, data);
        }
        if (shareEntity.getShareQZone() != null) {
            shareEntity.getShareQZone().tencent.onActivityResult(requestCode, resultCode, data);
        }
    }
}
