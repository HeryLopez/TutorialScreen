package com.app.herysapps.tutorialscreens;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.herysapps.tutorialscreens.dummy.DummyContent;
import com.app.herysapps.tutorialscreenslib.Properties;
import com.app.herysapps.tutorialscreenslib.TutorialScreen;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private TutorialScreen tutorialScreen;

    private MenuItem menuItem;

    private boolean mTwoPane;

    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = this.findViewById(R.id.main_layout);

        Toolbar toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final int modalColor01 = Color.parseColor("#e6000000");
        final int modalColor02 = Color.parseColor("#E6001A15");

        final int detailsColor01 = Color.parseColor("#3F51B5");
        final int detailsColor02 = Color.parseColor("#ff4040");

        final String title01 = "Welcomes to Tutorial Screen";
        final String text01 = "A simple Tutorial Screen library for your applications\n\nBackground windows default, no target, title in blue, text in white, background buttons in blue and text buttons in white";

        final String title02 = "Tutorial Screen in Normal Buttons";
        final String text02 = "Background windows default, target in normal button, circular target, target in blue, target events are disabled, title in blue, text in white, background buttons in blue and text buttons in white";

        final String title03 = "Tutorial Screen in Floating Buttons";
        final String text03 = "Background windows default, target in floating button, circular target, target without color, target events are enabled, title in blue, text in white, background buttons in blue, text buttons in white and next button is hidden\n\nClick in Floating Buttonto continue";

        final String title04 = "Tutorial Screen in MenuItems";
        final String text04 = "Background windows green, target in MenuItem, circular target, target without color, target events are disabled, title in red, text in white, background buttons in red and text buttons in white";

        final String title05 = "Tutorial Screen in Navigator View";
        final String text05 = "Background windows green, target in Navigator View, rectangular target, target without color, target events are disabled, title in red, text in white, background buttons in red and text buttons in white";

        final String title06 = "Tutorial Screen in Recycler View";
        final String text06 = "Background windows green, target in Recycler View, rectangular target, target in red, target events are disabled, title in red, text in white, background buttons in red, text buttons in white, next button is hidden and cancel button label is changed by CLOSE";



        // -- FloatingActionButton -----------------------------------------------------------------
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tutorialScreen.IsActive()) {
                    tutorialScreen.clear();
                    tutorialScreen.setTitle(title04);
                    tutorialScreen.setMessage(text04);
                    tutorialScreen.setModalColor(modalColor02);
                    tutorialScreen.setTitleColor(detailsColor02);
                    tutorialScreen.setBackButtonColor(detailsColor02);
                    tutorialScreen.showNextButton(true);
                    tutorialScreen.setClickEventEnable(false);
                    tutorialScreen.show(menuItem.getActionView(), "Menu");

                    mDrawerLayout.openDrawer(GravityCompat.START);
                } else {
                    Snackbar.make(root, "Fab Button clicked", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        // -- Button -------------------------------------------------------------------------------
        final Button button = (Button) findViewById(R.id.boton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(root, "Button clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // -- Reset Button -------------------------------------------------------------------------
        final Button buttonReset = (Button) findViewById(R.id.reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                tutorialScreen.setTitle(title01);
                tutorialScreen.setMessage(text01);
                tutorialScreen.setModalColor(modalColor01);
                tutorialScreen.showNextButton(true);
                tutorialScreen.setTargetColor(detailsColor01);
                tutorialScreen.setBackButtonColor(detailsColor01);
                tutorialScreen.setTitleColor(detailsColor01);
                tutorialScreen.setNormalButtonText("Cancel");
                tutorialScreen.setTargetStyle(Properties.TargetStyle.NO_TARGET);
                tutorialScreen.setClickEventEnable(false);
                tutorialScreen.show(root, "Root");
            }
        });

        // -- Navigation View ----------------------------------------------------------------------
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        /*
        // When the first element must be the NavigatorView
        navigationView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
            }
        });
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                View viewById = ((ViewGroup)((ViewGroup)root.findViewById(R.id.nav_view)).getChildAt(0)).getChildAt(3);
                tutorialScreen.show(viewById, "ButtonNav");
            }
        });
        */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Snackbar.make(root, "Navigation View. Element clicked : " + item.getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                /*
                if(item.getItemId() == R.id.navAccounts)
                    tutorialScreen.show(((NavigationView) mDrawerLayout.findViewById(R.id.nav_view)).getTouchables().get(0), "Button");
                if(item.getItemId() == R.id.navBudgets)
                    tutorialScreen.show(((NavigationView) mDrawerLayout.findViewById(R.id.nav_view)).getTouchables().get(1), "Button");
                if(item.getItemId() == R.id.navCategories)
                    tutorialScreen.show(((NavigationView) mDrawerLayout.findViewById(R.id.nav_view)).getTouchables().get(2), "Button");
                */
                return false;
            }
        });

        // -- RecyclerView -------------------------------------------------------------------------
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        // -- TutorialScreen -----------------------------------------------------------------------
        tutorialScreen = new TutorialScreen.Builder(this).create();
        tutorialScreen
                .setTitle(title01)
                .setMessage(text01)
                .setModalColor(modalColor01)
                .setTargetColor(detailsColor01)
                .showNextButton(true)
                .setTitleColor(detailsColor01)
                .setMessageColor(Color.parseColor("#ffffff"))
                .setTargetStyle(Properties.TargetStyle.NO_TARGET)
                .setBackButtonColor(detailsColor01)
                .setTextButtonColor(Color.parseColor("#ffffff"))
                .setClickEventEnable(false)
                .setNextButton("Next", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Valid from where was called
                        if (tutorialScreen.getTargetViewId().equals("Root")) {
                            tutorialScreen.clear();
                            tutorialScreen.setTitle(title02);
                            tutorialScreen.setMessage(text02);
                            tutorialScreen.setTargetStyle(Properties.TargetStyle.CIRCULAR_TARGET);
                            tutorialScreen.show(button, "Button");

                        } else if (tutorialScreen.getTargetViewId().equals("Button")) {
                            tutorialScreen.clear();
                            tutorialScreen.setTargetColor(-1);
                            tutorialScreen.showNextButton(false);
                            tutorialScreen.setTitle(title03);
                            tutorialScreen.setMessage(text03);
                            tutorialScreen.setClickEventEnable(true);
                            tutorialScreen.show(fab, "Fab");

                        } else if (tutorialScreen.getTargetViewId().equals("Fab")) {
                            // Do nothing. See onClick event of Fab Button

                        } else if (tutorialScreen.getTargetViewId().equals("Menu")) {
                            View viewById = ((ViewGroup) ((ViewGroup) root.findViewById(R.id.nav_view)).getChildAt(0)).getChildAt(3);

                            tutorialScreen.clear();
                            tutorialScreen.setTargetColor(-1);
                            tutorialScreen.setTitle(title05);
                            tutorialScreen.setMessage(text05);
                            tutorialScreen.setTargetStyle(Properties.TargetStyle.RECTANGULAR_TARGET);
                            tutorialScreen.show(viewById, "NavigatorView");

                        } else if (tutorialScreen.getTargetViewId().equals("NavigatorView")) {
                            mDrawerLayout.closeDrawer(GravityCompat.START);

                            tutorialScreen.clear();
                            tutorialScreen.showNextButton(false);
                            tutorialScreen.setTargetColor(detailsColor02);
                            tutorialScreen.setTitleColor(detailsColor02);
                            tutorialScreen.setBackButtonColor(detailsColor02);
                            tutorialScreen.setNormalButtonText("Close");
                            tutorialScreen.setTitle(title06);
                            tutorialScreen.setMessage(text06);
                            tutorialScreen.show(((RecyclerView) recyclerView).getChildAt(0), "RecyclerView");

                        } else if (tutorialScreen.getTargetViewId().equals("RecyclerView")) {
                            // Do nothing. Hide the next button.
                        }
                    }
                })
                .setNormalButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tutorialScreen.clear();
                    }
                });

        tutorialScreen.show(root, "Root");
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

        menuItem = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_menu_01:
                Snackbar.make(root, "Menu Item. Element clicked : 01", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case R.id.action_menu_02:
                Snackbar.make(root, "Menu Item. Element clicked : 02", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {

                        Snackbar.make(v.getRootView(), "Recycler View. Element clicked : " + holder.mItem.content, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }


        }
    }
}
