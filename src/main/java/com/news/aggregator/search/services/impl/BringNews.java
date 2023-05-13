package com.news.aggregator.search.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.aggregator.search.dtos.*;
import com.news.aggregator.search.exceptions.JsonParsingException;
import com.news.aggregator.search.exceptions.RecordNotFoundException;
import com.news.aggregator.search.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Configuration
public class BringNews<T> {

    private final Logger LOG = LoggerFactory.getLogger(NewsSearchImpl.class);

    @Value("${ny.time.us.api.key}")
    private String API_KEY;

    @Value("${guardian.uk.api.key}")
    private String A_API_KY;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GuardianUKNewsResponseMaster guardianUKNewsResponseMaster;

    public <T extends Object> T nyTimesUSNews(String query) {
        return (T) NYTimesUSNewsSearch(query);
    }

    public <T extends Object> T guardianUKNews(String query) {
        return (T) guardianUKNewsSearch(query);
    }


    private List<NYTimesUSNewsresponseDto> NYTimesUSNewsSearch(String query) {
        ResponseEntity<String> response = null;
        NYTimesUSNewsResponse newsResponse = null;
        List<LinkedHashMap<String, String>> obj = null;
        List<NYTimesUSNewsresponseDto> nyTimesUSNewsresponseDtoList = new ArrayList<>();
        try {
            response = this.restTemplate.exchange(
                    Constants.NewsURLs.NY_TIMES_US_URL + query + "&api-key=" + API_KEY,
                    HttpMethod.GET, null, String.class);
            ObjectMapper mapper = new ObjectMapper();
            newsResponse = mapper.readValue(response.getBody(), NYTimesUSNewsResponse.class);
            obj = (List<LinkedHashMap<String, String>>) newsResponse.getResponses().get("docs");

            for (LinkedHashMap<String, String> map : obj) {
                NYTimesUSNewsresponseDto nyTimesUSNewsresponse = new NYTimesUSNewsresponseDto();
                nyTimesUSNewsresponse.setAbstractData(map.get("abstract"));
                nyTimesUSNewsresponse.setWebUrl(map.get("web_url"));
                nyTimesUSNewsresponse.setSnippet(map.get("snippet"));
                nyTimesUSNewsresponse.setLead_paragraph(map.get("lead_paragraph"));
                nyTimesUSNewsresponse.setSource(map.get("source"));
                Object headlineObj = map.get("headline");
                LinkedHashMap<String, String> headLineMap = null;
                if (headlineObj instanceof LinkedHashMap) {
                    headLineMap = (LinkedHashMap<String, String>) headlineObj;
                    nyTimesUSNewsresponse.setHeadline(headLineMap);
                }
                String pubDate = map.get("pub_date");
                nyTimesUSNewsresponse.setPub_date(pubDate);
                nyTimesUSNewsresponseDtoList.add(nyTimesUSNewsresponse);
            }
        } catch (JsonProcessingException exception) {
            LOG.error("The news response could not be parsed");
            throw new JsonParsingException("The news response could not be parsed");
        } catch (Exception exception) {
            throw new RecordNotFoundException("News report not found for search query");
        }
        return nyTimesUSNewsresponseDtoList;
    }

    private GuardianUKNewsResponseMaster guardianUKNewsSearch(String query) {
        ResponseEntity<String> response = null;
        GuardianUKNewsResponse guardianUKNewsResponse = null;
        List<GuardianUKNewsResponseDto> guardianUKNewsResponseDtoList = new ArrayList<>();
        try {
            response = this.restTemplate.exchange(
                    Constants.NewsURLs.GUARDIAN_UK_URL + query + "&api-key=" + A_API_KY,
                    HttpMethod.GET, null, String.class);
            ObjectMapper mapper = new ObjectMapper();
            guardianUKNewsResponse = mapper.readValue(response.getBody(), GuardianUKNewsResponse.class);
            LinkedHashMap<String, Object> map = guardianUKNewsResponse.getResponse();

            guardianUKNewsResponseMaster.setTotalData((Integer) map.get("total"));
            guardianUKNewsResponseMaster.setStartIndex((Integer) map.get("startIndex"));
            guardianUKNewsResponseMaster.setPageSize((Integer) map.get("pageSize"));
            guardianUKNewsResponseMaster.setCurrentPage((Integer) map.get("currentPage"));
            guardianUKNewsResponseMaster.setPageCount((Integer) map.get("pages"));

            guardianUKNewsResponseMaster.setOrderBy((String) map.get("orderBy"));

            List<LinkedHashMap<String, String>> listMap = (List<LinkedHashMap<String, String>>) map.get("results");
            for (LinkedHashMap<String, String> res : listMap) {
                GuardianUKNewsResponseDto guardianUKNewsResponseDto = new GuardianUKNewsResponseDto();
                guardianUKNewsResponseDto.setId(res.get("id"));
                guardianUKNewsResponseDto.setSectionId(res.get("sectionId"));
                guardianUKNewsResponseDto.setSectionName(res.get("sectionName"));
                guardianUKNewsResponseDto.setWebPublicationDate(res.get("webPublicationDate"));
                guardianUKNewsResponseDto.setWebTitle(res.get("webTitle"));
                guardianUKNewsResponseDto.setWebUrl(res.get("webUrl"));
                guardianUKNewsResponseDto.setApiUrl(res.get("apiUrl"));
                guardianUKNewsResponseDto.setPillarId(res.get("pillarId"));
                guardianUKNewsResponseDto.setPillarName(res.get("pillarName"));
                guardianUKNewsResponseDtoList.add(guardianUKNewsResponseDto);
            }
            guardianUKNewsResponseMaster.setGuardianUKNewsResponseDtoList(guardianUKNewsResponseDtoList);
        } catch (JsonProcessingException exception) {
            LOG.error("The news response could not be parsed");
            throw new JsonParsingException("The news response could not be parsed");
        } catch (Exception exception) {
            LOG.error("News report not found for search query");
            throw new RecordNotFoundException("News report not found for search query");
        }
        return guardianUKNewsResponseMaster;

    }
}
