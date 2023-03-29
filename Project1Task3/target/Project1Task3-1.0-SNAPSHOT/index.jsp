<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% if (request.getAttribute("doctype") != null) { %>
<%= request.getAttribute("doctype") %>
<% } else { %>
<!DOCTYPE html>
<% } %>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Distributed Systems Class Clicker" %>
</h1>

<% if (request.getAttribute("selected") != null) { %>
<h3>Your "${selected}" response has been registered</h3>
<% } %>

<h3><%= "Submit your answer to the current question:" %>
</h3>

<form action="submit" method="GET">
    <div>
        <input type="radio" id="a" name="input" value="a">
        <label for="a">a</label>
    </div>
    <div>
        <input type="radio" id="b" name="input" value="b">
        <label for="b">b</label>
    </div>

    <div>
        <input type="radio" id="c" name="input" value="c">
        <label for="c">c</label>
    </div>

    <div>
        <input type="radio" id="d" name="input" value="d">
        <label for="d">d</label>
    </div>
    <br/>
    <button type="submit" value="Submit">Submit</button>
</form>
</body>
</html>