package com.tech.paper.jsonobj;

public class WordModel {
    private String word;
    private String wordId;
    private Integer wordFrequency;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public Integer getWordFrequency() {
        return wordFrequency;
    }

    public void setWordFrequency(Integer wordFrequency) {
        this.wordFrequency = wordFrequency;
    }

    public WordModel(String word, String wordId, Integer wordFrequency) {
        this.word = word;
        this.wordId = wordId;
        this.wordFrequency = wordFrequency;
    }

    public WordModel() {
    }
}
