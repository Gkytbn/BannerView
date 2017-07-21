package gkbn.BannerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 高悦兵 on 2016/10/10
 * 安卓轮播图无限轮播实现
 */

public class BannerView extends FrameLayout implements View.OnClickListener {
    
    
   public  final static int LEFT = 0;
  public  final static int RIGHT = 1;
  public  final static int CENTER = 2;
    /**
     * 下载地址list
     */
    private List<String> urls = new ArrayList<>();
    /**
     * 标题list
     */
    private List<String> titles = new ArrayList<>();
    /**
     * imagView控件list
     */
    private List<ImageView> imgs = new ArrayList<>();
    private Timer timer;
    /**
     * 默认切换页面时间
     */
    private int defulatTime = 3500;
    private MyTimerTask timerTask; //定时器
    private Paint paint; //画笔
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    /**
     * 字体大小，默认16 sp
     */
    private float mTextSize = 16.0f;
    /**
     * 文本对齐方式,默认左边对齐
     */
    private int mTextGravity = LEFT;
    /**
     * 指引器圆点对齐方式，默认中间对齐
     */
    private int mIndicatorGravity = CENTER;
    /**
     * 选中时圆点的颜色
     */
    private int mIndicatorSelectColor = Color.GREEN;
    /**
     * 圆点普通的颜色
     */
    private int mIndicatorNormalColor = Color.YELLOW;
    /**
     * 底部文字和 圆点背景颜色，默认没有设置
     */
    private int mFooterBackGroundColor;
    /**
     * 是否启用底部背景
     */
    private boolean mFooterBackGroundEnable = false;
    /**
     * 文字颜色，默认白色
     */
    private int mTextColor = Color.WHITE;


    public BannerView(Context context) {
        super(context);
        init();
    }


    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    /**
     * 设置文本对齐方式
     *
     * @param mTextGravity
     */
    public void setTextGravity(int mTextGravity) {
        this.mTextGravity = mTextGravity;
    }

    /**
     * 圆点指引器颜色
     *
     * @param selcetColor 选中颜色
     * @param NormalColor 普通颜色
     */
    public void setIndicatorColor(int selcetColor, int NormalColor) {
        this.mIndicatorSelectColor = selcetColor;
        this.mIndicatorNormalColor = NormalColor;

    }

    /**
     * 设置圆点对齐方式
     *
     * @param mIndicatorGravity
     */
    public void setIndicatorGravity(int mIndicatorGravity) {
        this.mIndicatorGravity = mIndicatorGravity;
    }

    /**
     * 设置底部背景颜色
     *
     * @param mFooterBackGroundColor
     */
    public void setFooterBackGround(int mFooterBackGroundColor) {
        this.mFooterBackGroundColor = mFooterBackGroundColor;
        this.mFooterBackGroundEnable = true;
    }

 
    @Override
    public void onClick(View v) {
        if (imageViewLoad != null) {
            imageViewLoad.ImageClick(viewPager.getCurrentItem() - 1);
        }
    }


    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    /**
     * 设置自动切换间隔时间
     *
     * @param defulatTime
     */
    public void setDefulatTime(int defulatTime) {
        this.defulatTime = defulatTime;
        if (timerTask != null) {
            timerTask.cancel();  //将原任务从队列中移除
        }
        timerTask = new MyTimerTask();
        timer.schedule(timerTask, 3000, defulatTime);
    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };


    /**
     * 添加标题
     *
     * @param title 标题list<String>
     */
    public void addTitles(List<String> title) {

        //viewpager 循环  4 1 2 3 4 1，列表第一个加入最后一个元素，最后的元素加上第一个元素。
        // 当显示最会的一个1 时 切换到第一个1 ；
        //当显示最后一个 4 d的时候 ，显示第一个 4；

        this.titles.removeAll(titles);
        this.titles.addAll(title);

        if (titles.size() >= 2) {
            String first = titles.get(0);
            titles.add(0, titles.get(titles.size() - 1));
            titles.add(first);
        }
    }

    /**
     * 添加图片地址
     *
     * @param url 地址list<String>
     */
    public void addUrl(List<String> url) {
        urls.removeAll(urls);
        this.urls.addAll(url);

        if (urls.size() >= 2) {
            String first = urls.get(0);
            urls.add(0, urls.get(urls.size() - 1));
            urls.add(first);
        }
        for (int i = 0; i < urls.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setOnClickListener(BannerView.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgs.add(imageView);
        }
        viewPager.setCurrentItem(urls.size() - 1);
        myPagerAdapter.notifyDataSetChanged();
    }


    /**
     * 设置文本字体大小和颜色
     *
     * @param mTextSize 大小
     * @param color     颜色
     */
    public void setTextSize(float mTextSize, int color) {
        this.mTextColor = color;
        this.mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics());
    }


