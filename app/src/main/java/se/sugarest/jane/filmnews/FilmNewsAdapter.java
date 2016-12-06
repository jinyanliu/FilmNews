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

public class FilmNewsAdapter extends ArrayAdapter<FilmNews> {

    private static final String DATE_SEPARATOR = "T";

    String publishDatePartOne;

    String formattedDate;

    /**
     * This is my own custom constructor(it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data I want to populate into the list.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param filmNews A list of FilmNews objects to display in a list.
     */
    public FilmNewsAdapter(Activity context, ArrayList<FilmNews> filmNews){
        super(context, 0, filmNews);
    }

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
        holder.articleTitleTextView = (TextView)convertView.findViewById(R.id.article_title);
        holder.articleTitleTextView.setText(currentFilmNews.getArticleTitle());
        holder.sectionNameTextView = (TextView)convertView.findViewById(R.id.section_name);
        holder.sectionNameTextView.setText(currentFilmNews.getSectionName());

        String[] parts = currentFilmNews.getPublishDate().split(DATE_SEPARATOR);
        publishDatePartOne = parts[0];

        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputDateFormat = new SimpleDateFormat("MMM d, yyyy");

        String inputDateString = publishDatePartOne;

//        Date date = inputDateFormat.parse(inputDateString);
//        String outputDateString = outputDateFormat.format(date);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");

        try {
//            Date publishDate = dateFormat.parse(publishDatePartOne);
//            formattedDate = formatDate(publishDate);
            Date date = inputDateFormat.parse(inputDateString);
            formattedDate = outputDateFormat.format(date);
        } catch (java.text.ParseException e) {
            Log.e(LOG_TAG, "Problem formatting Date Object.", e);
        }

        holder.publishDateTextView = (TextView)convertView.findViewById(R.id.publish_date);
        holder.publishDateTextView.setText(formattedDate);

        convertView.setTag(holder);

        //Return the list_item view layout (containing 3 TextViews)
        //so that it can be shown in the ListView
        return convertView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
        return dateFormat.format(dateObject);
    }
}
