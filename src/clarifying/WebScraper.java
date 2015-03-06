package clarifying;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class WebScraper {
    private int totalLength = 0;
    private long totalTime = 0;
    private int totalLengthWithoutTags = 0;

    public WebScraper(URLs urlsList, Words wordsList, String[] flags) {
        setFlags(flags);
        scrap(urlsList, wordsList);
    }

    private void setFlags(String[] arguments) {
        for (String argument : arguments) {
            switch (argument) {
                case "-v": {
                    Flags.v.setFlag(argument);
                    break;
                }
                case "-c": {
                    Flags.c.setFlag(argument);
                    break;
                }
                case "-w": {
                    Flags.w.setFlag(argument);
                    break;
                }
                case "-e": {
                    Flags.e.setFlag(argument);
                    break;
                }
                default: {
                    System.out.println("\"" + argument + "\"" + " is wrong flag. Flags must begins with \"-\" symbol," +
                            " and must be \"-v\", \"-w\", \"-c\", or \"-e\".");
                    break;
                }
            }
        }
    }
   /*
    *  This method searches for word appearance in
    *  every readLine() from BufferedReader, also counts
    *  it's appearance (total and temp).
    *  For -e flag this method collects sentences
    *  where this word was matched.
    */
    private void searchForWord(Word word, String sentence) {
        Pattern patternWord = Pattern.compile(word.toString(), Pattern.MULTILINE);
        Matcher matcher = patternWord.matcher(sentence);
        while (matcher.find()) {
            word.incrementTotalAppearanceCounter();
            word.increaseTemporaryCounter();
            int lastIndexOfWord = sentence.indexOf(matcher.group(0)) + word.toString().length();
            if (0 < lastIndexOfWord && lastIndexOfWord <= 150) {
                word.addSentence(sentence.substring(0, lastIndexOfWord));
            } else if (50 < lastIndexOfWord && lastIndexOfWord <= sentence.length()-150 && lastIndexOfWord + 150 < sentence.length()) {
                word.addSentence(sentence.substring(lastIndexOfWord, lastIndexOfWord + 150));
            } else word.addSentence(sentence);
        }
    }

   /*
    *  Regular expressions isn't good for parsing
    *  but without 3d party libraries - i'll use them.
    *  Method excludes some HTML tags. When try-catch
    *  catches exception, cycle continues, some tags
    *  remains in text.
    */
    private String excludeHTMLTags(String sentence) {
        //Pattern HTMLPattern = Pattern.compile("<(?:\"[^\"]*\"['\"]*|'[^']*'['\"]*|[^'\">])>");
        Pattern HTMLTagPattern = Pattern.compile("<([a-zA-Z]+)(?:[^>]*[^/]*)?>|</([a-zA-Z]+)(?:[^>]*[^/]*)?>");
        Matcher matcher = HTMLTagPattern.matcher(sentence);
        while(matcher.find()) {
            try {
                sentence = sentence.replaceAll(matcher.group(), "");
            }
            catch (PatternSyntaxException e) {
            }
        }
        return sentence;
    }
   /*
    *  Main method which collects all text
    *  from URL.
    */
    private void scrap(URLs urlsList, Words wordsList) {
        for (String URL : urlsList.toStringArray()) {
            StringBuilder text = new StringBuilder();
            String textWithoutHTMTags;
            System.out.println("======================================================================");
            System.out.println("Scraping this URL:\t\t" + URL + "\n");
            long timerForCurrentURL = System.currentTimeMillis();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(URL).openStream()))){
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    text.append(line);
                    for (Word word : wordsList.getAllWords()) {
                        searchForWord(word, excludeHTMLTags(line).replaceAll("\t", " "));
                    }
                }
                totalLength += text.length();
                textWithoutHTMTags = excludeHTMLTags(text.toString());
                totalLengthWithoutTags += textWithoutHTMTags.length();
            }
            catch (IOException e) {
                System.out.println("An error occurred. Failed or interrupted I/O operations! " +
                        "This URL: " + URL + "will be skipped.");
                continue;
            }
    /*
     *  Checking every flag for this URL
     */
            if (Flags.v.getFlag()) {
                long currentURLTime = System.currentTimeMillis() - timerForCurrentURL;
                totalTime += currentURLTime;
                System.out.println("(" + Flags.v.toString() + ")" +
                        " Time spent for scraping and collecting data: " + currentURLTime + " ms");
            }

            if (Flags.c.getFlag()) {
                System.out.println("(" + Flags.c.toString() + ")" + " Number of characters on this page: "
                        + text.length() + " (" + textWithoutHTMTags.length() + " without tags)");
            }

            if (Flags.w.getFlag()) {
                System.out.println("(" + Flags.w.toString() + ")" + " Counting every word appearance: ");
                for (Word word : wordsList.getAllWords()) {
                    System.out.println("   •  " + word + " - " + word.getTemporaryCounter() +  ";");
                }
            }

            if (Flags.e.getFlag()) {
                System.out.println("(" + Flags.e.toString() + ")" + " Sentences with every word: ");
                for (Word word : wordsList.getAllWords()) {
                    int counter = 1;
                    System.out.println("------------------ " + word + " ------------------");
                    for (String sentence : word.getAllSentences()) {
                        System.out.println("Sentence #" + counter + "\t" + sentence);
                        counter++;
                    }
                }
            }
            wordsList.resetAllTemporaryCounters();
            wordsList.resetAllSentencesLists();
        }
    /*
     *  Showing total statistics.
     */
        System.out.println("\n+++++++++++++++++++++++++++++++++++TOTAL++++++++++++++++++++++++++++++++++++++\n");
        if (Flags.v.getFlag()) {
            System.out.println("(" + Flags.v.toString() + ")" + " Total time spent for scraping and collecting data: "
                    + totalTime + " ms");
        }
        if (Flags.c.getFlag()) {
            System.out.println("(" + Flags.c.toString() + ")" + " Total number of characters: " + totalLength
                    + " (" + totalLengthWithoutTags + " without tags)");
        }
        if (Flags.w.getFlag()) {
            System.out.println("(" + Flags.w.toString() + ")" + " Counting every word appearance in total: ");
            for (Word word : wordsList.getAllWords()) {
                System.out.println("   •  " + word + " - " + word.getTotalAppearanceCounter() +  ";");
            }
        }
    }
}
