<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>


<html>
    <head>
        <title>Interesting Picture</title>
    </head>
    <body>
        <% if (request.getAttribute("pictureURL") != null) { %>
            <% if (!request.getParameter("searchWord").toString().toLowerCase().equals("andy")){ %>
                <h1>Here is an interesting picture of a <%= request.getParameter("searchWord")%></h1><br>
                <img src="<%= request.getAttribute("pictureURL")%>"><br><br>
            <% } else { %>
                <h1>Here is an interesting picture of a <%= request.getParameter("searchWord")%></h1><br>
                <% for(int i = 0; i < 10; i++) { %>
                    <img src="https://upload.wikimedia.org/wikipedia/commons/0/09/Andrew_Carnegie%2C_by_Theodore_Marceau.jpg" width="50" height="50"><br><br>
                <% } %>
            <% } %>
        <% } else { %>
        <h1>An interesting picture of a <%= request.getParameter("searchWord")%> could not be found</h1><br>
        <% } %>
        <form action="getAnInterestingPicture" method="GET">
            <label for="letter">Type another word.</label>
            <input type="text" name="searchWord" value="" /><br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>