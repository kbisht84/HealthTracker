package com.kanakb.healthtracker;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.kanakb.healthtracker.ApiClient.BookClient;
import com.kanakb.healthtracker.Model.Book;
import com.kanakb.healthtracker.adapter.BookAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Search extends AppCompatActivity {

    private ListView listView;
    private Menu menu;
    private ArrayList<String> items;
    private NavigationView nvDrawer;

    private ArrayAdapter<String> mAdapter;

    private ListView lvBooks;
    private BookAdapter bookAdapter;
    private ProgressBar progress;

    private ActionBarDrawerToggle drawerToggle;
    private String mActivityTitle;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private CustomAdapter aptr;

    private BookClient client;

    ArrayAdapter<String>adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search);

        progress = (ProgressBar) findViewById(R.id.progress);

        lvBooks = (ListView) findViewById(R.id.lvBooks);

        ArrayList<Book> aBooks = new ArrayList<>();
        bookAdapter = new BookAdapter(this, aBooks);
        lvBooks.setAdapter(bookAdapter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Menu Drawer
        //drawerToggle = setupDrawerToggle();
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        //listView = (ListView) findViewById(R.id.listView);

        //listView.setAdapter(adapter);
        items=new ArrayList<>();
        items.add("Man utd");
        items.add("Man city");
        items.add("Chelsea");
        items.add("Arsenal");
        items.add("Liverpool");
        items.add("TottemHam");
        setupDrawer();
        // Setup drawer view
        setupDrawerContent(nvDrawer);


    }

    private void fetchBooks(String query) {

        progress.setVisibility(ProgressBar.VISIBLE);
        client = new BookClient();
        client.getBooks(query, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("docs");
                        // Parse json array into array of model objects
                        final ArrayList<Book> books = Book.fromJson(docs);
                        // Remove all books from the adapter
                        bookAdapter.clear();
                        // Load model objects into the adapter
                        for (Book book : books) {
                            bookAdapter.add(book); // add book through the adapter
                        }
                        bookAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }


        });
    }


    private void setupDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);

                boolean isOpened = mDrawer.isDrawerOpen(GravityCompat.START);
                boolean isVisible = mDrawer.isDrawerVisible(GravityCompat.START);

                if (!isOpened && !isVisible) {
                    if (newState == DrawerLayout.STATE_IDLE) {
                        // drawer just hid completely
                        restoreActionBar();
                    } else {
                        // } else if (newState == DrawerLayout.STATE_SETTLING) {
                        // drawer just entered screen
                        overrideActionBar();
                    }
                }
            }

            private void restoreActionBar() {
                supportInvalidateOptionsMenu();
            }

            private void overrideActionBar() {
                supportInvalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        mDrawer.setDrawerListener(drawerToggle);


    }


   /* private ActionBarDrawerToggle setupDrawerToggle() {


        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }*/

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        try {


            Fragment fragment = null;

            Class fragmentClass;
            switch (menuItem.getItemId()) {
                case R.id.nav_first_fragment:
                    fragmentClass = FirstFragment.class;
                    break;
                case R.id.nav_second_fragment:
                    fragmentClass = SecondFragment.class;
                    break;
                case R.id.nav_third_fragment:
                    fragmentClass = ThirdFragment.class;
                    break;
                default:
                    fragmentClass = FirstFragment.class;
            }
            fragment = (Fragment) fragmentClass.newInstance();


            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }




    //Search TextField
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        this.menu = menu;
        final MenuItem searchItem = menu.findItem(R.id.menu_search);
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView =
//                (SearchView) menu.findItem(R.id.menu_search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//
//        String[] columns = new String[]{"_id", "text"};
//        Object[] temp = new Object[] { 0, "default" };
//        final MatrixCursor cursor = new MatrixCursor(columns);
//        for (int i = 0; i < items.size(); i++) {
//            temp[0] = i;
//            temp[1] = items.get(i);
//            cursor.addRow(temp);
//        }
//        aptr = new CustomAdapter(Search.this, cursor, items);
//        searchView.setSuggestionsAdapter(aptr);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                if (s.length() > 2)
//                    aptr.getFilter().filter(s.toString());
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String arg0) {
//                if (!TextUtils.isEmpty(arg0)) {
//                    aptr.getFilter().filter(arg0.toString());
//                    return true;
//                }
//                return false;
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                fetchBooks(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                Search.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



        return true;
    }







}