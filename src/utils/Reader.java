
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
    public String separator = ";";
    private String buffer = "";  // Stores input for later reading

    private Reader() {}
    private static Reader instance;
    public static Reader getInstance() {
        if (instance == null)
            instance = new Reader();
        return instance;
    }
    
    /**
     * Reads whole line of user input.
     * @return whole line of user input
     * @throws java.io.IOException
     */
    public String nextLine() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Get user input and make sure it's not blank
        String s = "";
        do {
            s = br.readLine().trim();
            if (s.equals("")) {
                System.out.println("Can't leave blank input");
            }
        } while (s.equals(""));

        return s;
    }
    /**
     * @return everything between separators or the whole line if no separator was found
     * @throws java.io.IOException
     * @see Reader#separator
     */
    public String nextString() throws IOException {
        if (buffer.equals("")) {
            readLineIntoBuffer();
        }

        // Read from buffer instead of directly from user input
        String s = "";
        try ( StringReader sr = new StringReader(buffer); ) {
            // Read til a separator is found
            char c;
            do {
                c = (char)sr.read();
                s += c;
            } while (c != separator.charAt(0));
            
        } catch (IOException e) {
            throw new IOException("There was an error when reading from inputeee - " + e.getMessage());
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
     * @throws java.io.IOException
     */
    public int nextInt() throws IOException {
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
     * @throws java.io.IOException
     * @see Reader#nextInt()
     */
    public int nextInt(int min, int max) throws IOException {
        int i = 0;

        do {
            i = nextInt();
            if (i < min || i > max)
                System.out.println("Integer out of range given ("+min+" - "+max+")");
        } while (i < min || i > max);

        return i;
    }
    /**
     * Asks for an integer number greater than or equal to 0 and lower than max til a valid one is given, will read til separator or end of input.
     * @return next integer found
     * @throws java.io.IOException
     * @see Reader#nextInt(int, int)
     */
    public int nextInt(int max) throws IOException {
        return nextInt(0, max);
    }

    /**
     * Stores whole line of user input for future reading.
     * @see Reader#nextLine()
     */
    private void readLineIntoBuffer() throws IOException {
        // Store user input adding a separator at the end to stop the reading
        buffer = nextLine() + separator;
    }
}