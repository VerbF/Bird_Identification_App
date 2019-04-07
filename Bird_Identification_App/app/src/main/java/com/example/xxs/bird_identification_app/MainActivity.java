package com.example.xxs.bird_identification_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.wildma.pictureselector.PictureSelector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    TextView loading_tv;
    Bitmap bird_image;
    String image_Path;
    CircularProgressView progressView;
    View obscuration_view;
    final String url = "http://192.168.1.105:8888/uploadImg";
    final String TAG = "MainActivity";
    final int ovscuration_alpha = 170;
    final int cropWidth = 1200;
    final int cropHeight =1200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_btn =  (ImageButton) findViewById(R.id.imageButton);
        identify_btn = (Button) findViewById(R.id.identify_btn);
        image_btn.getBackground().setAlpha(0);

        //选择图片
        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(MainActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true, cropWidth, cropWidth, 1, 1);
            }
        });
        //开始识别
        identify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置蒙层和正在加载组件
                progressView = (CircularProgressView) findViewById(R.id.progress_view);
                obscuration_view = findViewById(R.id.obscuration_view);
                loading_tv = (TextView) findViewById(R.id.loading_tv);

                obscuration_view.bringToFront();
                obscuration_view.setVisibility(View.VISIBLE);
                obscuration_view.getBackground().setAlpha(ovscuration_alpha);

                loading_tv.bringToFront();
                loading_tv.setVisibility(View.VISIBLE);

                progressView.bringToFront();
                progressView.startAnimation();
                progressView.setVisibility(View.VISIBLE);

                //上传图像
                uploadImage(image_Path);
            }
        });
    }
    //用户选择图片结束
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                //将用户选择的图片显示在imagebutton中
                image_Path  = data.getStringExtra(PictureSelector.PICTURE_PATH);
                image_btn =  (ImageButton) findViewById(R.id.imageButton);
                bird_info_tv = (TextView) findViewById(R.id.bird_info_tv);

                bird_info_tv.setVisibility(View.INVISIBLE);
                bird_image = BitmapFactory.decodeFile(image_Path);
                image_btn.setImageBitmap(bird_image);

            }
        }
    }
    protected  Bitmap base64_to_Bitmap(String base64Data)
    {
        byte[] bytes = Base64.decode(base64Data,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
    //上传图片，接收检测结果（图片 + 鸟类信息 + 鸟类名称）
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
            //成功响应
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //从resonance中取出json对象
                String json_result = response.body().string();
                JSONObject jsonObj = null;
                Bitmap temp_image = null;
                String temp_bird_name = null;
                String temp_bird_info = null;
                try {
                    jsonObj = new JSONObject(json_result);
                    temp_bird_name = jsonObj.getString("bird_name");
                    temp_bird_info = jsonObj.getString("bird_info");
                    temp_image = base64_to_Bitmap(jsonObj.getString("image_base64_string"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final Bitmap result_image = temp_image;
                final String bird_name = temp_bird_name;
                final String bird_info = temp_bird_info;

                bird_image = result_image;
                Log.e(TAG, "成功"+response);
                //更新UI （图片 + 鸟类信息）
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //隐藏蒙层和正在加载组件
                        progressView = (CircularProgressView) findViewById(R.id.progress_view);
                        obscuration_view = findViewById(R.id.obscuration_view);
                        loading_tv = (TextView) findViewById(R.id.loading_tv);

                        loading_tv.setVisibility(View.INVISIBLE);
                        obscuration_view.setVisibility(View.INVISIBLE);
                        progressView.stopAnimation();
                        progressView.setVisibility(View.INVISIBLE);
                        //显示结果图片和鸟类信息
                        image_btn =  (ImageButton) findViewById(R.id.imageButton);
                        bird_info_tv = (TextView) findViewById(R.id.bird_info_tv);
                        image_btn.setImageBitmap(result_image);
                        bird_info_tv.setVisibility(View.VISIBLE);
                        bird_info_tv.setText(bird_name + " "+ bird_info);
                        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
