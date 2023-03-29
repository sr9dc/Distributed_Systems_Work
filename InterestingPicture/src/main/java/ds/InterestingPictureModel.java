package ds;

/*
 * @author Joe Mertz
 * 
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * making a request to flickr.com and then screen scraping the HTML that is
 * returned in order to fabricate an image URL.
 */
//import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterestingPictureModel {

    /**
     * Arguments.
     *
     * @param searchTag The tag of the photo to be searched for.
     * @param picSize The string "mobile" or "desktop" indicating the size of
     * photo requested.
     */
    public String doFlickrSearch(String searchTag, String picSize) 
            throws UnsupportedEncodingException  {
        /*
         * URL encode the searchTag, e.g. to encode spaces as %20
         *
         * There is no reason that UTF-8 would be unsupported.  It is the
         * standard encoding today.  So if it is not supported, we have
         * big problems, so don't catch the exception.
         */
        searchTag = URLEncoder.encode(searchTag, "UTF-8");
 
        String response = "";

        // Create a URL for the page to be screen scraped
        String flickrURL =
                "https://www.flickr.com/search/?text="
                + searchTag
                +"&license=2%2C3%2C4%2C5%2C6%2C9";
        
        // Fetch the page
        response = fetch(flickrURL);

        /*
         * Search the page to find the picture URL
         *
         * Screen scraping is an art that requires creatively looking at the
         * HTML that is returned and figuring out how to cut out the data that 
         * is important to you.
         *
         * These particular searches were crafted by carefully looking at 
         * the HTML that Flickr returned, and finding (by experimentation) the
         * generalizable steps that will reliably get a picture URL.
         * 
         * First do a String search that gets me close to the picture URL target
         */

        // Several small steps
        int cutLeft = response.indexOf("main search-photos-results");
        cutLeft = response.indexOf("photo-list-photo-container", cutLeft);
        String s = "loading=\"lazy\" src=";
        cutLeft = response.indexOf("loading=\"lazy", cutLeft) + s.length() + 1;

        // If not found, then no such photo is available.
        if (cutLeft == -1) {
            System.out.println("pictureURL= null");
            return (String) null;
        }

        // Look for the jpg extension
        int cutRight = response.indexOf("jpg", cutLeft) + 3;

        // Now snip out the part from positions cutLeft to cutRight
        // and prepend the protocol (i.e. https).
        String pictureURL = "https:"+response.substring(cutLeft, cutRight);
        pictureURL = interestingPictureSize(pictureURL, picSize);
        return pictureURL;
    }

    /*
     * Return a URL of an image of appropriate size
     * 
     * Arguments
     * @param picSize The string "mobile" or "desktop" indicating the size of
     * photo requested.
     * @return The URL an image of appropriate size.
     */
    private String interestingPictureSize(String pictureURL, String picSize) {
        int finalDot = pictureURL.lastIndexOf(".");
        /*
         * From the flickr online documentation, an underscore and a letter 
         * before the final "." and file extension is a size indicator.  
         * "_m" for small and "-z" for big.
         */
        String sizeLetter = (picSize.equals("mobile")) ? "m" : "z";
        if (pictureURL.indexOf("_", finalDot-2) == -1) {
            // If the URL currently did not have a _? size indicator, add it.
            return (pictureURL.substring(0, finalDot) + "_" + sizeLetter
                + pictureURL.substring(finalDot));
        } else {
            // Else just change it
            return (pictureURL.substring(0, finalDot - 1) + sizeLetter
                + pictureURL.substring(finalDot));
        }
    }

    /*
     * Make an HTTP request to a given URL
     * 
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which 
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
            // Do something reasonable.  This is left for students to do.
        }
        return response;
    }
}
