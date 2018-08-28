package com.example.superl.park30.UI.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.example.superl.park30.R;


/**
 * Created by SuperL on 2018/8/20.
 */

public class MyRoundImageView extends android.support.v7.widget.AppCompatImageView {

    //图片类型， 圆角或圆形
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    //圆角的默认大小
    private static final int BODER_RADIUS_DEFAULT = 10;
    //圆角的大小
    private int mBorderRadius;
    //绘图的Paint
    private Paint mBitmapPaint;
    //圆角半径
    private int mRadius;
    // 3 x 3矩阵， 主要是用来缩小方大
    private Matrix mMatrix;
    //渲染图像， 使用图像为绘制图形着色
    private BitmapShader mBitmapShaper;
    //View的宽度
    private int mWidth;
    private RectF mRoundRect;

    public MyRoundImageView(Context context) {
        super(context);
        init(context);
    }

    public MyRoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyRoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    private void init(Context context){
        init(context, null);
    }
    private void init(Context context, AttributeSet attrs){
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        //圆角默认为10dp
        mBorderRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_borderRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BODER_RADIUS_DEFAULT,
                        getResources().getDisplayMetrics()));
        //默认为Circle
        type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == TYPE_CIRCLE){
            mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
            mRadius = mWidth / 2 ;
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null){
            return;
        }
        setUpShader();
        if (type == TYPE_ROUND){
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint);
        }else {
            canvas.drawCircle(mRadius, mRadius,mRadius - 1,mBitmapPaint);
        }
    }

    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) return;

        Bitmap bmp = drawableToBitmap(drawable);
        //将bmp作为着色器，在指定区域作色
        mBitmapShaper = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (type == TYPE_CIRCLE){
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = mWidth * 1.0f/ bSize;
        }else if (type == TYPE_ROUND){
            scale = Math.max(getWidth() *1.0f / bmp.getWidth(), getHeight() * 1.0f / bmp.getHeight());
        }
        //shader的变换矩阵， 我们这里主要是用于放大或者缩小
        mMatrix.setScale(scale, scale);
        //设置变换矩阵
        mBitmapShaper.setLocalMatrix(mMatrix);
        //设置shader
        mBitmapPaint.setShader(mBitmapShaper);

    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable){
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,w,h);
        drawable.draw(canvas);
        return bitmap;


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (type == TYPE_ROUND){
            mRoundRect = new RectF(0,0,getWidth(), getHeight());
        }
    }
}
