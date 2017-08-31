package com.app.herysapps.tutorialscreenslib;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TutorialScreen {

    // Properties
    private Properties mProperties;
    // Modal Screen and indicator circle
    private ModalScreen mModalScreen;
    // Dialog
    private View mDialogLayout;
    // Parent
    private ViewGroup mParent;


    private TutorialScreen(Properties properties) {
        mProperties = properties;

        mParent = (ViewGroup) mProperties.getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
    }



    public TutorialScreen setTitle(String s) {
        mProperties.setTitle(s);
        return this;
    }

    public TutorialScreen setMessage(String s) {
        mProperties.setMessage(s);
        return this;
    }

    public TutorialScreen setClickEventEnable(boolean b) {
        mProperties.setClickEventEnable(b);
        return this;
    }

    public TutorialScreen setNextButton(String text, final View.OnClickListener listener) {
        mProperties.setNextButtonText(text);
        mProperties.setNextButtonListener(listener);
        return this;
    }

    public TutorialScreen setNormalButton(String text, final View.OnClickListener listener) {
        mProperties.setNormalButtonText(text);
        mProperties.setNormalButtonListener(listener);
        return this;
    }

    public TutorialScreen setModalColor(int color) {
        mProperties.setModalColor(color);
        return this;
    }

    public TutorialScreen setTitleColor(int color) {
        mProperties.setTitleColor(color);
        return this;
    }

    public TutorialScreen setMessageColor(int color) {
        mProperties.setMessageColor(color);
        return this;
    }

    public TutorialScreen setTargetColor(int color) {
        mProperties.setTargetColor(color);
        return this;
    }

    public TutorialScreen setBackButtonColor(int color) {
        mProperties.setBackButtonColor(color);
        return this;
    }

    public TutorialScreen setTextButtonColor(int color) {
        mProperties.setTextButtonColor(color);
        return this;
    }



    public String getTargetViewId(){
        return mProperties.getTargetViewId();
    }



    public TutorialScreen show(View v, String id) {

        mProperties.setTargetView(v);
        mProperties.setTargetViewId(id);

        // TourGuide can only be setup after all the views is ready and obtain it's position/measurement
        // so when this is the 1st time TourGuide is being added,
        // else block will be executed, and ViewTreeObserver will make TourGuide setup process to be delayed until everything is ready
        // when this is run the 2nd or more times, if block will be executed
        if (ViewCompat.isAttachedToWindow(mProperties.getTargetView())) {
            initialize();
        } else {
            final ViewTreeObserver viewTreeObserver = mProperties.getTargetView().getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mProperties.getTargetView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    initialize();
                }
            });
        }

        return this;
    }

    private void initialize(){
        // Initialize the modal screen
        initModalScreen();

        // Initialize the dialog
        initDialog();
    }

    private void initModalScreen() {
        mModalScreen = new ModalScreen(mProperties);
        mModalScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enable null click event for avoid click event in covered items
            }
        });

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );

        mParent.addView(mModalScreen, layoutParams);
    }

    private void initDialog() {

        int [] pos = new int[2];
        mProperties.getTargetView().getLocationOnScreen(pos);
        int targetViewY = pos[1];

        final float scale = mProperties.getActivity().getResources().getDisplayMetrics().density;

        int margin40 = (int)(40 * scale + 0.5f);
        int margin16 = (int)(16 * scale + 0.5f);
        int margin8 = (int)(8 * scale + 0.5f);

        LinearLayout.LayoutParams paramsRoot = new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        paramsRoot.setMargins(margin40, margin40 + 100, margin40, margin40 ); // < top > botton

        LinearLayout lineLayoutRoot = new LinearLayout(mProperties.getActivity());
        lineLayoutRoot.setOrientation(LinearLayout.VERTICAL);
        lineLayoutRoot.setGravity((targetViewY > (mParent.getHeight()/2))? Gravity.TOP : Gravity.BOTTOM );
        lineLayoutRoot.setLayoutParams(paramsRoot);

            LinearLayout.LayoutParams paramsDialog = new LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            LinearLayout lineLayoutDialog = new LinearLayout(mProperties.getActivity());
            lineLayoutDialog.setGravity(Gravity.BOTTOM);
            lineLayoutDialog.setOrientation(LinearLayout.VERTICAL);
            lineLayoutDialog.setLayoutParams(paramsDialog);


                // -- Labels -----------------------------------------------------------------------

                LinearLayout.LayoutParams paramsTitle = new LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                );
                paramsTitle.setMargins(0, 0, 0, margin8);
                LinearLayout lineLayoutTitle = new LinearLayout(mProperties.getActivity());
                lineLayoutTitle.setOrientation(LinearLayout.VERTICAL);
                lineLayoutTitle.setLayoutParams(paramsTitle);

                TextView title = new TextView(mProperties.getActivity());
                title.setVisibility(View.VISIBLE);
                title.setText(mProperties.getTitle());
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                title.setTextColor(mProperties.getTitleColor());

                lineLayoutTitle.addView(title);

                TextView message = new TextView(mProperties.getActivity());
                message.setVisibility(View.VISIBLE);
                message.setText(mProperties.getMessage());
                message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                message.setTextColor(mProperties.getMessageColor());

                // -- Buttons ----------------------------------------------------------------------
                LinearLayout.LayoutParams paramsButtons = new LinearLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                );

                paramsButtons.setMargins(margin8, margin16, margin8, margin16);
                LinearLayout lineLayoutButtons = new LinearLayout(mProperties.getActivity());
                lineLayoutButtons.setGravity(Gravity.END);
                lineLayoutButtons.setOrientation(LinearLayout.HORIZONTAL);
                lineLayoutButtons.setLayoutParams(paramsButtons);

                    int hButtons = (int)(35 * scale + 0.5f);
                    LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            hButtons
                    );
                    paramsButton.setMargins(margin8, 0, 0, 0);

                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(mProperties.getBackButtonColor());
                    gd.setCornerRadius(10);

                    Button buttonClose = new Button(mProperties.getActivity());
                    buttonClose.setText(mProperties.getNormalButtonText());
                    buttonClose.setOnClickListener(mProperties.getNormalButtonListener());
                    buttonClose.setBackground(gd);
                    buttonClose.setTextColor(mProperties.getTextButtonColor());
                    buttonClose.setLayoutParams(paramsButton);

                    Button button = new Button(mProperties.getActivity());
                    button.setText(mProperties.getNextButtonText());
                    button.setOnClickListener(mProperties.getNextButtonListener());
                    button.setBackground(gd);
                    button.setTextColor(mProperties.getTextButtonColor());
                    button.setLayoutParams(paramsButton);

                lineLayoutButtons.addView(buttonClose);
                if(mProperties.getNextButtonText() != null && mProperties.getNextButtonListener() != null){
                    lineLayoutButtons.addView(button);
                }


            lineLayoutDialog.addView(lineLayoutTitle);
            lineLayoutDialog.addView(message);
            lineLayoutDialog.addView(lineLayoutButtons);

        lineLayoutRoot.addView(lineLayoutDialog);
        lineLayoutRoot.setLayoutParams(paramsRoot);


        mDialogLayout = lineLayoutRoot;

        mParent.addView(mDialogLayout);
    }


    public void clear(){
        // Remove Modal Screen
        if (mModalScreen !=null) {
            mParent.removeView(mModalScreen);
        }
        // Remove Dialog
        if (mDialogLayout!=null) {
            mParent.removeView(mDialogLayout);
        }
    }


    /**
     * Builder
     *
     *
     */
    public static class Builder { // Builder Patter: The activity value and the screen size don't change in the TutorialScreen instance (immutable).

        private Properties mProperties;

        public Builder(Activity activity) {
            Point screenSize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(screenSize);

            mProperties = new Properties(activity, screenSize);

            // Default Values
            mProperties.setModalColor(Color.parseColor("#e6000000"));
            mProperties.setTargetColor(-1);
        }

        public TutorialScreen create() {
            return new TutorialScreen(mProperties);
        }
    }
}
