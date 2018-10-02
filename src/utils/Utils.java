/**
 * @author mfontana
 * @author Denky
 */

package utils;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class Utils {
    static String buffer = "";   // Stores input reading remains

    /**
     * @return whole line of user input
     */
    public static String nextLine() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String string = "";

        // Get user input and make sure it's not blank
        try {
            do {
                string = br.readLine().trim();
                if (string.equals("")) {
                    System.out.println("Can't leave blank input");
                } 
            } while (string.equals(""));
        } catch (IOException e) {
            System.out.println("Invalid input given - Error: " + e);
        }

        return string;
    }

    /**
     * @return everything between separators
     * @param separator character used as a separator
     */
    public static String nextString(String separator) {
        // If buffer is empty read user input and store it in buffer
        fillBuffer();

        // Read from buffer instead of directly from user input
        StringReader sr = new StringReader(buffer);
        String string = "";

        try {
            // Read til a separator is found
            do {
                string += (char)sr.read();
            } while (string.charAt(string.length() - 1) != separator.charAt(0));

            // Store rest of user input back to buffer for future reading
            buffer = buffer.substring(string.length());
        } catch (IOException e) {
            System.out.println("Invalid input given: " + e.getMessage());
        }

        // Return everything read minus the separator
        return string.substring(0, string.length() - 1);
    }
    /**
     * nextString(String separator) for single words.
     * @return next single word found
     */
    public static String nextString() {
        return nextString(" ");
    }

    public static int nextInt(String separator) {
        boolean valid = false;
        int i = 0;

        do {
            try {
                i = Integer.parseInt(nextString(separator));
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid input given: " + e.getMessage());
                buffer = "";
            }
        } while (!valid);

        return i;
    }
    public static int nextInt() {
        return 0;
    }

    /**
     * Checks if buffer is empty and stores user input in it if so.
     */
    public static void fillBuffer() {
        if (buffer.equals("")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                buffer = br.readLine();
                System.out.println("Buffer: " + buffer);
            } catch (IOException e) {
                System.out.println("Invalid input given - Error: " + e);
            }
        }
    }
}