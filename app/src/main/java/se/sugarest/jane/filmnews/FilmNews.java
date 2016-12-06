package se.sugarest.jane.filmnews;

/**
 * Created by jane on 12/5/16.
 */

/**
 * Represents an FilmNews article.
 * It contains the article title, section name, publish date and detail webUrl of an article.
 */
public class FilmNews {

    /**
     * Title of the article
     */
    private String mArticleTitle;

    /**
     * Name of the section
     */
    private String mSectionName;

    /**
     * Publish date of the article
     */
    private String mPublishDate;

    /**
     * Website URL of the article
     */
    private String mUrl;

    /**
     * Constructs a new {@link FilmNews} object.
     *
     * @param articleTitle is the title of the article.
     * @param sectionName  is the name of the section.
     * @param publicDate   is the publish date of the article.
     * @param url          is the website url of the article.
     */
    public FilmNews(String articleTitle, String sectionName, String publicDate, String url) {
        mArticleTitle = articleTitle;
        mSectionName = sectionName;
        mPublishDate = publicDate;
        mUrl = url;
    }

    /**
     * Get the title of the article
     */
    public String getArticleTitle() {
        return mArticleTitle;
    }

    /**
     * Get the name of the section
     */
    public String getSectionName() {
        return mSectionName;
    }

    /**
     * Get the publish date of the article
     */
    public String getPublishDate() {
        return mPublishDate;
    }

    /**
     * Get the website url of the article
     */
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String toString() {
        return "FilmNews{" +
                "mArticleTitle='" + mArticleTitle + '\'' +
                ", mSectionName='" + mSectionName + '\'' +
                ", mPublishDate='" + mPublishDate + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
