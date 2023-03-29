package edu.cmu.testapplication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.*;

public class FetchDeckOfCards {
    public static void main(String[] args) throws IOException {
        // Demonstration of API

        // create one new deck and shuffle it
        String deckURL = "https://www.deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";
        JSONObject deckJSON = getJSON(deckURL);
        String deckCode = getDeckCode(deckJSON);

        System.out.println("Here is the JSON representation of the virtual deck, pre-shuffled:");
        System.out.println(deckJSON.toString(4));


        // draw 2 cards
        int count = 2;
        String drawNCardsURL = "https://www.deckofcardsapi.com/api/deck/" + deckCode + "/draw/?count=" + count;
        JSONObject drawNCardsJSON = getJSON(drawNCardsURL);
        String deckCode2 = getDeckCode(drawNCardsJSON);

        System.out.println("\nHere is the JSON representation of " + count + " random cards drawn from the deck:");
        System.out.println(drawNCardsJSON.toString(4));

        System.out.println("\nThe remaining virtual deck code is:");
        System.out.println(deckCode2);

        // from here the API keeps track of the remaining cards in the deck
        // other functionalities can be added from this point
    }

    public static JSONObject getJSON(String deckOfCardsURL) throws IOException {
        // Adapted from https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751
        URL url = new URL(deckOfCardsURL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Getting the response code
        int responsecode = conn.getResponseCode();

        JSONObject data_obj;
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {
            String inline = "";
            Scanner scanner = new Scanner(conn.getInputStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner and connection
            scanner.close();
            conn.disconnect();

            return new JSONObject(inline);
        }
    }
    public static String getDeckCode (JSONObject data_obj) throws IOException {
        return data_obj.getString("deck_id");
    }
}