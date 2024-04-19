package org.max.home.accu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.max.home.accu.weather.DailyForecast;
import org.max.seminar.spoon.AbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Get10DayForecastTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(Get10DayForecastTest.class);

    @Test
    void get_shouldReturn200() throws IOException {
        logger.info("Запуск теста с кодом ответа 200");
        //given
        ObjectMapper mapper = new ObjectMapper();
        DailyForecast forecast = new DailyForecast();
        forecast.setTemperature(25);


        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/12345");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/12345"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(forecast))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/forecasts/v1/daily/10day/12345");
        logger.debug("Создание HTTP-клиента");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/12345")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals(25, mapper.readValue(response.getEntity().getContent(), DailyForecast.class).getTemperature());

    }

    @Test
    void get_shouldReturn500() throws IOException {
        logger.info("Запуск теста с кодом ответа 500");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/12345");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/12345"))
                .willReturn(aResponse()
                        .withStatus(500).withBody("ERROR")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/forecasts/v1/daily/10day/12345");
        logger.debug("Создание HTTP-клиента");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/12345")));
        assertEquals(500, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }

    @Test
    void get_shouldReturn400() throws IOException {
        logger.info("Запуск теста с кодом ответа 400");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/12345");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/12345"))
                .willReturn(aResponse()
                        .withStatus(400).withBody("BAD REQUEST")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/forecasts/v1/daily/10day/12345");
        logger.debug("Создание HTTP-клиента");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/12345")));
        assertEquals(400, response.getStatusLine().getStatusCode());
        assertEquals("BAD REQUEST", convertResponseToString(response));
    }

    @Test
    void get_shouldReturn404() throws IOException {
        logger.info("Запуск теста с кодом ответа 404");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/12345");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/12345"))
                .willReturn(aResponse()
                        .withStatus(404).withBody("NOT FOUND")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/forecasts/v1/daily/10day/12345");
        logger.debug("Создание HTTP-клиента");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/12345")));
        assertEquals(404, response.getStatusLine().getStatusCode());
        assertEquals("NOT FOUND", convertResponseToString(response));
    }

    @Test
    void get_shouldReturn401() throws IOException {
        logger.info("Запуск теста с кодом ответа 401");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/12345");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/12345"))
                .willReturn(aResponse()
                        .withStatus(401).withBody("UNAUTHORIZED")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/forecasts/v1/daily/10day/12345");
        logger.debug("Создание HTTP-клиента");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/12345")));
        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals("UNAUTHORIZED", convertResponseToString(response));
    }

}
