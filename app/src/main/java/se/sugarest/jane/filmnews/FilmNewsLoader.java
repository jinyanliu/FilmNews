package se.sugarest.jane.filmnews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by jane on 12/5/16.
 */

/**
 * Loads a list of filmNews by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class FilmNewsLoader extends AsyncTaskLoader<List<FilmNews>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = FilmNewsLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link FilmNewsLoader}
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public FilmNewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.i(LOG_TAG, "onStartLoading");
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<FilmNews> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        Log.i(LOG_TAG, "loadInBackground");

        //Perform the network request, parse the response, and extract a list of filmNewses.
        List<FilmNews> filmNewses = QueryUtils.fetchFilmNewsData(mUrl);
        return filmNewses;
    }
}
