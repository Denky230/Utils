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
    static String cache = "";   // Stores input reading remains
    
    /**
     * @return Whole line of user input
     */
    public static String inputStringLine() {
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
     * @return Everything between separators ("/') or a single word if no separators are found
     */
    public static String inputString() {
        // If cache is empty read user input and store it in cache
        if (cache.equals("")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            try {
                cache = br.readLine().trim();
            } catch (IOException e) {
                System.out.println("Invalid input given - Error: " + e);
            }            
        }
        
        // Read from cache instead of directly from user input
        StringReader sr = new StringReader(cache);
        String string = "";
        
        try {
            int i = sr.read();
            // If first char found is a separator catch everything til next separator
            if (i == (char)'"' || i == (char)'\'') {
                do {
                    i = sr.read();
                    string += (char)i;
                } while (i != (char)'"' && i != (char)'\'');
                string = string.substring(0, string.length() - 1) + "  ";   // GITANADA MASTER
            // else catch just that single word
            } else {
                do {
                    string += (char)i;
                    i = sr.read();
                } while (i != -1 && i != (char)' ');
            }
            
            // Store rest of user input back to cache for future reading
            cache = cache.substring(string.length()).trim();
        } catch (IOException e) {
            System.out.println("Invalid input given - Error: " + e);
        }
        
        return string.trim();
    }
}