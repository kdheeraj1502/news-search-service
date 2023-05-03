package com.news.aggregator.search.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.aggregator.search.dtos.*;
import com.news.aggregator.search.exceptions.JsonParsingException;
import com.news.aggregator.search.exceptions.NewsCustomExceptionHandler;
import com.news.aggregator.search.exceptions.RecordNotFoundException;
import com.news.aggregator.search.models.NewsResponseMaster;
import com.news.aggregator.search.models.PageDetails;
import com.news.aggregator.search.services.NewsSearchService;
import com.news.aggregator.search.utils.Constants;
import com.news.aggregator.search.utils.NewsResponseUtility;
import com.news.aggregator.search.utils.PaginationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

//@Slf4j
@Service
public class NewsSearchImpl implements NewsSearchService {
    private final Logger LOG = LoggerFactory.getLogger(NewsSearchImpl.class);

    private static final String API_KEY = "RC0IVXfM38l8G0QRyd0CCbkEQQxOW8mr";
    private static final String A_API_KY = "4c494de1-e09d-45b9-914a-8cf86657c045";

    //  @Autowired
    // private NewsResponseMapper newsResponseMapper;

    @Autowired
    private RestTemplate restTemplate;

    public NewsResponseMaster searchNews(int page, int perPage, String query) {
        NewsResponseMaster newsResponseMaster = new NewsResponseMaster();
        try{
            long startTime = System.currentTimeMillis();
            newsResponseMaster.setCreatedAt(new Date());
            List<NYTimesUSNewsresponseDto> nyTimesUSNewsresponseDtoList = NYTimesUSNewsSearch(query);
            GuardianUKNewsResponseMaster guardianUKNewsResponseMaster = guardianUKNewsSearch(query);
            NewsResponse newsResponse = NewsResponseUtility.createResponse(guardianUKNewsResponseMaster, nyTimesUSNewsresponseDtoList);
            Set<Map.Entry<PageDetails, List<Map<String, NewsData>>>> set =  newsResponse.getTodaysNews().get(LocalDate.now()).entrySet();
            Iterator<Map.Entry<PageDetails, List<Map<String, NewsData>>>> iterator = set.iterator();
            List<Map<String, NewsData>> source = new ArrayList<>();
            List<PageDetails> pageDetails =
                    newsResponse.getTodaysNews().get(LocalDate.now()).keySet().stream().collect(Collectors.toList());
            while(iterator.hasNext()){
                Map.Entry<PageDetails, List<Map<String, NewsData>>> entry = iterator.next();
                source.addAll(entry.getValue());
            }
            newsResponseMaster.setTotalNoOfPages(pageDetails.get(0).getPageSize());
            newsResponseMaster.setUserSearchKeyword(query);
            newsResponseMaster.setDataCount(pageDetails.get(0).getTotalData());
            newsResponseMaster.setPreviousPageNo(page);
            newsResponseMaster.setNextPageNo(page + 1);

            Map<String, List<String>> urlMap = new HashMap<>();
             List<String> urls = new ArrayList<>();
             Map<String, List<String>> headlineMap = new HashMap<>();
             List<String> headlines = new ArrayList<>();
            List<Map<String, NewsData>> result = PaginationUtility.getPage(source, page, perPage);
            for(Map<String, NewsData> res : result){
                Set<Map.Entry<String, NewsData>> dataSet = res.entrySet();
                Iterator<Map.Entry<String, NewsData>> it = dataSet.iterator();
                while(it.hasNext()){
                    Map.Entry<String, NewsData> entry = it.next();
                    urls.add(entry.getKey());
                    headlines.add(entry.getValue().getHeadlines());
                    newsResponseMaster.setCity(entry.getValue().getSection());
                }
            }
            urlMap.put("URLs", urls);
            headlineMap.put("Headlines", headlines);
            newsResponseMaster.setURL(urlMap);
            newsResponseMaster.setHeadline(headlineMap);
            newsResponseMaster.setUpdatedAt(new Date());
            return newsResponseMaster;
        } catch(Exception exception){
            throw new RecordNotFoundException("News for :: [ " + query + " ] does no exist. Please check your search query");
        }
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

            for(LinkedHashMap<String, String> map : obj){
                NYTimesUSNewsresponseDto nyTimesUSNewsresponse = new NYTimesUSNewsresponseDto();
                nyTimesUSNewsresponse.setAbstractData(map.get("abstract"));
                nyTimesUSNewsresponse.setWebUrl(map.get("web_url"));
                nyTimesUSNewsresponse.setSnippet(map.get("snippet"));
                nyTimesUSNewsresponse.setLead_paragraph(map.get("lead_paragraph"));
                nyTimesUSNewsresponse.setSource(map.get("source"));
                Object headlineObj = map.get("headline");
                LinkedHashMap<String, String> headLineMap = null;
                if(headlineObj instanceof LinkedHashMap){
                    headLineMap = (LinkedHashMap<String, String>) headlineObj;
                    nyTimesUSNewsresponse.setHeadline(headLineMap);
                }
                String pubDate = map.get("pub_date");
                nyTimesUSNewsresponse.setPub_date(pubDate);
                nyTimesUSNewsresponseDtoList.add(nyTimesUSNewsresponse);
            }
        } catch (JsonProcessingException exception) {
            throw new JsonParsingException("The news response could not be parsed");
        } catch (Exception exception) {
            throw new RecordNotFoundException("News report not found for search query");
        }
        return nyTimesUSNewsresponseDtoList;
    }

    private  GuardianUKNewsResponseMaster  guardianUKNewsSearch(String query) {
        ResponseEntity<String> response = null;
        GuardianUKNewsResponse guardianUKNewsResponse = null;
        GuardianUKNewsResponseMaster guardianUKNewsResponseMaster = new GuardianUKNewsResponseMaster();
        List<GuardianUKNewsResponseDto> guardianUKNewsResponseDtoList = new ArrayList<>();
        try {
            response = this.restTemplate.exchange(
                    Constants.NewsURLs.GUARDIAN_UK_URL + query + "&api-key=" + A_API_KY,
                    HttpMethod.GET, null, String.class);
            ObjectMapper mapper = new ObjectMapper();
            guardianUKNewsResponse = mapper.readValue(response.getBody(), GuardianUKNewsResponse.class);
            LinkedHashMap<String, Object> map = guardianUKNewsResponse.getResponse();

            guardianUKNewsResponseMaster.setTotalData((Integer) map.get("total"));
            guardianUKNewsResponseMaster.setStartIndex ((Integer) map.get("startIndex"));
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
            throw new JsonParsingException("The news response could not be parsed");
        } catch (Exception exception) {
            throw new RecordNotFoundException("News report not found for search query");
        }
        return guardianUKNewsResponseMaster;

    }
}