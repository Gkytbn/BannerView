# BannerView  安卓轮播图无限轮播实现

实现无限轮播，简单实现图片，文字和指引器显示和布局。基于纯代码实现，只需要将java文件 放入项目，就可以直接使用。
轮播文字信息，指引器均由Canvas 画上去，相比布局式简单，性能优越。

![image](https://github.com/Gkytbn/BannerView/blob/master/screenshots2.png)



```

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

        if (this.mIndicatorGravity == Gravity.CENTER) {
            x = (getMeasuredWidth() - count * circleRSize * 2 - (count - 1) * magin) * 0.5f;
        } else if (this.mIndicatorGravity == Gravity.LEFT) {
            x = magin;
        } else if (this.mIndicatorGravity == Gravity.RIGHT) {

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
            if (this.mTextGravity == Gravity.LEFT) {
                textX = magin;
            } else if (this.mTextGravity == Gravity.RIGHT) {
                textX = getMeasuredWidth() - mTextBound.width() - magin;
            } else if (this.mTextGravity == Gravity.CENTER) {
                textX = getMeasuredWidth() * 0.5f - mTextBound.width() * 0.5f;
            }
            canvas.drawText(s, textX, textY, paint);
        }
    }
```




使用方法：

```
    BannerView adView = (BannerView) findViewById(R.id.ad);

    List<String> strings = new ArrayList<>();
        
    strings.add("http://pic74.nipic.com/file/20150813/10634318_132510392000_2.jpg");
        
    strings.add("http://pic33.nipic.com/20130914/3267219_115451422382_2.jpg");
         
    strings.add("http://pic36.nipic.com/20131213/7755667_223712251391_2.jpg");
        
    strings.add("http://pic81.nipic.com/file/20151028/22036204_104827200000_2.jpg");
        
    strings.add("http://pic67.nipic.com/file/20150515/12973503_100930685000_2.jpg");
        
    adView.addUrl(strings);//设置显示图片地址
        
    List<String> titles = new ArrayList<>();
        
    titles.add("0000");
        
    titles.add("111");
        
    titles.add("22222");
        
    titles.add("33333");
        
    titles.add("44444");
        
    titles.add("5555");
        
    titles.add("6666666666666666");
        
    titles.add("777777777777777777");
        
    adView.addTitles(titles);// 设置显示 标题
        
    adView.setTextSize(16.0f,Color.GREEN);//设置文本字体大小和颜色

    adView.setTextGravity(BannerView.CENTER);// 设置文本对齐方式

    adView.setIndicatorGravity(BannerView.RIGHT);//设置圆点对齐方式

    adView.setIndicatorColor(Color.RED,Color.BLUE);//圆点指引器颜色

    adView.setDefulatTime(2000);//设置自动切换间隔时间
          
    adView.setFooterBackGround(Color.parseColor("#6F6B6B6B"));//底部文字和 圆点背景颜色，默认没有设置
        
        
    adView.setImageViewLoadLinstener(new BannerView.ImageViewLoad() {
     @Override
      public void ImageLoad(ImageView view, String url) {
          //图片加载回调,方便不同的图片加载框架
       Glide.with(MainActivity.this).load(url).error(R.mipmap.ad).dontAnimate().into(view);
     
       }

     @Override
     public void ImageClick(int index) {
      //点击事件回调
           Log.e(TAG, "ImageClick: " + index);
     }
     });
```
