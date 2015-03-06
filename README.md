# WebScrapper
### Simple web-scrapper (JAVA)

Launch this program from console and add arguments like this:

Usage example: <code>java -jar HireRightScraper.jar [URL_OR_URLS] [KEY_WORDS] –v –w –c –e</code>

Where: <ul> <li> <b><i>URL_OR_URLS</b></i> - URL for scraping OR path to list of URLS in *.txt file (Example: http://www.thevenusproject.com/faq#faqnoanchor OR C:\urls.txt); </li>
  <li> <b><i>KEY_WORDS</b></i> - list of words (without spaces) that you need to find on the page (Example: Venus,project); </li>
  <li> <b><i>-v</b></i> - information about time spend on data scrapping; </li>
  <li> <b><i>-w</b></i> - number of provided word(s) occurrence on webpage; </li>
  <li> <b><i>-c</b></i> - number of characters of each web page </li>
  <li> <b><i>-e</b></i> - sentences’ which contain given words </li>
</ul>

Data processing results are printed to output for each web resources separately and for all resources as total.
No 3d-party libraries
