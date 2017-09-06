package com.app.herysapps.tutorialscreenslib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * A tutorial screen for Android.
 * <p>
 * Created by Hery Lopez on 29/08/2017.
 * <p>
 * Github:
 * <p>
 * Author: <a href="https://github.com/HeryLopez/">https://github.com/HeryLopez</a>
 * <br/>Project:  <a href="https://github.com/HeryLopez/TutorialScreen">https://github.com/HeryLopez/TutorialScreen</a>
 */
public class ModalScreen extends FrameLayout {

    private Paint mTransparent, mStroke;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int[] mTargetPosition;

    private float mLeft, mTop;
    private int mRadius;
    private RectF mRectF;
    private float mRoundCorner;

    private Properties mProperties;

    public ModalScreen(Context context) {
        super(context);
    }

    public ModalScreen(Properties properties) {
        super(properties.getActivity());

        mProperties = properties;

        // Initialize the screen
        initialize();
    }

    private void initialize() {
        setWillNotDraw(false);

        float mDensity = mProperties.getActivity().getResources().getDisplayMetrics().density;

        // Get the target position
        int[] pos = new int[2];
        mProperties.getTargetView().getLocationOnScreen(pos);
        mTargetPosition = pos;

        mBitmap = Bitmap.createBitmap(mProperties.getScreenSize().x, mProperties.getScreenSize().y, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        // Transparent Circle
        mTransparent = new Paint();
        mTransparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mTransparent.setFlags(Paint.ANTI_ALIAS_FLAG);

        // Stroke
        if(mProperties.getTargetColor() != -1){
            mStroke = new Paint();
            mStroke.setColor(mProperties.getTargetColor());
            mStroke.setFlags(Paint.ANTI_ALIAS_FLAG);
            mStroke.setStyle(Paint.Style.STROKE);
            mStroke.setStrokeWidth(10);
        }

        if(mProperties.getTargetStyle() == Properties.TargetStyle.CIRCULAR_TARGET){

            // Get the target radius
            int padding = (int) (16 * mDensity + 0.5f);

            if (mProperties.getTargetView().getHeight() > mProperties.getTargetView().getWidth()) {
                mRadius = mProperties.getTargetView().getHeight() / 2 + padding;
            } else {
                mRadius = mProperties.getTargetView().getWidth() / 2 + padding;
            }

            // Get position of circle
            mLeft = mTargetPosition[0] + mProperties.getTargetView().getWidth() / 2;
            mTop = mTargetPosition[1] + mProperties.getTargetView().getHeight() / 2;

        } else if(mProperties.getTargetStyle() == Properties.TargetStyle.RECTANGULAR_TARGET){

            mRoundCorner = (int) (10 * mDensity);

            int padding = (int) (6 * mDensity + 0.5f);

            mLeft = mTargetPosition[0] - padding;
            mTop = mTargetPosition[1] - padding;

            float mRight = mTargetPosition[0] + mProperties.getTargetView().getWidth() + padding;
            float mBottom = mTargetPosition[1] + mProperties.getTargetView().getHeight() + padding;

            mRectF = new RectF(mLeft, mTop, mRight, mBottom);

        } else if(mProperties.getTargetStyle() == Properties.TargetStyle.NO_TARGET){
            // Do nothing
        }
    }

    private boolean isWithinButton(MotionEvent ev) {
        return ev.getRawY() >= mTargetPosition[1] &&
                ev.getRawY() <= (mTargetPosition[1] + mProperties.getTargetView().getHeight()) &&
                ev.getRawX() >= mTargetPosition[0] &&
                ev.getRawX() <= (mTargetPosition[0] + mProperties.getTargetView().getWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBitmap.eraseColor(Color.TRANSPARENT);

        // Fill the canvas with the modal color
        mCanvas.drawColor(mProperties.getModalColor());

        if(mProperties.getTargetStyle() == Properties.TargetStyle.CIRCULAR_TARGET){
            // Draw transparent circle
            mCanvas.drawCircle(mLeft, mTop, mRadius, mTransparent);

            // Draw stroke circle
            if(mProperties.getTargetColor() != -1){
                mCanvas.drawCircle(mLeft, mTop, mRadius, mStroke);
            }

        } else if(mProperties.getTargetStyle() == Properties.TargetStyle.RECTANGULAR_TARGET){

            mCanvas.drawRoundRect(mRectF,  mRoundCorner,  mRoundCorner, mTransparent);

            if(mProperties.getTargetColor() != -1){
                mCanvas.drawRoundRect(mRectF,  mRoundCorner,  mRoundCorner, mStroke);
            }

        } else if(mProperties.getTargetStyle() == Properties.TargetStyle.NO_TARGET){
            // Do nothing
        }

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mCanvas.setBitmap(null);
        mBitmap = null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (mProperties.getTargetView() != null) {
            if (isWithinButton(ev) && !mProperties.isClickEventEnable()) {
                return true;   // block it
            } else if (isWithinButton(ev)) {
                return false;  // let it pass through
            }
        }
        // do nothing, just propagating up to super
        return super.dispatchTouchEvent(ev);
    }

}