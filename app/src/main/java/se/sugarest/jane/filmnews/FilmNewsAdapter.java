package se.sugarest.jane.filmnews;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static se.sugarest.jane.filmnews.MainActivity.LOG_TAG;

/**
 * Created by jane on 12/5/16.
 */

/**
 * FilmNewsAdapter is an ArrayAdapter that can provide the layout for the list
 * based on a data source, whish is a list of FilmNews objects.
 */
public class FilmNewsAdapter extends ArrayAdapter<FilmNews> {

    private static final String DATE_SEPARATOR = "T";

    String publishDatePartOne;

    String formattedDate;

    /**
     * This is my own custom constructor(it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data I want to populate into the list.
     *
     * @param context  The current context. Used to inflate the layout file.
     * @param filmNews A list of FilmNews objects to display in a list.
     */
    public FilmNewsAdapter(Activity context, ArrayList<FilmNews> filmNews) {
        super(context, 0, filmNews);
    }

    /**
     * Provides a view for an AdapterView(ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check is the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //Get the FilmNews object located at this position in the list
        FilmNews currentFilmNews = getItem(position);

        ViewHolder holder = new ViewHolder();
        //Find the TextView in the list_item.xml layout with the ID article_title
        holder.articleTitleTextView = (TextView) convertView.findViewById(R.id.article_title);
        //Display the title of the current article in that TextView.
        holder.articleTitleTextView.setText(currentFilmNews.getArticleTitle());
        //Find the TextView in the list_item.xml layout with the ID section_name
        holder.sectionNameTextView = (TextView) convertView.findViewById(R.id.section_name);
        //Display the section name of the current article in that TextView.
        holder.sectionNameTextView.setText(currentFilmNews.getSectionName());

        //Split the publish date string of the current article from DATE_SEPARATOR
        String[] parts = currentFilmNews.getPublishDate().split(DATE_SEPARATOR);
        //Get only the first part, date information.
        publishDatePartOne = parts[0];

        //Format date format
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputDateFormat = new SimpleDateFormat("MMM d, yyyy");
        String inputDateString = publishDatePartOne;
        try {
            Date date = inputDateFormat.parse(inputDateString);
            formattedDate = outputDateFormat.format(date);
        } catch (java.text.ParseException e) {
            Log.e(LOG_TAG, "Problem formatting Date Object.", e);
        }

        //Find the TextView in the list_item.xml layout with the ID publish_date
        holder.publishDateTextView = (TextView) convertView.findViewById(R.id.publish_date);
        //Display the formatted publish date of the current article in that TextView. (i.e. "Mar 3, 2016")
        holder.publishDateTextView.setText(formattedDate);

        convertView.setTag(holder);

        //Return the list_item view layout (containing 3 TextViews)
        //so that it can be shown in the ListView
        return convertView;
    }
}