    /**
     * 绘制 底部圆点信息和title，绘制信息要在子空间前面，所以放在后面绘制，每一次更新ui，就会调用。
     * 很方便实现底部信息对应。
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int count = urls.size() >= 2 ? urls.size() - 2 : urls.size();
        float RectHight = getMeasuredHeight() * 0.2f;//矩形的高度

        if (this.mFooterBackGroundEnable) {
            paint.setColor(mFooterBackGroundColor);
            canvas.drawRect(0, getMeasuredHeight() - RectHight, getMeasuredWidth(), getMeasuredHeight(), paint);
        }
        float circleRSize = RectHight * 0.15f;
        float magin = circleRSize * 1.5f;

        float x = 0;

        if (this.mIndicatorGravity == CENTER) {
            x = (getMeasuredWidth() - count * circleRSize * 2 - (count - 1) * magin) * 0.5f;
        } else if (this.mIndicatorGravity == LEFT) {
            x = magin;
        } else if (this.mIndicatorGravity ==RIGHT) {

            x = getMeasuredWidth() - count * circleRSize * 2 - count * magin;
        }

        float cY = getMeasuredHeight() - RectHight + RectHight * 0.5f;
        float cX = x + circleRSize;


        for (int i = 1; i < count + 1; i++) {
            if (viewPager.getCurrentItem() == i) {
                paint.setColor(mIndicatorSelectColor);
            } else {
                paint.setColor(mIndicatorNormalColor);
            }
            if (i != 1) {
                cX += circleRSize * 2 + magin;
            }
            canvas.drawCircle(cX, cY, circleRSize, paint);
        }

        if (titles.size() - 1 >= viewPager.getCurrentItem()) {

            String s;
            //避免因为index重复 取到错误的标题
            if (myPagerAdapter.getCount() - 1 == viewPager.getCurrentItem()) {
                s = titles.get(1);
            } else if (viewPager.getCurrentItem() == 0) {
                s = titles.get(imgs.size() - 2);
            } else {
                s = titles.get(viewPager.getCurrentItem());
            }
            paint.setColor(mTextColor);
            Rect mTextBound = new Rect();
            paint.getTextBounds(s, 0, s.length(), mTextBound);
            paint.setTextSize(this.mTextSize);
            float textX = 0.0f;
            float textY = getMeasuredHeight() - (RectHight - mTextBound.height()) * 0.5f;
            if (this.mTextGravity == LEFT) {
                textX = magin;
            } else if (this.mTextGravity == RIGHT) {
                textX = getMeasuredWidth() - mTextBound.width() - magin;
            } else if (this.mTextGravity == CENTER) {
                textX = getMeasuredWidth() * 0.5f - mTextBound.width() * 0.5f;
            }
            canvas.drawText(s, textX, textY, paint);
        }
    }


    private void init() {
        setWillNotDraw(false);//强制调用Viewgroup onDraw（）方法；
        viewPager = new ViewPager(getContext());
        this.addView(viewPager, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //实现viewPager 无限循环
                if (urls.size() >= 2) {
                    if (position == 0 && positionOffset == 0)
                        viewPager.setCurrentItem(urls.size() - 2, false);
                    else if (position == urls.size() - 1 && positionOffset == 0)
                        viewPager.setCurrentItem(1, false);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        timerTask = new MyTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, 3000, defulatTime);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {


            case MotionEvent.ACTION_MOVE:
                //滑动时取消定时器
                if (timerTask != null) {
                    timerTask.cancel();  //将原任务从队列中移除
                    timerTask = null;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                //滑动结束 ，重新开启定时器
                if (timerTask == null) {
                    timerTask = new MyTimerTask();
                    timer.schedule(timerTask, 5000, defulatTime);
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //取消定时器
        if (timerTask != null) {
            timerTask.cancel();  //将原任务从队列中移除
        }
    }


    /**
     * 事件回调
     */
    public interface ImageViewLoad {
        /**
         * 图片加载回调，可使用Glide 等图片加载框架进行加载，避免OOM
         *
         * @param view 对应imageView
         * @param url  对应的url 地址
         */
        void ImageLoad(ImageView view, String url);

        /**
         * 点击事件回调
         *
         * @param index
         */
        void ImageClick(int index);
    }

    private ImageViewLoad imageViewLoad;

    /**
     * 设置事件回调对象
     *
     * @param imageViewLoad
     */
    public void setImageViewLoadLinstener(ImageViewLoad imageViewLoad) {
        this.imageViewLoad = imageViewLoad;
    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = imgs.get(position);

            if (imageView.getDrawable() == null) {
                if (imageViewLoad != null) {
                    imageViewLoad.ImageLoad(imageView, urls.get(position));//加载土图片
                }
            }
            container.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

    }

}
