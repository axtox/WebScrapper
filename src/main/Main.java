package main;

import clarifying.*;
  /*
   *  Main class.
   *  Usage example:
   *  java -jar HireRightScraper.jar http://www.thevenusproject.com/faq#faqnoanchor Venus,Freedom –v –w –c –e"
   */
public class Main {
    public static void main(String[] args) {
        if (args.length < 7) {
            try {
                String[] flags = new String[4];
                System.arraycopy(args, 2, flags, 0, args.length-2);
                new WebScraper(new URLs(args[0]), new Words(args[1]), flags);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong number of input arguments.\n" +
                        "Usage example: http://www.cnn.com Greece,default –v –w –c –e");
                System.exit(1);
            }
        } else {
            System.out.println("Too many arguments! You can specify up to six arguments.\n" +
                    "Usage example: http://www.cnn.com Greece,default –v –w –c –e");
            System.exit(0);
        }
    }
}
