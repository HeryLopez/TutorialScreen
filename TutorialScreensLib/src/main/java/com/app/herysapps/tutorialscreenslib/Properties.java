package com.app.herysapps.tutorialscreenslib;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;

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
public class Properties {

    public enum TargetStyle {
        CIRCULAR_TARGET, RECTANGULAR_TARGET, NO_TARGET
    }

    private final Activity activity;
    private final Point screenSize;

    private View targetView;
    private String targetViewId;

    private String title, message;

    private int modalColor, targetColor, titleColor, messageColor, backButtonColor, textButtonColor;

    private boolean IsClickEventEnable = false;

    private TargetStyle targetStyle;

    private boolean showNextButton = false;
    private boolean showNormalButton = false;
    private String nextButtonText, normalButtonText;
    private View.OnClickListener nextButtonListener, normalButtonListener;


    Properties(Activity activity, Point screenSize) {
        this.activity = activity;
        this.screenSize = screenSize;
    }

    Activity getActivity() {
        return activity;
    }


    View getTargetView() {
        return this.targetView;
    }

    void setTargetView(View targetView) {
        this.targetView = targetView;
    }


    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }


    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }


    int getModalColor() {
        return modalColor;
    }

    void setModalColor(int modalColor) {
        this.modalColor = modalColor;
    }


    int getTargetColor() {
        return targetColor;
    }

    void setTargetColor(int targetColor) {
        this.targetColor = targetColor;
    }


    int getTitleColor() {
        return titleColor;
    }

    void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }


    int getMessageColor() {
        return messageColor;
    }

    void setMessageColor(int messageColor) {
        this.messageColor = messageColor;
    }


    int getBackButtonColor() {
        return backButtonColor;
    }

    void setBackButtonColor(int backButtonColor) {
        this.backButtonColor = backButtonColor;
    }


    int getTextButtonColor() {
        return textButtonColor;
    }

    void setTextButtonColor(int textButtonColor) {
        this.textButtonColor = textButtonColor;
    }


    boolean isClickEventEnable() {
        return IsClickEventEnable;
    }

    void setClickEventEnable(boolean clickEventEnable) {
        IsClickEventEnable = clickEventEnable;
    }


    TargetStyle getTargetStyle() {
        return targetStyle;
    }

    void setTargetStyle(TargetStyle targetStyle) {
        this.targetStyle = targetStyle;
    }

    String getNextButtonText() {
        return nextButtonText;
    }

    void setNextButtonText(String nextButtonText) {
        this.nextButtonText = nextButtonText;
    }


    String getNormalButtonText() {
        return normalButtonText;
    }

    void setNormalButtonText(String normalButtonText) {
        this.normalButtonText = normalButtonText;
    }


    View.OnClickListener getNextButtonListener() {
        return nextButtonListener;
    }

    void setNextButtonListener(View.OnClickListener nextButtonListener) {
        this.nextButtonListener = nextButtonListener;
    }


    View.OnClickListener getNormalButtonListener() {
        return normalButtonListener;
    }

    void setNormalButtonListener(View.OnClickListener normalButtonListener) {
        this.normalButtonListener = normalButtonListener;
    }


    String getTargetViewId() {
        return targetViewId;
    }

    void setTargetViewId(String targetViewId) {
        this.targetViewId = targetViewId;
    }


    Point getScreenSize() {
        return screenSize;
    }



    public boolean showNextButton() {
        return showNextButton;
    }

    public void showNextButton(boolean showNextButton) {
        this.showNextButton = showNextButton;
    }

    public boolean showNormalButton() {
        return showNormalButton;
    }

    public void showNormalButton(boolean showNormalButton) {
        this.showNormalButton = showNormalButton;
    }

}
