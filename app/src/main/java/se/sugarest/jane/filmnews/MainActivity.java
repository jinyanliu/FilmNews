package se.sugarest.jane.filmnews;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<FilmNews>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * URL for filmNews data from the Guardian dataset
     */
    private static final String FILMNEWS_REQUEST_URL =
            "http://content.guardianapis.com/search?q=films&api-key=test";

    /**
     * Constant value for the filmNews loader ID. Can choose any integer.
     * This really only comes into play if using multiple loaders.
     */
    private static final int FILMNEWS_LOADER_ID = 1;

    /**
     * Adapter for the list of filmNewses
     */
    private FilmNewsAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Override
    public Loader<List<FilmNews>> onCreateLoader(int id, Bundle args) {
        //Create a new loader for the given URL
        Log.i(LOG_TAG, "onCreateLoader");
        return new FilmNewsLoader(this, FILMNEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<FilmNews>> loader, List<FilmNews> filmNewses) {
        //Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No Film News found."
        mEmptyStateTextView.setText(R.string.no_filmNews);

        // Clear the adapter of previous filmNews data
        mAdapter.clear();
        Log.i(LOG_TAG, "onLoadFinished");

        // If there is a valid list of {@link FilmNews}es, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (filmNewses != null && !filmNewses.isEmpty()) {
            mAdapter.addAll(filmNewses);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<FilmNews>> loader) {
        //Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        Log.i(LOG_TAG, "onLoadReset");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find a reference to the {@Link ListView} in the layout
        ListView filmNewsListView = (ListView) findViewById(R.id.list);

        //Create a new adapter that takes an empty list of filmNews as input
        mAdapter = new FilmNewsAdapter(this, new ArrayList<FilmNews>());

        //Set the adapter on the {@Link ListView}
        //So the list can be populated in the user interface
        filmNewsListView.setAdapter(mAdapter);

        //Set an item click listener on the ListView, which sends an intent to a web browser
        //to open a website with more information about the selected filmNews.
        filmNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Find the current filmNews that was clicked on
                FilmNews currentFilmNews = mAdapter.getItem(position);
                //Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri filmNewsUri = Uri.parse(currentFilmNews.getUrl());
                //Create a new intent to view the filmNews URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, filmNewsUri);
                //Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        filmNewsListView.setEmptyView(mEmptyStateTextView);

        //Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            //Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            //Initialize the loader. Pass in the int ID constant defined above and pass in null for
            //the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            //because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(FILMNEWS_LOADER_ID, null, this);
            Log.i(LOG_TAG, "initLoader");
        } else {
            //Otherwise, display error
            //First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            //Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet);
        }

    }

}
