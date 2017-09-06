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

/**
 * A tutorial screen for Android.
 * <p>
 * Created by Hery Lopez on 29/08/2017..
 * <p>
 * Github:
 * <p>
 * Author: <a href="https://github.com/HeryLopez/">https://github.com/HeryLopez</a>
 * <br/>Project:  <a href="https://github.com/HeryLopez/TutorialScreen">https://github.com/HeryLopez/TutorialScreen</a>
 */
public class TutorialScreen {

    // Properties
    private Properties mProperties;
    // Modal Screen and indicator circle
    private ModalScreen mModalScreen;
    // Dialog
    private View mDialogLayout;
    // Parent
    private ViewGroup mParent;

    private boolean IsActive = false;

    private TutorialScreen(Properties properties) {
        mProperties = properties;

        mParent = (ViewGroup) mProperties.getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
    }


    /**
     * Text for the title of tutorial screen.
     *
     * @param s title
     * @return TutorialScreen
     */
    public TutorialScreen setTitle(String s) {
        mProperties.setTitle(s);
        return this;
    }

    /**
     * Text for the message of tutorial screen.
     *
     * @param s message
     * @return TutorialScreen
     */
    public TutorialScreen setMessage(String s) {
        mProperties.setMessage(s);
        return this;
    }

    /**
     * Enable/Disable the click event in the target view.
     *
     * @param b Enable/Disable
     * @return TutorialScreen
     */
    public TutorialScreen setClickEventEnable(boolean b) {
        mProperties.setClickEventEnable(b);
        return this;
    }

    /**
     * Style for the target. CIRCULAR_TARGET, RECTANGULAR_TARGET or NO_TARGET
     *
     * @param style Style for the target
     * @return TutorialScreen
     */
    public TutorialScreen setTargetStyle(Properties.TargetStyle style) {
        mProperties.setTargetStyle(style);
        return this;
    }

    /**
     * Initialize the Next Button
     *
     * @param text Label for the button
     * @param listener Implementation for the onClick event
     * @return TutorialScreen
     */
    public TutorialScreen setNextButton(String text, final View.OnClickListener listener) {
        mProperties.setNextButtonText(text);
        mProperties.setNextButtonListener(listener);
        return this;
    }

    /**
     * Change the label next button.
     *
     * @param text Label for the button
     * @return TutorialScreen
     */
    public TutorialScreen setNextButtonText(String text) {
        mProperties.setNextButtonText(text);
        return this;
    }

    /**
     * Initialize the Normal Button
     *
     * @param text Label for the button
     * @param listener Implementation for the onClick event
     * @return TutorialScreen
     */
    public TutorialScreen setNormalButton(String text, final View.OnClickListener listener) {
        mProperties.setNormalButtonText(text);
        mProperties.setNormalButtonListener(listener);
        return this;
    }

    /**
     * Change the label next button.
     *
     * @param text Label for the button
     * @return TutorialScreen
     */
    public TutorialScreen setNormalButtonText(String text) {
        mProperties.setNormalButtonText(text);
        return this;
    }

    /**
     * Color for the tutorial screen background
     *
     * @param color android.graphics.Color;
     * @return TutorialScreen
     */
    public TutorialScreen setModalColor(int color) {
        mProperties.setModalColor(color);
        return this;
    }

    /**
     * Color for the tutorial screen title
     *
     * @param color android.graphics.Color;
     * @return TutorialScreen
     */
    public TutorialScreen setTitleColor(int color) {
        mProperties.setTitleColor(color);
        return this;
    }

    /**
     * Color for the tutorial screen message
     *
     * @param color android.graphics.Color;
     * @return TutorialScreen
     */
    public TutorialScreen setMessageColor(int color) {
        mProperties.setMessageColor(color);
        return this;
    }

    /**
     * Color for the target contour
     *
     * @param color android.graphics.Color;
     * @return TutorialScreen
     */
    public TutorialScreen setTargetColor(int color) {
        mProperties.setTargetColor(color);
        return this;
    }

    /**
     * Color for the buttons background
     *
     * @param color android.graphics.Color;
     * @return TutorialScreen
     */
    public TutorialScreen setBackButtonColor(int color) {
        mProperties.setBackButtonColor(color);
        return this;
    }

    /**
     * Color for the buttons text
     *
     * @param color android.graphics.Color;
     * @return TutorialScreen
     */
    public TutorialScreen setTextButtonColor(int color) {
        mProperties.setTextButtonColor(color);
        return this;
    }

    /**
     * Show/Hide the Next Button
     *
     * @param showNextButton Show/Hide
     * @return TutorialScreen
     */
    public TutorialScreen showNextButton(boolean showNextButton) {
        mProperties.showNextButton(showNextButton);
        return this;
    }

    /*
    public TutorialScreen showNormalButton(boolean showNormalButton) {
        mProperties.showNormalButton(showNormalButton);
        return this;
    }
    */

    /**
     * Indicate if the tutorial screen is in the window
     *
     * @return boolean
     */
    public boolean IsActive(){
       return IsActive;
    }

    /**
     * Get Id (Used in show(view, id)). That allows to identify the View in the OnClick event.
     * @return Id of view
     */
    public String getTargetViewId(){
        return mProperties.getTargetViewId();
    }


    /**
     * Assign the target view and initialize the tutorial screen.
     *
     * @param v Target View
     * @param id Identifier for the view. See getTargetViewId
     * @return TutorialScreen
     */
    public TutorialScreen show(View v, String id) {

        IsActive = true;

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
        int [] pos = new int[2];
        mParent.getLocationOnScreen(pos);
        // frameLayoutWithHole's coordinates are calculated taking full screen height into account
        // but we're adding it to the content area only, so we need to offset it to the same Y value of contentArea

        layoutParams.setMargins(0,-pos[1],0,0);
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
        LinearLayout lineLayoutRoot = new LinearLayout(mProperties.getActivity());
        lineLayoutRoot.setOrientation(LinearLayout.VERTICAL);
        lineLayoutRoot.setGravity((targetViewY > (mParent.getHeight()/2))? Gravity.TOP : Gravity.BOTTOM );
        lineLayoutRoot.setLayoutParams(paramsRoot);
        lineLayoutRoot.setPadding(margin40, margin40 + 100, margin40, margin40 );

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
                if(mProperties.showNextButton()){
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


    /**
     * Clear the tutorial screen from the window
     */
    public void clear(){
        IsActive = false;

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
     * Builder for creation of a tutorial screen instance (Builder Patter).
     */
    public static class Builder { // Builder Patter: The activity value and the screen size don't change in the TutorialScreen instance (immutable).

        private Properties mProperties;

        public Builder(Activity activity) {
            Point screenSize = new Point();
            screenSize.x = activity.getResources().getDisplayMetrics().widthPixels;
            screenSize.y = activity.getResources().getDisplayMetrics().heightPixels;

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
