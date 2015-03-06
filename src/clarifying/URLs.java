package clarifying;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    /*
     *  Contains list of URLs
     *  obtained from file or
     *  from argument.
     */
public class URLs {
    private List<String> URLs = new ArrayList<String>();

    public URLs (String pathOrURL) {
        setURLs(pathOrURL);
    }

    private void setURLs (String pathOrURL) {
       BufferedReader bufferedReader;
    /*
     *  if URL
     */
        if (isURL(pathOrURL) && isAccessibleURL(pathOrURL)) URLs.add(pathOrURL);
    /*
     *  if path to file (or something else)
     */
        else
           try {
               bufferedReader = new BufferedReader(new FileReader(pathOrURL));
               String url;
               while ((url = bufferedReader.readLine()) != null) {
                   if (isURL(url)  && isAccessibleURL(url)) URLs.add(url.trim());
                   else throw new MalformedURLException();
               }
               bufferedReader.close();
           } catch (FileNotFoundException e) {
               System.out.println("Wrong path to the URL list file or your URL are incorrect or unreachable!\n" +
                       "Use \" \" when declaring path.\n" +
                       "Use http://... form when declaring URL.");
               //Thread.currentThread().stop();
               System.exit(3);
           } catch (MalformedURLException e) {
               System.out.println("Some of URLs are incorrect or unreachable! " +
                       "Use http://... form and separate URLs with line break.");
               System.exit(2);
           } catch (IOException e) {
               System.out.println("An error occurred. Failed or interrupted I/O operations!");
               System.exit(-1);
           }
    }
    /*
     *  Is string is URL?
     */
    public boolean isURL(String URL) {
        try {
            new URL(URL.trim());
            return true;
        }
        catch (MalformedURLException e){
            return false;
        }
    }
    /*
     *  Is this URL is accessible?
     */
    public boolean isAccessibleURL(String URL){
       try (InputStreamReader inputStreamReader = new InputStreamReader(new URL(URL.trim()).openStream())) {
           return true;
       }
       catch (UnknownHostException e) {
           return false;
       }
       catch (IOException e) {
           System.out.println("Problems with " + URL + " URL. Failed or interrupted I/O operations!");
           return false;
       }
    }

    public String[] toStringArray() {
        String[] result = new String[URLs.size()];
        for (int counter = 0; counter < result.length; counter++) {
            result[counter] = URLs.get(counter);
        }
        return result;
    }
}
