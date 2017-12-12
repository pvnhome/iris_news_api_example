package kz.irbis.iris.news.model;

import java.io.Serializable;

/**
 * Класс для передачи новостного сообщения через IRIS News API.
 **/
public class News implements Serializable {
    private static final long serialVersionUID = -448661349940176507L;

    /**
    * Уникальный код записи.
    **/
    private int id;

    /**
    * Версия новостного сообщения.
    **/
    private long version;

    /**
    * Дата и время новостного сообщения в виде строки в формате "YYYY-MM-DD HH:mm:ss".
    **/
    private String newstime;

    /**
    * Заголовок новостного сообщения.
    **/
    private String title;

    /**
    * Тело новостного сообщения в HTML.
    **/
    private String body;

    /**
    * Время начала события в виде строки в формате "YYYY-MM-DD HH:mm:ss". Заполняется для событий.
    **/
    private String begtime;

    /**
    * Время окончания события в виде строки в формате "YYYY-MM-DD HH:mm:ss". Заполняется для событий.
    **/
    private String endtime;

    /**
    * Место проведения события. Заполняется для событий.
    **/
    private String eventplace;

    /**
    * Название источника.
    **/
    private String newssource;

    /**
    * Язык новостного сообщения. Допустимые значения "ru", "kz" или "en".
    **/
    private String lang;

    /**
    * Коды разделов к которым привязано новостное сообщение.
    **/
    private int[] sections;

    /**
    * Коды прикрепленных к новостному сообщению файлов.
    **/
    private int[] files;

    //==============================================================
    // Конструкторы.
    //==============================================================

    public News() {
        newstime = "";
        title = "";
        body = "";
        begtime = "";
        endtime = "";
        eventplace = "";
        newssource = "";
        lang = "";
    }

    public News(int id, long version, String newstime, String title, String body, String begtime, String endtime, String eventplace, String newssource, String lang, int[] sections, int[] files) {
        this.id = id;
        this.version = version;
        this.newstime = newstime;
        this.title = title;
        this.body = body;
        this.begtime = begtime;
        this.endtime = endtime;
        this.eventplace = eventplace;
        this.newssource = newssource;
        this.lang = lang;
        this.sections = sections;
        this.files = files;
    }

    //==============================================================
    // GET/SET-методы.
    //==============================================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBegtime() {
        return begtime;
    }

    public void setBegtime(String begtime) {
        this.begtime = begtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getEventplace() {
        return eventplace;
    }

    public void setEventplace(String eventplace) {
        this.eventplace = eventplace;
    }

    public String getNewssource() {
        return newssource;
    }

    public void setNewssource(String newssource) {
        this.newssource = newssource;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int[] getSections() {
        return sections;
    }

    public void setSections(int[] sections) {
        this.sections = sections;
    }

    public int[] getFiles() {
        return files;
    }

    public void setFiles(int[] files) {
        this.files = files;
    }
}
