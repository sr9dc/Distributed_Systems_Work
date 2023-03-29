package ds;
/*
 * @author Joe Mertz
 *
 * This is a short example of MVC.
 * The welcome-file in the deployment descriptor (web.xml) points
 * to this servlet.  So it is also the starting place for the web
 * application.
 *
 * The servlet is acting as the controller.
 * There are two views - prompt.jsp and result.jsp.
 * It decides between the two by determining if there is a search parameter or
 * not. If there is no parameter, then it uses the prompt.jsp view, as a
 * starting place. If there is a search parameter, then it searches for a
 * picture and uses the result.jsp view.
 * The model is provided by InterestingPictureModel.
 */

import ds.InterestingPictureModel;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/*
 * The following WebServlet annotation gives instructions to the web container.
 * It states that when the user browses to the URL path /getAnInterestingPicture
 * then the servlet with the name InterestingPictureServlet should be used.
 *
 * This is the exact same as putting the following lines in the deployment
 * descriptor, web.xml:
 *  <servlet>
 *      <servlet-name>IPServlet</servlet-name>
 *      <servlet-class>InterestingPicture.InterestingPictureServlet</servlet-class>
 *  </servlet>
 *  <servlet-mapping>
 *      <servlet-name>IPServlet</servlet-name>
 *      <url-pattern>/getAnInterestingPicture</url-pattern>
 *  </servlet-mapping>
 */
@WebServlet(name = "InterestingPictureServlet",
        urlPatterns = {"/getAnInterestingPicture"})
public class InterestingPictureServlet extends HttpServlet {

    InterestingPictureModel ipm = null;  // The "business model" for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        ipm = new InterestingPictureModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // get the search parameter if it exists
        String search = request.getParameter("searchWord");

        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        String nextView;
        /*
         * Check if the search parameter is present.
         * If not, then give the user instructions and prompt for a search string.
         * If there is a search parameter, then do the search and return the result.
         */
        if (search != null) {
            String picSize = (mobile) ? "mobile" : "desktop";
            // use model to do the search and choose the result view
            String pictureURL = ipm.doFlickrSearch(search, picSize);
            /*
             * Attributes on the request object can be used to pass data to
             * the view.  These attributes are name/value pairs, where the name
             * is a String object.  Here the pictureURL is passed to the view
             * after it is returned from the model interestingPictureSize method.
             */
            request.setAttribute("pictureURL",pictureURL);
            // Pass the user search string (pictureTag) also to the view.
            nextView = "result.jsp";
        } else {
            // no search parameter so choose the prompt view
            nextView = "prompt.jsp";
        }
        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}

