package com.app.herysapps.tutorialscreens;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.app.herysapps.tutorialscreenslib.TutorialScreen;

public class MainActivity extends AppCompatActivity {

    TutorialScreen tutorialScreen;

    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // --------------------
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tutorialScreen.clear();
            }
        });

        // --------------------
        Button button = (Button) findViewById(R.id.boton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorialScreen.clear();
                tutorialScreen.show(fab, "Fab");
            }
        });


        tutorialScreen = new TutorialScreen.Builder(this).create();
        tutorialScreen.setTitle("Criticism")
                .setMessage("Critics of Yoda conditions see the lack of readability as a disadvantage that outweighs the benefits described above.")
                //.setModalColor(Color.parseColor("#e6000000"))
                //.setTargetColor(Color.parseColor("#3F51B5"))
                .setTitleColor(Color.parseColor("#3F51B5"))
                .setMessageColor(Color.parseColor("#ffffff"))
                .setBackButtonColor(Color.parseColor("#3F51B5"))
                .setTextButtonColor(Color.parseColor("#ffffff"))
                .setClickEventEnable(false)
                .setNextButton("Next", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tutorialScreen.getTargetViewId().equals("Button")) {
                            tutorialScreen.clear();
                            tutorialScreen.setTitle("Advantage");
                            tutorialScreen.setMessage("Placing the constant value in the expression does not change the behavior of the program (unless the values evaluate to false—see below).");
                            tutorialScreen.show(menuItem.getActionView(), "BuBar");
                        } else if (tutorialScreen.getTargetViewId().equals("BuBar")) {
                            tutorialScreen.clear();
                            tutorialScreen.setTitle("Yoda conditions");
                            tutorialScreen.setMessage("In programming jargon, Yoda conditions (also called Yoda notation) is a programming style where the two parts of an expression are reversed from the typical order in a conditional statement.");
                            tutorialScreen.show(fab, "Fab");
                        } else if (tutorialScreen.getTargetViewId().equals("Fab")) {
                            tutorialScreen.clear();
                        }

                    }
                })
                .setNormalButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tutorialScreen.clear();
                    }
                });

        tutorialScreen.show(button, "Button");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.menu, menu);


        TypedValue typedValue = new TypedValue();

        // I used getActivity() as if you were calling from a fragment.
        // You just want to call getTheme() on the current activity, however you can get it
        this.getTheme().resolveAttribute(android.R.attr.actionBarItemBackground, typedValue, true);

        // it's probably a good idea to check if the color wasn't specified as a resource
        if (typedValue.resourceId != 0) {
            ((ImageButton) menu.getItem(0).getActionView()).setBackgroundResource(typedValue.resourceId);
        } else {
            // this should work whether there was a resource id or not
            ((ImageButton) menu.getItem(0).getActionView()).setBackgroundColor(typedValue.data);
        }


        float density = this.getResources().getDisplayMetrics().density;
        int padding48 = (int) (48 * density + 0.5f);
        LinearLayout.LayoutParams layoutParams = new AppBarLayout.LayoutParams(padding48, padding48); // 48 default menu item
        ((ImageButton) menu.getItem(0).getActionView()).setImageDrawable(menu.getItem(0).getIcon());
        ((ImageButton) menu.getItem(0).getActionView()).setLayoutParams(layoutParams);

        /*
        ((ImageButton)menu.getItem(1).getActionView()).setImageDrawable(menu.getItem(1).getIcon());
        ((ImageButton)menu.getItem(1).getActionView()).setBackground(drawableFromTheme);
        ((ImageButton)menu.getItem(1).getActionView()).setPadding(padding,padding,padding,padding);
        */

        menuItem = menu.findItem(R.id.action_settings);
        return true;
    }


}
