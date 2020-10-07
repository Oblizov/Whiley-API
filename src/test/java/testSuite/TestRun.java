package testSuite;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import settings.Preparation;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRun {

    Preparation prep = new Preparation();

    @DisplayName("1 Checking the search query on the Wiley site")
    @Test
    public void testOne(){

        JsonPath responseResult = prep.getResultWileySearch();

        assertEquals(responseResult.getList("suggestions.term").size(), 4);
        assertTrue(responseResult.getList("suggestions.term").stream().map(Object::toString).allMatch(x -> x.contains("<span class=\"search-highlight\">java</span>")));
        assertEquals(responseResult.getList("pages.title").size(), 4);
        assertTrue(responseResult.getList("pages.title").stream().map(Object::toString).allMatch(x -> x.contains("Wiley")));

    }

    @DisplayName("2.1 Request with a delay of 10 seconds")
    @Test
    public void testTwo1(){

        assertThat(prep.sendDelayQuery(10), equalTo(10L));

    }

    @DisplayName("2.2 Request with a delay of 11 seconds")
    @Test
    public void testTwo2(){

        assertThat(prep.sendDelayQuery(11), equalTo(10L));

    }

    @DisplayName("2.3 Request with a delay of  1 seconds")
    @Test
    public void testTwo3(){

        assertThat(prep.sendDelayQuery(1), equalTo(1L));

    }

    @DisplayName("3.1 Check content type of the image")
    @Test
    public void testThree1(){

        prep.prepareImageRequest();

        assertEquals(prep.getImageContentType(), "image/png");

    }

    @DisplayName("3.2 Check image type")
    @Test
    public void testThree2() throws IOException {

        prep.prepareImageRequest();
        InputStream result = prep.getImageInInputStream();

        assertEquals(ImageIO.read(result).getType(), BufferedImage.TYPE_3BYTE_BGR);
        result.close();

    }

    @DisplayName("3.3 Сheck the height of the image")
    @Test
    public void testThree3() throws IOException {

        prep.prepareImageRequest();
        InputStream result = prep.getImageInInputStream();

        assertEquals(ImageIO.read(result).getHeight(), 100);
        result.close();
    }

    @DisplayName("3.4 Сheck the width of the image")
    @Test
    public void testThree4() throws IOException {

        prep.prepareImageRequest();
        InputStream result = prep.getImageInInputStream();

        assertEquals(ImageIO.read(result).getWidth(), 100);
        result.close();
    }



}
