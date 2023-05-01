package com.SelfBuildApp.ddd.Project.domain.WebsiteCrawler;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Project.domain.CssModelFactory.CssFactory;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGB;
import com.SelfBuildApp.ddd.Project.domain.Unit.Named;
import com.SelfBuildApp.ddd.Support.infrastructure.Generator.impl.HtmlNodeShortUUID;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.core.io.Resource;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.w3c.dom.css.RGBColor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.print.attribute.standard.Media;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class PageCrawler {

    @Value("classpath:script.js")
    private Resource javaScript;

    @Autowired
    private CssFactory cssFactory;

    private Map<String, MediaQuery> mediaQueryMapUnique;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private HtmlNodeShortUUID shortUUID;

    public PageCrawler() {
        mediaQueryMapUnique = new HashMap<>();

    }


    public void run(String URL, HtmlProject dbEntity) throws IOException {
        // set chrome driver exe path
        System.setProperty("webdriver.chrome.driver", "/Users/romanpytka/Downloads/chromedriver");
        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(URL);
//        List<WebElement> elemnts = new By.ByCssSelector("*");

//        driver.findElement(By.cssSelector(".cmp-intro_acceptAll")).click();
//        WebDriverWait wait = new WebDriverWait(driver, 1);
        // find the search textbar in JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
//            for (WebElement htmlEl: driver.findElements(new By.ByCssSelector("body > *"))) {
//                if (htmlEl.getTagName().equals("html") || htmlEl.getTagName().equals("body") || htmlEl.getTagName().equals("head")) {
//                    continue;
//                }
//                System.out.println(htmlEl.getTagName());
//                System.out.println(htmlEl.getText());
//                System.out.println(htmlEl.getAttribute("class"));
//                List<WebElement> elements = htmlEl.findElements(By.cssSelector(":scope > *"));
//                for (WebElement htmlElN: elements) {
//                    System.out.println(htmlElN.getTagName());
//                    System.out.println(htmlElN.getText());
//                    System.out.println(htmlElN.getAttribute("class"));
//                }
//
////                wait.until(ExpectedConditions.elementToBeClickable(htmlEl));
//
////                continue;
//            }
        } catch (Exception e) {
                System.out.println(e.getMessage());

        }

        List<String> jsCodeList = Files.readAllLines(javaScript.getFile().toPath());
        StringBuilder jsCode = new StringBuilder();

        for (String line : jsCodeList) {
            jsCode.append(line + "\n");
        }

        String jsCodeStr = jsCode.toString();

//        Object searchTextbar = js.executeScript("return document.styleSheets[0]");
        Object json = js.executeScript(jsCodeStr);

        ObjectMapper mapper = new ObjectMapper();
//        Map<String,Object> map = mapper.readValue((byte[]) json, Map.class);

        JsonNode rootNode = mapper.readTree((String) json);

        JsonNode tags = rootNode.get("tags");
        JsonNode animations = rootNode.get("animations");

        mapToEntities(tags, dbEntity, null);
        // we have to cast the returned object into webelement to access
        // all the webelement related methods
//        ((WebElement) searchTextbar).sendKeys("abc");
//        String bgColor = driver.findElement(By.xpath("//h1[0]")).getCssValue("background-color");
//        String color = driver.findElement(By.xpath("//input[contains(@class,'searchSubmit')]")).getCssValue("background");
//        String borderBottomWidth = driver.findElement(By.xpath("//button[contains(@class,'btn-primary')]")).getCssValue("border-bottom-width");
//        System.out.println("Css Value for background color is : "+ bgColor);
//        System.out.println("Css Value for color is : "+ color);
//        System.out.println("Css Value for border bottom color is : "+ borderBottomWidth);
        driver.quit();
    }

    @Transactional
    private void mapToEntities(JsonNode tags, HtmlProject dbEntity, HtmlTag parent) {

        Map<String, MediaQuery> mediaQueryMap = new HashMap<>();

        for (JsonNode node : tags) {
            HtmlTag tag = new HtmlTag();
            String generateShortUUid = shortUUID.generateUniqueExcludedMemory(dbEntity.getId());

            tag.setShortUuid(generateShortUUid);
            tag.setTagName(node.get("tagName").toString().replaceAll("[\"]*", ""));
            tag.setProject(dbEntity);
            tag.setParent(parent);

            if (tag.getTagName() == "img" || tag.getTagName() == "input" || tag.getTagName() == "br") {
                tag.setClosingTag(false);
            }

            if (node.get("content") != null) {
                if (node.get("content").toString() != "null") {
                    tag.setSvgContent(node.get("content").toString().
                            replaceAll("^\"", "")
                                    .replaceAll("[\n]*", "")
                                    .replaceAll("\"$", ""));

                }
            }

            if (node.get("text") != null && !node.get("text").toString().isEmpty()) {
                TextNode text  = new TextNode();

                String generateShortUUidTextNode = shortUUID.generateUniqueExcludedMemory(dbEntity.getId());

                text.setShortUuid(generateShortUUidTextNode);

                text.setText(node.get("text").toString().
                        replaceAll("^\"", "")
                                .replaceAll("[\n]*", "")
                                .replaceAll("\"$", ""));
                text.setParent(tag);
                text.setProject(dbEntity);
                entityManager.persist(text);

            }


            Map<String, HtmlTagAttr> tagAttributes = new HashMap<>();
            for (JsonNode el : node.get("attributes")) {
                HtmlTagAttr attr = new HtmlTagAttr();
                attr.setKey(el.get("name").toString().replaceAll("[\"]*", ""));
                attr.setValue(el.get("value").toString().replaceAll("[\"]*", ""));

                tagAttributes.put(attr.getKey(), attr);
            }

            System.out.println(tag.getTagName());
            System.out.println(tagAttributes.size());
            tag.setAttrs(tagAttributes);
//            if () {
//
//            }

            for (JsonNode css : node.get("css")) {
                MediaQuery mediaQuery = null;
                if (css.has("media")) {
//                    if (mediaQueryMapUnique.containsKey(css.get("media").toString())) {
//                        mediaQuery = mediaQueryMapUnique.get((Object)css.get("media").toString());
//                    } else {
//                        mediaQuery = createMediaQuery(css.get("media").toString());
//
//                    }
                    // TODO implement media query factory
//                    continue;
                }
                addCssToEntities(css.get("props"), tag, mediaQuery);
            }


            Map<String, PseudoSelector> mediMap = new HashMap<>();

            entityManager.persist(tag);

            mapToEntities(node.get("children"), dbEntity, tag);

        }

    }

    private MediaQuery createMediaQuery(String media) {
        MediaQuery m = new MediaQuery();

        m.setName(media);
        m.setColor("red");
        m.setColorUnitName(Named.NAME);

        return null;
    }

    private void addCssToEntities(JsonNode cssList, HtmlTag dbEntity, MediaQuery mediaQuery) {
        Iterator<JsonNode> nodeIterator = cssList.getElements();
        Iterator<String> stringIterator = cssList.getFieldNames();
        while ( nodeIterator.hasNext() && stringIterator.hasNext()) {
            JsonNode cssVal = nodeIterator.next();
            CssStyle css = cssFactory.build(stringIterator.next(), cssVal.getTextValue());
            if (css == null) {
                continue;
            }
            css.updateCssIdentity();
            if (dbEntity.cssUniqueMap.containsKey(css.getCssIdentity())) {
                continue;
            }
            if (mediaQuery == null) {
//                if (dbEntity.cssUniqueMap.containsKey()) {
//
//                }
            }
            dbEntity.cssUniqueMap.put(css.getCssIdentity(), css);
            css.setHtmlTag(dbEntity);
            css.setMediaQuery(mediaQuery);
//            dbEntity.addCssStyle()
            entityManager.persist(css);

            Map<String, PseudoSelector> mediMap = new HashMap<>();

        }
    }
}
