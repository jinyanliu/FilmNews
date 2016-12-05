package se.sugarest.jane.filmnews;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jane on 12/5/16.
 */

public class FilmNewsAdapter extends ArrayAdapter<FilmNews> {

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
        holder.publishDateTextView = (TextView)convertView.findViewById(R.id.publish_date);
        holder.publishDateTextView.setText(currentFilmNews.getPublishDate());

        convertView.setTag(holder);

        //Return the list_item view layout (containing 3 TextViews)
        //so that it can be shown in the ListView
        return convertView;
    }
}
