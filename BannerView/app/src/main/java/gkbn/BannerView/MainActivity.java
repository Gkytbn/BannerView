package gkbn.BannerView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "gkbn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BannerView adView = (BannerView) findViewById(R.id.ad);

        List<String> strings = new ArrayList<>();
        strings.add("http://pic74.nipic.com/file/20150813/10634318_132510392000_2.jpg");
        strings.add("http://pic33.nipic.com/20130914/3267219_115451422382_2.jpg");
        strings.add("http://pic36.nipic.com/20131213/7755667_223712251391_2.jpg");
        strings.add("http://pic81.nipic.com/file/20151028/22036204_104827200000_2.jpg");
        strings.add("http://pic67.nipic.com/file/20150515/12973503_100930685000_2.jpg");
        adView.addUrl(strings);
        List<String> titles = new ArrayList<>();
        titles.add("0000");
        titles.add("111");
        titles.add("22222");
        titles.add("33333");
        titles.add("44444");
        titles.add("5555");
        titles.add("6666666666666666");
        titles.add("777777777777777777");
        adView.addTitles(titles);
        adView.setTextSize(16.0f,Color.GREEN);
        adView.setTextGravity(BannerView.Gravity.CENTER);
        adView.setIndicatorGravity(BannerView.Gravity.RIGHT);
//        adView.setIndicatorColor(Color.RED,Color.BLUE);
        adView.setDefulatTime(2000);
        adView.setFooterBackGround(Color.parseColor("#6F6B6B6B"));

        adView.setImageViewLoadLinstener(new BannerView.ImageViewLoad() {
            @Override
            public void ImageLoad(ImageView view, String url) {
                Glide.with(MainActivity.this).load(url).error(R.mipmap.ad).dontAnimate().into(view);
            }

            @Override
            public void ImageClick(int index) {
                Log.e(TAG, "ImageClick: " + index);
            }
        });
    }
}
