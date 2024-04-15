package uob.oop;

import java.util.Locale;

public class HtmlParser {
    /***
     * Extract the title of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the title if it's been found. Otherwise, return "Title not found!".
     */
    public static String getNewsTitle(String _htmlCode) {
        if(_htmlCode.indexOf("<title>") == -1)
        {
            return "Title not found!";
        }
        else
        {
        String firstSubstring = _htmlCode.substring(_htmlCode.indexOf("<title>"),_htmlCode.indexOf("</title>"));
        String titleSubsting = firstSubstring.substring((firstSubstring.indexOf(">")+1),(firstSubstring.indexOf("|")-1));
        return titleSubsting;

        }
    }

    /***
     * Extract the content of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the content if it's been found. Otherwise, return "Content not found!".
     */
    public static String getNewsContent(String _htmlCode) {
        if(_htmlCode.indexOf("\"articleBody\":") == -1)
        {
            return "Content not found!";
        }
        else
        {
        String stringOfArticleBody = "\"articleBody\": \"";
        String newsContent = _htmlCode.substring((_htmlCode.indexOf(stringOfArticleBody)+(stringOfArticleBody.length()))
                ,(_htmlCode.indexOf(" \",\"mainEntityOfPage\"")));
        return newsContent.toLowerCase();
        }

    }


}
