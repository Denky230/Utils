
package utils;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

/**
 * Contains methods for reading strings and integers.<br>Supports reading multiple inputs from a single line through the use of <i>separator</i>.
 * @author mfontana
 * @author Denky
 */
public class Reader {

    /**
     * Used to separate the different elements when reading multiple inputs at a time.<br>
     * Example: name;surname;age;country
     */
    public static String separator = ";";
    static String buffer = "";  // Stores input for later reading

    /**
     * Reads whole line of user input.
     * @return whole line of user input
     */
    public static String nextLine() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = "";

        try {
            // Get user input and make sure it's not blank
            do {
                s = br.readLine().trim();
                if (s.equals("")) {
                    System.out.println("Can't leave blank input");
                }
            } while (s.equals(""));

            br.close();
            
        } catch (IOException e) {
            throw new IOException("There was an error when reading from input - " + e.getMessage());
        }

        return s;
    }

    /**
     * @return everything between separators or the whole line if no separator was found
     */
    public static String nextString() throws IOException {
        String s = "";

        if (buffer.equals(""))
            readLineIntoBuffer();

        // Read from buffer instead of directly from user input
        StringReader sr = new StringReader(buffer);

        try {
            // Read til a separator is found
            char c;
            do {
                c = (char)sr.read();
                s += c;
            } while (c != separator.charAt(0));

            sr.close();
            
        } catch (IOException e) {
            throw new IOException("There was an error when reading from input - " + e.getMessage());
        }

        // Store rest of user input back to buffer for future reading
        buffer = buffer.substring(s.length());

        // Remove ending separator
        s = s.substring(0, s.length() - 1);
        return s;
    }

    /**
     * Asks for an integer number til a valid one is given, will read til separator or end of input.
     * @return next integer found
     */
    public static int nextInt() throws IOException {
        boolean valid = false;
        String s = "";
        int i = 0;

        do {
            try {
                s = nextString();
                i = Integer.parseInt(s);
                valid = true;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input given: " + s + ". Integer number expected");
                buffer = "";    // Empty buffer so nextString() reads user input again
            }
        } while (!valid);

        return i;
    }
    /**
     * Asks for an integer number greater than or equal to min and lower than max til a valid one is given, will read til separator or end of input.
     * @return next integer found
     * @see Reader#nextInt()
     */
    public static int nextInt(int min, int max) throws IOException {
        int i = 0;

        do {
            i = nextInt();
            if (i < min || i > max)
                System.out.println("Integer out of range given ("+min+" - "+max+"). ");
        } while (i < min || i > max);

        return i;
    }
    /**
     * Asks for an integer number greater than or equal to 0 and lower than max til a valid one is given, will read til separator or end of input.
     * @return next integer found
     * @see Reader#nextInt(int, int)
     */
    public static int nextInt(int max) throws IOException {
        return nextInt(0, max);
    }

    /**
     * Stores whole line of user input for future reading.
     * @see Reader#nextLine()
     */
    static void readLineIntoBuffer() throws IOException {
        String s = nextLine();

        // Store user input adding a separator at the end to stop the reading
        buffer = s + separator;
    }
}