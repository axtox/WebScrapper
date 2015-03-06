package clarifying;


import java.util.*;

public class Words {
    /*
     *  Words contains list of all words.
     */
    private List<Word> wordsList = new ArrayList<Word>();

    public Words(String Words) {
        setWords(Words);
    }

    private void setWords(String Words) {
        for (String word : Words.split(",")) {
            wordsList.add(new Word(word));
        }
       // Collections.addAll(wordsList, Words.split(","));
    }

    public Word[] getAllWords() {
        Word[] result = new Word[wordsList.size()];
        for (int counter = 0; counter < result.length; counter++) {
            result[counter] = wordsList.get(counter);
        }
        return result;
    }

    public void resetAllTemporaryCounters() {
        for (Word word : wordsList) {
            word.resetTemporaryCounter();
        }
    }

    public void resetAllSentencesLists() {
        for (Word word : wordsList) {
            word.resetSentencesList();
        }
    }
}
/*
 *  Each word has it's own sentences,
 *  where this word is appears.
 *  There might be more then one sentence -
 *  depends on how many times the word appears on the page.
 */
class Word {
    private String word;
    private List<String> sentencesWithThisWord = new ArrayList<String>();
    private int totalAppearanceCounter = 0;
    private int temporaryCounter = 0;

    public Word(String word) {
        this.word = word;
    }

    public void addSentence(String sentence) {
        sentencesWithThisWord.add(sentence);
    }

    public String[] getAllSentences() {
        String[] result = new String[sentencesWithThisWord.size()];
        for (int counter = 0; counter < result.length; counter++) {
            result[counter] = sentencesWithThisWord.get(counter);
        }
        return result;
    }

    public void resetSentencesList() {
        sentencesWithThisWord.clear();
    }

    public void incrementTotalAppearanceCounter() {
        totalAppearanceCounter++;
    }

    public int getTotalAppearanceCounter() {
        return totalAppearanceCounter;
    }

    public String toString() {
        return word;
    }

    public int getTemporaryCounter() {
        return temporaryCounter;
    }

    public void increaseTemporaryCounter() {
        temporaryCounter++;
    }

    public void resetTemporaryCounter() {
        temporaryCounter = 0;
    }
}