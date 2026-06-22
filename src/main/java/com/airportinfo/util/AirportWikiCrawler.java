package com.airportinfo.util;

import com.airportinfo.Setting;
import com.airportinfo.model.Airport;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Web crawling from wikipedia.
 *
 * @author lalaalal
 */
public class AirportWikiCrawler {
    private static final String EN_WIKI_URL = "https://en.wikipedia.org/wiki/";
    private static final HashMap<String, DocumentCacheData> documentCache = new HashMap<>();
    private static final HashMap<String, String> translationCache = new HashMap<>();
    private static final HashMap<String, BufferedImage> imageCache = new HashMap<>();
    private final Document document;
    private final Airport airport;
    private final boolean englishOnly;

    private static String getLocalizedWikiURL() {
        Locale locale = Locale.getDefault();
        return EN_WIKI_URL.replace("en.wikipedia", locale.getLanguage() + ".wikipedia");
    }

    private static String makeAirportSearchTerm(String name) {
        return name.trim().replace(' ', '_');
    }

    private static DocumentCacheData createDocument(Airport airport) throws IOException {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
        try {
            String airportSearchTerm = makeAirportSearchTerm(airport.getAirportName());
            String url = getLocalizedWikiURL() + airportSearchTerm;
            return new DocumentCacheData(Locale.getDefault(), Jsoup.connect(url).userAgent(userAgent).get());
        } catch (HttpStatusException e) {
            String airportSearchTerm = makeAirportSearchTerm(airport.getRawData().englishName);
            String url = EN_WIKI_URL + airportSearchTerm;
            return new DocumentCacheData(Locale.ENGLISH, Jsoup.connect(url).userAgent(userAgent).get());
        }
    }

    private static boolean isEnglishOnly(DocumentCacheData documentCacheData) {
        return !Locale.getDefault().equals(Locale.ENGLISH)
                && documentCacheData.locale.equals(Locale.ENGLISH);
    }

    /**
     * Load document from wikipedia.
     *
     * @param airport Airport to search
     * @throws HttpStatusException If page not found
     * @throws IOException         If an I/O error occurs
     */
    public AirportWikiCrawler(Airport airport) throws IOException {
        this.airport = airport;
        DocumentCacheData cachedDocumentData = documentCache.get(airport.getAirportName());
        if (cachedDocumentData != null) {
            document = cachedDocumentData.document;
            englishOnly = isEnglishOnly(cachedDocumentData);
        } else {
            DocumentCacheData documentCacheData = createDocument(airport);
            document = documentCacheData.document;
            englishOnly = isEnglishOnly(documentCacheData);
            documentCache.put(airport.getAirportName(), documentCacheData);
        }
    }

    /**
     * Get information about airport.
     *
     * @return Information about airport
     */
    public String getInformation() {
        try {
            String information = findAirportInformation();
            if (englishOnly && Setting.getInstance().isLocalizeEnglish())
                return translateInformation(information);
            return information;
        } catch (RuntimeException e) {
            return Translator.getBundleString("not_found");
        }
    }

    private String translateInformation(String information) {
        String cachedTranslation = translationCache.get(information);
        if (cachedTranslation != null)
            return cachedTranslation;
        String translatedInformation = Translator.translate(Locale.ENGLISH, Locale.getDefault(), information);
        translationCache.put(information, translatedInformation);
        return translatedInformation;
    }

    private String findAirportInformation() {
        Elements elements = document.select(".mw-parser-output p");
        for (Element element : elements) {
            if (!element.parents().select("table").isEmpty()) continue;
            
            String airportInfo = element.text().replaceAll("\\[[0-9a-zA-Z ]*]", "");
            if (airportInfo.contains(airport.getIATA().trim())
                    || airportInfo.contains(airport.getAirportName().trim())
                    || airportInfo.contains(airport.getRawData().englishName.trim()))
                return airportInfo;
        }
        throw new RuntimeException("not_found");
    }

    /**
     * Get array of URL string.
     *
     * @param numImages Number of maximum images.
     * @return Array of URL
     */
    public String[] getImageURLs(int numImages) {
        ArrayList<String> result = new ArrayList<>();
        Elements elements = document.select(".mw-parser-output img");
        int count = 0;
        for (Element element : elements) {
            if (count >= numImages)
                break;

            String sourceURL = element.attr("src").replaceFirst("^/+", "https://");
            if (sourceURL.contains("static"))
                continue;
            result.add(sourceURL);
            count += 1;
        }

        return result.toArray(new String[0]);
    }

    /**
     * Get array of BufferedImage.
     *
     * @param numImages Number of maximum images
     * @return Array of images
     * @throws IOException If an I/O error occurs
     */
    public BufferedImage[] getBufferedImages(int numImages) throws IOException {
        String[] imageURLs = getImageURLs(numImages);
        BufferedImage[] bufferedImages = new BufferedImage[imageURLs.length];
        int index = 0;
        for (String imageURL : imageURLs) {
            if (imageCache.containsKey(imageURL)) {
                bufferedImages[index] = imageCache.get(imageURL);
            } else {
                URL url = new URL(imageURL);
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
                BufferedImage bufferedImage = ImageIO.read(connection.getInputStream());
                bufferedImages[index] = bufferedImage;
                imageCache.put(imageURL, bufferedImage);
            }
            index += 1;
        }

        return bufferedImages;
    }
    
    private record DocumentCacheData(Locale locale, Document document) {
    }
}