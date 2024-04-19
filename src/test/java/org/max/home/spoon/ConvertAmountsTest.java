package org.max.home.spoon;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.max.seminar.spoon.AbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConvertAmountsTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(ConvertAmountsTest.class);

    @Test
    void get_shouldReturn200() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        ConvertAmountsDto bodyResponse = new ConvertAmountsDto();
        bodyResponse.setAnswer("Answer");
        bodyResponse.setSourceAmount(100d);
        bodyResponse.setTargetAmount(200d);
        bodyResponse.setType("Count");
        bodyResponse.setTargetUnit("Soup");

        logger.debug("Формирование мока для GET /recipes/convert");
        stubFor(get(urlPathEqualTo("/recipes/convert"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyResponse))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl()+"/recipes/convert");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("ingredientName", "flour")
                .addParameter("sourceAmount", String.valueOf(2.5))
                .addParameter("sourceUnit", "cups")
                .addParameter("targetUnit", "grams")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/recipes/convert")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Answer", mapper.readValue(response.getEntity().getContent(), ConvertAmountsDto.class).getAnswer());

    }

    @Test
    void get_shouldReturn500() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 500 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        ConvertAmountsDto bodyResponse = new ConvertAmountsDto();
        bodyResponse.setAnswer("Internal Server Error");
        bodyResponse.setSourceAmount(100d);
        bodyResponse.setTargetAmount(0d);
        bodyResponse.setType("Error");
        bodyResponse.setTargetUnit("None");

        logger.debug("Формирование мока для GET /recipes/convert");
        stubFor(get(urlPathEqualTo("/recipes/convert"))
                .willReturn(aResponse()
                        .withStatus(500).withBody(mapper.writeValueAsString(bodyResponse))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl()+"/recipes/convert");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("ingredientName", "sugar")
                .addParameter("sourceAmount", String.valueOf(1.5))
                .addParameter("sourceUnit", "tablespoons")
                .addParameter("targetUnit", "grams")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/recipes/convert")));
        assertEquals(500, response.getStatusLine().getStatusCode());
        assertEquals("Internal Server Error", mapper.readValue(response.getEntity().getContent(), ConvertAmountsDto.class).getAnswer());
    }

    @Test
    void get_shouldReturn400() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 400 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        ConvertAmountsDto bodyResponse = new ConvertAmountsDto();
        bodyResponse.setAnswer("Bad Request");
        bodyResponse.setSourceAmount(0d);
        bodyResponse.setTargetAmount(0d);
        bodyResponse.setType("Error");
        bodyResponse.setTargetUnit("None");

        logger.debug("Формирование мока для GET /recipes/convert");
        stubFor(get(urlPathEqualTo("/recipes/convert"))
                .willReturn(aResponse()
                        .withStatus(400).withBody(mapper.writeValueAsString(bodyResponse))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl()+"/recipes/convert");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("ingredientName", "salt")
                .addParameter("sourceAmount", String.valueOf(0))
                .addParameter("sourceUnit", "teaspoons")
                .addParameter("targetUnit", "grams")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/recipes/convert")));
        assertEquals(400, response.getStatusLine().getStatusCode());
        assertEquals("Bad Request", mapper.readValue(response.getEntity().getContent(), ConvertAmountsDto.class).getAnswer());
    }

    @Test
    void get_shouldReturn404() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 404 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        ConvertAmountsDto bodyResponse = new ConvertAmountsDto();
        bodyResponse.setAnswer("Not Found");
        bodyResponse.setSourceAmount(0d);
        bodyResponse.setTargetAmount(0d);
        bodyResponse.setType("Error");
        bodyResponse.setTargetUnit("None");

        logger.debug("Формирование мока для GET /recipes/convert");
        stubFor(get(urlPathEqualTo("/recipes/convert"))
                .willReturn(aResponse()
                        .withStatus(404).withBody(mapper.writeValueAsString(bodyResponse))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl()+"/recipes/convert");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("ingredientName", "pepper")
                .addParameter("sourceAmount", String.valueOf(0))
                .addParameter("sourceUnit", "pinches")
                .addParameter("targetUnit", "grams")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/recipes/convert")));
        assertEquals(404, response.getStatusLine().getStatusCode());
        assertEquals("Not Found", mapper.readValue(response.getEntity().getContent(), ConvertAmountsDto.class).getAnswer());
    }
}

