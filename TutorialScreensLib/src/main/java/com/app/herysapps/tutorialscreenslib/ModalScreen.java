package com.app.herysapps.tutorialscreenslib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.widget.FrameLayout;


public class ModalScreen extends FrameLayout {

    private Paint mTransparentCircle, mStrokeCircle;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int mRadius;
    private int[] mPos;

    private Properties mProperties;

    public ModalScreen(Context context) {
        super(context);
    }

    public ModalScreen(Properties properties) {
        super(properties.getActivity());

        mProperties = properties;

        // Get the target position
        int[] pos = new int[2];
        mProperties.getTargetView().getLocationOnScreen(pos);
        mPos = pos;

        // Get the target radius
        float mDensity = mProperties.getActivity().getResources().getDisplayMetrics().density;
        int padding = (int) (20 * mDensity + 0.5f);

        if (mProperties.getTargetView().getHeight() > mProperties.getTargetView().getWidth()) {
            mRadius = mProperties.getTargetView().getHeight() / 2 + padding;
        } else {
            mRadius = mProperties.getTargetView().getWidth() / 2 + padding;
        }

        // Initialize the screen
        initialize();
    }

    private void initialize() {
        setWillNotDraw(false);

        mBitmap = Bitmap.createBitmap(mProperties.getScreenSize().x, mProperties.getScreenSize().y, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        // Transparent Circle
        mTransparentCircle = new Paint();
        mTransparentCircle.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mTransparentCircle.setFlags(Paint.ANTI_ALIAS_FLAG);

        // Stroke
        if(mProperties.getTargetColor() != -1){
            mStrokeCircle = new Paint();
            mStrokeCircle.setColor(mProperties.getTargetColor());
            mStrokeCircle.setFlags(Paint.ANTI_ALIAS_FLAG);
        }
    }

    private boolean isWithinButton(MotionEvent ev) {
        return ev.getRawY() >= mPos[1] &&
                ev.getRawY() <= (mPos[1] + mProperties.getTargetView().getHeight()) &&
                ev.getRawX() >= mPos[0] &&
                ev.getRawX() <= (mPos[0] + mProperties.getTargetView().getWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBitmap.eraseColor(Color.TRANSPARENT);

        float cx = mPos[0] + mProperties.getTargetView().getWidth() / 2;
        float cy = mPos[1] + mProperties.getTargetView().getHeight() / 2;

        // Fill the canvas with the modal color
        mCanvas.drawColor(mProperties.getModalColor());

        // Draw transparent circle
        mCanvas.drawCircle(cx, cy, mRadius, mTransparentCircle);

        // Draw stroke circle
        if(mProperties.getTargetColor() != -1){
            mStrokeCircle.setStyle(Paint.Style.STROKE);
            mStrokeCircle.setStrokeWidth(10);
            mCanvas.drawCircle(cx, cy, mRadius, mStrokeCircle);
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