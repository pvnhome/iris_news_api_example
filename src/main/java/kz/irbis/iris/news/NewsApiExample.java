package kz.irbis.iris.news;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.clapper.util.misc.FileHashMap;

import kz.irbis.iris.news.model.News;

/**
 * <p>Пример испоользования IRIS News API.</p>
 * <p><b>Created:</b> 11.12.2017 19:34:59</p>
 * @author victor
 */
public class NewsApiExample {
    /**
     * IP-адрес сервера IRIS News API.
     */
    private static final String IP = "192.168.1.24";
    /**
     * Пароль для доступа к серверу IRIS News API.
     */
    private static final String PASSWORD = "qqq";
    /**
     * Размер пакета новостных сообщений.
     * Столько сообщений загружается за один вызов метода IRIS News API. 
     */
    private static final int BUNDLE_SIZE = 10;
    /**
     * Ключ для хранения последней версии новостных сообщений.
     */
    private static final String LAST_VERSION_KEY = "last_version";

    public static void main(String[] args) {
        FileHashMap<Integer, News> newsMap = null;
        FileHashMap<String, String> settingsMap = null;
        try {
            // Открываем демонстрационные хранилища для новостных сообщений и настроек
            newsMap = new FileHashMap<>("data/news", 0);
            settingsMap = new FileHashMap<>("data/settings", 0);

            // Загружаем из настроек последнюю версию новостных сообщениий
            // Если версия в настройках отсутствует, принимаем ей равной нулю, что значит отсутствие ограничений по версии.
            long lastVersion = settingsMap.containsKey(LAST_VERSION_KEY) ? Long.parseLong(settingsMap.get(LAST_VERSION_KEY)) : 0L;

            // Формируем URI для доступа к IRIS News API
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("https://" + IP + ":8443/iris_news_api/methods/news");

            // Задаем параметры для вызова метода news
            target = target.queryParam("username", "mobapp").queryParam("password", PASSWORD);
            target = target.queryParam("deviceinfo", "ANDROID");
            target = target.queryParam("depth", 2).queryParam("bundle", BUNDLE_SIZE).queryParam("langs", "ru");

            // IRIS News API предполагает загрузку сообщений пакетами укащзанного размера.
            // Выполняем загрузку пакетов в цикле до тех пор пока не получим пустой пакет.
            News[] news = null;
            do {
                news = target.queryParam("version", lastVersion).request().get(News[].class);

                System.out.println("LOADED: " + news.length);

                for (News rec : news) {
                    // Проверяем была ли загружена новость ранее
                    if (findNews(newsMap, rec)) {
                        // Новость уже была загружена ранее. Обновляем. 
                        updateNews(newsMap, rec);
                        System.out.println("UPDATE: " + rec.getTitle());
                    } else {
                        // Новость не была загружена ранее. Сохряняем в хранилище. 
                        insertNews(newsMap, rec);
                        System.out.println("INSERT: " + rec.getTitle());
                    }

                    // Обновляем последнюю загруженную версию если она меньше версии новостного сообщения.
                    // Делаем это с учетом того, что порядок новостных сообщений не гарантируется в IRIS Nesw API.
                    if (rec.getVersion() > lastVersion) {
                        lastVersion = rec.getVersion();
                    }
                }

                // Сохряняем последнюю загруженную версию в настройках.
                System.out.println("LAST VERSION: " + lastVersion);
                settingsMap.put(LAST_VERSION_KEY, Long.toString(lastVersion));

            } while (news.length > 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (newsMap != null) {
                try {
                    newsMap.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (settingsMap != null) {
                try {
                    settingsMap.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Имитация поиска новости в хранилище данных
     */
    private static boolean findNews(FileHashMap<Integer, News> newsMap, News rec) {
        return newsMap.containsKey(rec.getId());
    }

    /**
     * Имитация добавления новости в хранилище данных
     */
    private static void insertNews(FileHashMap<Integer, News> newsMap, News rec) {
        newsMap.put(rec.getId(), rec);
    }

    /**
     * Имитация обновления новости в хранилище данных
     */
    private static void updateNews(FileHashMap<Integer, News> newsMap, News rec) {
        newsMap.put(rec.getId(), rec);
    }
}
