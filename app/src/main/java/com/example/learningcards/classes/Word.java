package com.example.learningcards.classes;

public class Word {
    private int id;
    private String status_en;
    private String status_cin;
    private String russian;
    private String english;
    private String englishTranscription;
    private String englishUsage;
    private String chinese;
    private String chineseUsage;
    private String pinyin;
    private String group;

    public Word() {
        russian = english = englishTranscription = englishUsage = chineseUsage = chinese = pinyin = null;
        group = "Без группы";
        status_en = status_cin = "NOT_LEARNED";
    }

    public Word(String status_en, String status_cin, String russian, String english, String englishTranscription, String englishUsage, String chinese, String chineseUsage, String pinyin, String group) {
        this.status_en = status_en;
        this.status_cin = status_cin;
        this.russian = russian;
        this.english = english;
        this.englishTranscription = englishTranscription;
        this.englishUsage = englishUsage;
        this.chinese = chinese;
        this.chineseUsage = chineseUsage;
        this.pinyin = pinyin;
        this.group = group;
    }

    public Word(int id, String status_en, String status_cin, String russian, String english, String englishTranscription, String englishUsage, String chinese, String chineseUsage, String pinyin, String group) {
        this.id = id;
        this.status_en = status_en;
        this.status_cin = status_cin;
        this.russian = russian;
        this.english = english;
        this.englishTranscription = englishTranscription;
        this.englishUsage = englishUsage;
        this.chinese = chinese;
        this.chineseUsage = chineseUsage;
        this.pinyin = pinyin;
        this.group = group;
    }

    public int getId() {
        return id;
    }


    public String getStatus_en() {
        return status_en;
    }

    public void setStatus_en(String status_en) {
        this.status_en = status_en;
    }

    public String getStatus_cin() {
        return status_cin;
    }

    public void setStatus_cin(String status_cin) {
        this.status_cin = status_cin;
    }

    public String getRussian() {
        return russian;
    }

    public void setRussian(String russian) {
        this.russian = russian;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getEnglishTranscription() {
        return englishTranscription;
    }

    public void setEnglishTranscription(String englishTranscription) {
        this.englishTranscription = englishTranscription;
    }

    public String getEnglishUsage() {
        return englishUsage;
    }

    public void setEnglishUsage(String englishUsage) {
        this.englishUsage = englishUsage;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getChineseUsage() {
        return chineseUsage;
    }

    public void setChineseUsage(String chineseUsage) {
        this.chineseUsage = chineseUsage;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", status_en='" + status_en + '\'' +
                ", status_cin='" + status_cin + '\'' +
                ", russian='" + russian + '\'' +
                ", english='" + english + '\'' +
                ", englishTranscription='" + englishTranscription + '\'' +
                ", englishUsage='" + englishUsage + '\'' +
                ", chinese='" + chinese + '\'' +
                ", chineseUsage='" + chineseUsage + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}