package eli.per.sharingtest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

        copyFile("photo/IMG_20170811_104735.png");
        copyFile("photo/IMG_20170811_105222.png");
        copyFile("photo/photo.jpg");
        copyFile("video/VID_20170811_105225.mp4");
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
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (shareEntity.getShareToSina() != null) {
            shareEntity.getShareToSina().getShareHandler().doResultIntent(intent, shareEntity.getShareToSina());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (shareEntity.getShareToQQ() != null) {
            shareEntity.getShareToQQ().tencent.onActivityResult(requestCode, resultCode, data);
        }
        if (shareEntity.getShareToQZone() != null) {
            shareEntity.getShareToQZone().tencent.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void copyFile(final String filePath) {

        File folder = new File(getExternalFilesDir(null) + "/photo");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        folder = new File(getExternalFilesDir(null) + "/video");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        final File file = new File(getExternalFilesDir(null).getPath() + "/" + filePath);
        if (!file.exists()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream inputStream = getAssets().open(filePath);
                        OutputStream outputStream = new FileOutputStream(file);
                        byte buffer[] = new byte[1444];
                        int readSize;

                        while ((readSize = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, readSize);
                        }

                        inputStream.close();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
}
