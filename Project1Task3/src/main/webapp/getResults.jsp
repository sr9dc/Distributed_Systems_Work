<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%= request.getAttribute("doctype") %>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Distributed Systems Class Clicker" %>
</h1>

<% int a; %>
<% int b; %>
<% int c; %>
<% int d; %>

<% if (request.getAttribute("goToResults").equals("true")) { %>
<% a = Integer.valueOf((String) request.getAttribute("aCount")); %>
<% b = Integer.valueOf((String) request.getAttribute("bCount")); %>
<% c = Integer.valueOf((String) request.getAttribute("cCount")); %>
<% d = Integer.valueOf((String) request.getAttribute("dCount")); %>
<h3><%= "The results from the survey are as follows: " %></h3>
<h3>A: <%= String.valueOf(a)%></h3>
<h3>B: <%= String.valueOf(b)%></h3>
<h3>C: <%= String.valueOf(c)%></h3>
<h3>D: <%= String.valueOf(d)%></h3>
<% } else { %>
<h3><%= "There are currently no results" %></h3><br/>
<% } %>

</body>
</html>