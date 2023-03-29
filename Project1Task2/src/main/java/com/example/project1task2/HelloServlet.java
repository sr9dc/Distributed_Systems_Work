package com.example.project1task2;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import org.jsoup.*;


import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String state = request.getParameter("states");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println("<h1>State: " + state + "</h1>");

        out.println("<h2>Population: " + population(state) + "</h2>");

        out.println("<h2>Nickname: " + nickname(state) + "</h2>");

        out.println("<h2>Capital: " + capital(state) + "</h2>");

        out.println("<h2>Song: " + song(state) + "</h2>");

        out.println("<h2>Flower:</h2>");
        out.println("<img src=\""  + flowerURL(state) + "\" width=\"150\" height=\"150\"><br><br>");
        out.println("<h4>Credit: https://statesymbolsusa.org/categories/flower</h4>");

        out.println("<h2>Flag:</h2>");
        out.println("<img src=\"" + flagURL(state) + "\" width=\"150\" height=\"125\"><br><br>");
        out.println("<h4>Credit: https://states101.com/flags</h4>");
        out.println("<br></br>");

        out.println("<form action=index.jsp>");
        out.println("<input type=\"Submit\" value=\"Continue\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    public String nickname(String state) throws IOException {
        String url = "https://www.britannica.com/topic/List-of-nicknames-of-U-S-States-2130544";

        Document doc = Jsoup.connect(url).validateTLSCertificates(false).get();

        Element table = doc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
            Element row = rows.get(i);
            Elements cols = row.select("td");

            if(cols.get(0).text().equalsIgnoreCase(state)) {
                return cols.get(1).text();
            }
        }
        return "Not Found";
    }

    public String capital(String state) throws IOException {
        String url = "https://gisgeography.com/united-states-map-with-capitals/";
        Document doc = Jsoup.connect(url).validateTLSCertificates(false).get();

        String searchString = "";
        Element e1 = doc.select("p").get(5); //select the first table.
        Element e2 = doc.select("p").get(6); //select the first table.

        searchString += e1.text() + " " + e2.text();

        String regex = "(?=" + state + ".*?\\(([^\\)]+)\\))";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(searchString);

        if (m.find()){
            return m.group(1);
        }else{
            return "Not found";
        }
    }

    public String song(String state) throws IOException {
        String url = "https://www.50states.com/songs/";
        Document doc = Jsoup.connect(url).validateTLSCertificates(false).get();

        Element table = doc.select("table").get(0); //select the first table.
        Elements rows = table.select("li");

        for (int i = 0; i < rows.size(); i++) { //first row is the col names so skip it.
            Element row = rows.get(i);
            Elements cols1 = row.select("dt");
            Elements cols2 = row.select("a");

            if(cols1.get(0).text().equalsIgnoreCase(state)) {
                String songs = "";
                for (int j = 0; j < cols2.size(); j++) {
                    songs += cols2.get(j).select("a").attr("title");
                    if(j < cols2.size()-1) songs += ", ";
                }
                return songs;
            }
        }
        return "Not Found";
    }

    public String populationURL(String state) throws IOException {
        Scanner sc = new Scanner(new File("/Users/sairajuladevi/IdeaProjects/Project1Task2/fips.csv"));
        sc.useDelimiter(",|\\n");   //sets the delimiter pattern
        while (sc.hasNext())  //returns a boolean value
        {
            if(sc.next().equalsIgnoreCase(state)){
                String state_code = sc.next();
                sc.close();  //closes the scanner
                return "https://api.census.gov/data/2020/dec/pl?get=NAME,P1_001N&for=state:" + state_code + "&key=2ee5b59a6167b655c9b2d446b3a1f435b25aceb7";
            }
        }
        sc.close();  //closes the scanner
        return "Not found!";
    }

    public String population(String state) throws IOException {
        String url = populationURL(state);

        Document doc = Jsoup.connect(url).ignoreContentType(true).validateTLSCertificates(false).get();

        String searchString = doc.text();

        String regex = "(?=\"" + state + "\",\"([^\"]+)\")";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(searchString);

        if (m.find()){
            return m.group(1);
        }else{
            return "Not found";
        }
    }

    public String flagURL(String state) throws IOException{
        String url = "https://www.states101.com/flags";
        Document doc = Jsoup.connect(url).validateTLSCertificates(false).get();

        Element table = doc.select("div").get(12); //select the first table.
        Elements rows = table.select("div");

        for (int i = 0; i < rows.size(); i++) { //first row is the col names so skip it.
            Elements cols1 = rows.get(i).select("a");
            if(cols1.get(0).text().equalsIgnoreCase(state)) {
                Element image = rows.get(i).select("img").first();
                return image.absUrl("src");
            }
        }
        return "Not Found";
    }

    public String flowerURL(String state) throws IOException{
        String url = "https://statesymbolsusa.org/categories/flower";

        Document doc = Jsoup.connect(url).validateTLSCertificates(false).get();

        Element table = doc.select("div").get(263); //select the first table.
        Elements rows = table.select("li");

        for (int i = 0; i < rows.size(); i++) { //first row is the col names so skip it.
            Elements cols1 = rows.get(i).select("div");
            if(cols1.get(2).text().equalsIgnoreCase(state)) {
                Element image = rows.get(i).select("img").first();
                return image.absUrl("src");
            }
        }
        return "Not Found";
    }


    public void destroy() {
    }
}