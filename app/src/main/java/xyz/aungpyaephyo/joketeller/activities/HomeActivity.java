package xyz.aungpyaephyo.joketeller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import xyz.aungpyaephyo.joketeller.R;
import xyz.aungpyaephyo.joketeller.fragments.JokeFragment;
import xyz.aungpyaephyo.joketeller.utils.JokeTellerConstants;
import xyz.aungpyaephyo.joketeller.utils.MMFontUtils;

public class HomeActivity extends AppCompatActivity {

    private int jokeIndex = -1;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView tvSearchGuide;
    private FrameLayout flContainer;
    private Button btnNextJoke;
    private Button btnPreviousJoke;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Menu leftMenu = navigationView.getMenu();
        MMFontUtils.applyMMFontToMenu(leftMenu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
                Snackbar.make(flContainer, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        tvSearchGuide = (TextView) findViewById(R.id.tv_search_jokes);
        flContainer = (FrameLayout) findViewById(R.id.fl_container);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (savedInstanceState == null) {
            jokeIndex++;
            JokeFragment fragment = JokeFragment.newInstance(jokeIndex);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();

        }

        btnNextJoke = (Button) findViewById(R.id.btn_next_joke);
        btnNextJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jokeIndex++;
                if (jokeIndex < JokeTellerConstants.TOTAL_JOKES) {
                    JokeFragment fragment = JokeFragment.newInstance(jokeIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_container, fragment)
                            .commit();

                } else {
                    jokeIndex = JokeTellerConstants.TOTAL_JOKES - 1;
                    Toast.makeText(getApplicationContext(), R.string.msg_no_more_joke, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPreviousJoke = (Button) findViewById(R.id.btn_previous_joke);
        btnPreviousJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jokeIndex--;
                if (jokeIndex >= 0) {
                    JokeFragment fragment = JokeFragment.newInstance(jokeIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_container, fragment)
                            .commit();
                } else {
                    jokeIndex = 0;
                    Toast.makeText(getApplicationContext(), R.string.msg_no_more_joke, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                tvSearchGuide.setVisibility(View.VISIBLE);
                flContainer.setVisibility(View.INVISIBLE);
                btnNextJoke.setVisibility(View.INVISIBLE);
                btnPreviousJoke.setVisibility(View.INVISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                tvSearchGuide.setVisibility(View.INVISIBLE);
                flContainer.setVisibility(View.VISIBLE);
                btnNextJoke.setVisibility(View.VISIBLE);
                btnPreviousJoke.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        } else {
            Snackbar.make(flContainer, "ShareActionProvider is being null. Why ?", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_love:
                Toast.makeText(getApplicationContext(), getString(R.string.lbl_love_the_joke), Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static Intent createShareIntent(){
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("text/*");
        myShareIntent.putExtra(Intent.EXTRA_TEXT, "Hello Share Action Provider!");
        return myShareIntent;
    }
}
