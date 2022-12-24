package com.group17.game.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Methods to retrieve the message of the day from the web server.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public class MessageOfTheDay {
    /**
     * Get the message of the day from the URL.
     *
     * @return the message
     */
    public static String get() {
        String solution = solve(httpGet("http://cswebcat.swan.ac.uk/puzzle"));
        return httpGet(
                "http://cswebcat.swan.ac.uk/message?solution=" + solution);
    }

    private static String solve(String puzzle) {
        StringBuilder solution = new StringBuilder();
        boolean shiftDirection = true;
        for (int i = 0; i < puzzle.length(); i++) {
            char current = puzzle.charAt(i);

            if (shiftDirection) {
                if (current == 'Z') {
                    solution.append('A');
                } else {
                    solution.append((char) (((int) current) + 1));
                }
            } else {
                if (current == 'A') {
                    solution.append('Z');
                } else {
                    solution.append((char) (((int) current) - 1));
                }
            }
            shiftDirection = !shiftDirection;
        }
        return solution.toString();
    }

    private static String httpGet(String url) {
        StringBuilder response = new StringBuilder();
        try {
            URL _url = new URL(url);
            HttpURLConnection connection =
                    (HttpURLConnection) _url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
