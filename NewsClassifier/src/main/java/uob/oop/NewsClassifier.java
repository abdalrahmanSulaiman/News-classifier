package uob.oop;

import java.text.DecimalFormat;
import java.util.Arrays;

public class NewsClassifier {
    public String[] myHTMLs;
    public String[] myStopWords = new String[127];
    public String[] newsTitles;
    public String[] newsContents;
    public String[] newsCleanedContent;
    public double[][] newsTFIDF;

    private final String TITLE_GROUP1 = "Osiris-Rex's sample from asteroid Bennu will reveal secrets of our solar system";
    private final String TITLE_GROUP2 = "Bitcoin slides to five-month low amid wider sell-off";

    public Toolkit myTK;

    public NewsClassifier() {
        myTK = new Toolkit();
        myHTMLs = myTK.loadHTML();
        myStopWords = myTK.loadStopWords();

        loadData();
    }

    public static void main(String[] args) {
        NewsClassifier myNewsClassifier = new NewsClassifier();

        myNewsClassifier.newsCleanedContent = myNewsClassifier.preProcessing();

        myNewsClassifier.newsTFIDF = myNewsClassifier.calculateTFIDF(myNewsClassifier.newsCleanedContent);

        //Change the _index value to calculate similar based on a different news article.
        double[][] doubSimilarity = myNewsClassifier.newsSimilarity(0);

        System.out.println(myNewsClassifier.resultString(doubSimilarity, 10));

        String strGroupingResults = myNewsClassifier.groupingResults(myNewsClassifier.TITLE_GROUP1, myNewsClassifier.TITLE_GROUP2);
        System.out.println(strGroupingResults);
    }

    public void loadData() {
        //TODO 4.1 - 2 marks
        newsTitles = new String[myHTMLs.length];
        newsContents = new String[myHTMLs.length];
        for (int i=0;i<myHTMLs.length;i++){
            newsTitles[i] = HtmlParser.getNewsTitle(myHTMLs[i]);
            newsContents[i] = HtmlParser.getNewsContent(myHTMLs[i]);
        }
    }

    public String[] preProcessing() {
        String[] myCleanedContent = null;
        //TODO 4.2 - 5 marks
        myCleanedContent = new String[newsContents.length];
        for(int i = 0; i<newsContents.length;i++){
            myCleanedContent[i] = NLP.textCleaning(newsContents[i]);
            myCleanedContent[i] = NLP.textLemmatization(myCleanedContent[i]);
            myCleanedContent[i] = NLP.removeStopWords(myCleanedContent[i],myStopWords);
        }

        return myCleanedContent;
    }

    public double calculateTF(String[] _cleanedContents,int index,int j,String[] vocabularyList){
        int numberOfTimes = 0;
        String[] wordsindocument = _cleanedContents[index].split(" ");
        for (String word: wordsindocument){
            if(word.equals(vocabularyList[j])){
                numberOfTimes++;
            }
        }
        double TF = numberOfTimes/(double)wordsindocument.length;
        return TF;
    }
    public double calculateIDF(String[] _cleanedContents,int index,int j,String[] vocabularyList){
        int N = _cleanedContents.length;
        int logDenominator = 0;
        boolean stop = true;
        for (int i=0;i<N;i++){
            String[] wordsindocument = _cleanedContents[i].split(" ");
            for (String word:wordsindocument){
                if(word.equals(vocabularyList[j]) && stop ){
                    logDenominator ++;
                    stop = false;
                    break;
                }
            }
            stop = true;
        }
        double numOverDen = N/(double)logDenominator;
        double IDF = Math.log(numOverDen) + 1;
        return IDF;
    }

    public double[][] calculateTFIDF(String[] _cleanedContents) {
        String[] vocabularyList = buildVocabulary(_cleanedContents);
        double[][] myTFIDF = null;
        myTFIDF = new double[_cleanedContents.length][vocabularyList.length];
        //TODO 4.3 - 10 marks
        for (int i =0;i<myTFIDF.length;i++){
            for (int j=0;j<myTFIDF[i].length;j++){
                myTFIDF[i][j] = calculateTF(_cleanedContents,i,j,vocabularyList) * calculateIDF(_cleanedContents,i,j,vocabularyList);
            }
        }
        return myTFIDF;
    }

