package com.example.xxs.bird_identification_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Call;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    ImageButton image_btn ;
    Button identify_btn;
    TextView bird_info_tv ;
    Bitmap bird_image;
    String image_Path;
    final String url = "http://192.168.1.105:8888/uploadImg";
    final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_btn =  (ImageButton) findViewById(R.id.imageButton);
        identify_btn = (Button) findViewById(R.id.identify_btn);
        //选择图片
        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(MainActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true, 1200, 1200, 1, 1);
            }
        });
        //开始识别
        identify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(image_Path);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                image_Path = data.getStringExtra(PictureSelector.PICTURE_PATH);
                bird_info_tv  = (TextView) findViewById(R.id.bird_info_tv);
                image_btn =  (ImageButton) findViewById(R.id.imageButton);
                bird_info_tv.setText(image_Path);
                bird_image = BitmapFactory.decodeFile(image_Path);
                image_btn.setImageBitmap(bird_image);

            }
        }
    }
    //上传图片
    protected void uploadImage(String image_path) {
        if (image_path == null)
        {
            Toast.makeText(MainActivity.this, "请选择图片！", Toast.LENGTH_SHORT).show();
            return;
        }
        int timeout = 60;
        OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(timeout,TimeUnit.SECONDS)
                .readTimeout(timeout,TimeUnit.SECONDS)
                .writeTimeout(timeout,TimeUnit.SECONDS)
                .build();
        File file = new File(image_path);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img", "bird.jpg",
                        RequestBody.create(MediaType.parse("image/png"), file));

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: "+e );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final InputStream inputStream = response.body().byteStream();//得到图片的流
                final Bitmap result_image = BitmapFactory.decodeStream(inputStream);
                bird_image = result_image;
                Log.e(TAG, "成功"+response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image_btn =  (ImageButton) findViewById(R.id.imageButton);
                        image_btn.setImageBitmap(result_image);
                        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
