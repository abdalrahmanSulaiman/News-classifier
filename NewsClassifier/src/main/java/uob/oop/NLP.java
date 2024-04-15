package uob.oop;

import java.util.Locale;

public class NLP {
    /***
     * Clean the given (_content) text by removing all the characters that are not 'a'-'z', '0'-'9' and white space.
     * @param _content Text that need to be cleaned.
     * @return The cleaned text.
     */
    public static String textCleaning(String _content) {
        StringBuilder sbContent = new StringBuilder();

        char[] contentArray = _content.toLowerCase().toCharArray();
        String allowedCharacter = "abcdefghijklmnopqrstuvwxyz0123456789" + " ";
        int a;
        for (a=0;a<contentArray.length;a++){
            if (allowedCharacter.indexOf(contentArray[a]) != -1){
                sbContent.append(contentArray[a]);
            }
        }

        return sbContent.toString().trim();
    }

    /***
     * Text lemmatization. Delete 'ing', 'ed', 'es' and 's' from the end of the word.
     * @param _content Text that need to be lemmatized.
     * @return Lemmatized text.
     */
    public static String textLemmatization(String _content) {
        StringBuilder sbContent = new StringBuilder();
        String[] arrayOfWord = _content.split(" ");
        for (String word: arrayOfWord)
        {
        StringBuilder editingStringBuilder = new StringBuilder();
        if(word.endsWith("ing") == true)
        {
            editingStringBuilder.append(word);
            editingStringBuilder.delete((word.length()-3),word.length());
            word = editingStringBuilder.toString();
            sbContent.append(word);
            sbContent.append(" ");
        }
        else if (word.endsWith("ed") == true) {
            //word = word.split("ed")[0];
            editingStringBuilder.append(word);
            editingStringBuilder.delete((word.length()-2),word.length());
            word = editingStringBuilder.toString();
            sbContent.append(word);
            sbContent.append(" ");
        }

        else if(word.endsWith("es")== true)
        {
            //word = word.split("es")[0];
            editingStringBuilder.append(word);
            editingStringBuilder.delete((word.length()-2),word.length());
            word = editingStringBuilder.toString();
            sbContent.append(word);
            sbContent.append(" ");
        }

        else if(word.endsWith("s")== true)
        {

            editingStringBuilder.append(word);
            editingStringBuilder.delete((word.length()-1),word.length());
            word = editingStringBuilder.toString();
            sbContent.append(word);
            sbContent.append(" ");
        }

        else {
            sbContent.append(word);
            sbContent.append(" ");
        }
        }
        return sbContent.toString().trim();
    }

    /***
     * Remove stop-words from the text.
     * @param _content The original text.
     * @param _stopWords An array that contains stop-words.
     * @return Modified text.
     */
    public static String removeStopWords(String _content, String[] _stopWords) {
        StringBuilder sbConent = new StringBuilder();
        boolean skip = false;
        String[] _contentArray = _content.split(" ");
        for (int i = 0;i<_contentArray.length;i++){
            for (int x = 0;x<_stopWords.length;x++){
                if (_contentArray[i].equals(_stopWords[x])) {
                    skip = true;
                    break;
                }
            }
            if (skip == false){
                sbConent.append(_contentArray[i]);
                sbConent.append(" ");
            }
            skip = false;
        }
        return sbConent.toString().trim();
    }

}