    public String[] buildVocabulary(String[] _cleanedContents) {
        String[] arrayVocabulary = null;
        //TODO 4.4 - 10 marks
        String wordsInArray = "";
        int N = 0;
        for (int i = 0; i < _cleanedContents.length; i++) {
            String[] wordsArray = _cleanedContents[i].split(" ");
            for (String word : wordsArray) {
                    wordsInArray += word +" ";
                    N++;
                }
            }


        String[] testArray = wordsInArray.split(" ");
        int c = 1;
        while (c<testArray.length){
            String temp = testArray[c];
            int x = c-1;
            while(x>=0 && !testArray[x].equals(temp)){
                --x;
            }
            if(x != -1 && testArray[x].equals(testArray[c])){
                N--;
            }
            ++c;
        }
            arrayVocabulary = new String[N];
            for (int z = 0;z<arrayVocabulary.length;z++){
                arrayVocabulary[z] = "";
            }
        boolean test = false;
        int numAssignments = 0;
        for (int i = 0; i < _cleanedContents.length; i++) {
            String[] wordsArray = _cleanedContents[i].split(" ");
            for (int j = 0;j<wordsArray.length;j++) {
                for (int x = 0;x<arrayVocabulary.length;x++){
                    if(arrayVocabulary != null && arrayVocabulary[x].equals(wordsArray[j])){
                        test = true;
                    }
                }
                if(test == false &&numAssignments<N){
                    arrayVocabulary[numAssignments] = wordsArray[j];
                    numAssignments++;
                } else if (test == true) {
                    test = false;
                }
            }
        }
            return arrayVocabulary;
        }


    public double[][] newsSimilarity(int _newsIndex) {
        double[][] mySimilarity = null;
        mySimilarity = new double[newsCleanedContent.length][2];
        //String[] arrayOfWords = newsCleanedContent[_newsIndex].split("");
        Vector newsItem = new Vector(newsTFIDF[_newsIndex]);
        for (int i =0;i<mySimilarity.length;i++){
            for (int j=0;j<mySimilarity[i].length;j++){

                if(j == 0){
                    mySimilarity[i][j] = i;
                }
                else{
                    Vector toBeComparedNews = new Vector(newsTFIDF[i]);
                    mySimilarity[i][j] = newsItem.cosineSimilarity(toBeComparedNews);
                }
            }
        }
        for (int a = 0; a < mySimilarity.length - 1; ++a)
            for (int b = 0; b < mySimilarity.length - 1; ++b)
                if (mySimilarity[b+1][1] > mySimilarity[b][1]) {
                    double[] temp = mySimilarity[b];
                    mySimilarity[b] = mySimilarity[b + 1];
                    mySimilarity[b + 1] = temp;
                }
                    //TODO 4.5 - 15 marks
        return mySimilarity;
    }

    public String groupingResults(String _firstTitle, String _secondTitle) {
        int[] arrayGroup1 = null, arrayGroup2 = null;
        //TODO 4.6 - 15 marks
        int firstTitleIndex = 0 ;
        int secondTitleIndex = 0;
        for (int i = 0;i<newsTitles.length;i++){
            if(_firstTitle.equals(newsTitles[i])){
                firstTitleIndex = i;
            }
            else if(_secondTitle.equals(newsTitles[i])){
                secondTitleIndex = i;
            }
        }
        Vector newsItem1 = new Vector(newsTFIDF[firstTitleIndex]);
        Vector newsItem2 = new Vector(newsTFIDF[secondTitleIndex]);
        String firstGroup = "";
        String secondGroup = "";
        for (int i = 0;i<newsTitles.length;i++){
            Vector newsItemToBeCompared = new Vector(newsTFIDF[i]);
            if(newsItem1.cosineSimilarity(newsItemToBeCompared)>newsItem2.cosineSimilarity(newsItemToBeCompared)){
                firstGroup += i +" ";
            } else if (newsItem1.cosineSimilarity(newsItemToBeCompared)<newsItem2.cosineSimilarity(newsItemToBeCompared)) {
                secondGroup += i +" ";
            }

        }
        String[] firstgrouparray = firstGroup.split(" ");
        String[] secondgrouparray = secondGroup.split(" ");
        arrayGroup1 = new int[firstgrouparray.length];
        arrayGroup2 = new int[secondgrouparray.length];

        for (int i = 0;i<firstgrouparray.length;i++){
            arrayGroup1[i] =  Integer.parseInt(firstgrouparray[i]);
        }
        for (int i = 0;i<secondgrouparray.length;i++){
            arrayGroup2[i] =  Integer.parseInt(secondgrouparray[i]);
        }
        return resultString(arrayGroup1, arrayGroup2);
    }

    public String resultString(double[][] _similarityArray, int _groupNumber) {
        StringBuilder mySB = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        for (int j = 0; j < _groupNumber; j++) {
            for (int k = 0; k < _similarityArray[j].length; k++) {
                if (k == 0) {
                    mySB.append((int) _similarityArray[j][k]).append(" ");
                } else {
                    String formattedCS = decimalFormat.format(_similarityArray[j][k]);
                    mySB.append(formattedCS).append(" ");
                }
            }
            mySB.append(newsTitles[(int) _similarityArray[j][0]]).append("\r\n");
        }
        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

    public String resultString(int[] _firstGroup, int[] _secondGroup) {
        StringBuilder mySB = new StringBuilder();
        mySB.append("There are ").append(_firstGroup.length).append(" news in Group 1, and ").append(_secondGroup.length).append(" in Group 2.\r\n").append("=====Group 1=====\r\n");

        for (int i : _firstGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }
        mySB.append("=====Group 2=====\r\n");
        for (int i : _secondGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }

        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

}
