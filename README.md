# BannerView  安卓轮播图无限轮播实现

##实现无限轮播，基于纯代码实现，只需要将java文件 放入项目，就可以直接使用


![image](https://github.com/Gkytbn/BannerView/blob/master/screenshots2.png)


##使用方法：


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

   adView.setTextGravity(BannerView.Gravity.CENTER);// 设置文本对齐方式

   adView.setIndicatorGravity(BannerView.Gravity.RIGHT);//设置圆点对齐方式

   adView.setIndicatorColor(Color.RED,Color.BLUE);//圆点指引器颜色

   adView.setDefulatTime(2000);//设置自动切换间隔时间
          
   adView.setFooterBackGround(Color.parseColor("#6F6B6B6B"));//底部文字和 圆点背景颜色，默认没有设置
        
        
   adView.setImageViewLoadLinstener(new BannerView.ImageViewLoad() {
     @Override
      public void ImageLoad(ImageView view, String url) {
          //图片加载回调
       Glide.with(MainActivity.this).load(url).error(R.mipmap.ad).dontAnimate().into(view);
       }

     @Override
     public void ImageClick(int index) {
      //点击事件回调
           Log.e(TAG, "ImageClick: " + index);
     }
     });
