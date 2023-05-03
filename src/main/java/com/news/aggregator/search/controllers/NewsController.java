package com.news.aggregator.search.controllers;

import com.news.aggregator.search.models.NewsResponseMaster;
import com.news.aggregator.search.services.NewsSearchService;
import com.news.aggregator.search.utils.Constants;
import com.news.aggregator.search.utils.NullAwareBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(Constants.Routes.NEWS_REQUEST)
@Api(value = "Provides API to customise the user search on International news")
public class NewsController {

    private static final Logger LOG = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsSearchService newsSearchService;

    private static final BeanUtilsBean beanUtils = new NullAwareBeanUtils();

    @ApiOperation(value = "Health Check")
    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity ping() {
        return ResponseEntity.ok().body("Success!");
    }


    @ApiOperation(value = "This API is exposed for user search on Internation news")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Bad request"),
                    @ApiResponse(code = 200, message = "Successful"),
                    @ApiResponse(code = 200, message = "The used search news", response = NewsResponseMaster.class)
            }

    )
    @GetMapping(value = Constants.Routes.ARTICLE_SEARCH + "/{query}", produces = "application/json")
    public NewsResponseMaster searchNews(@PathVariable(value = "query", required = true) String query,
                                         @RequestParam(name = Constants.UrlParameters.PAGE, defaultValue = "1") @Min(1) int page,
                                         @RequestParam(name = Constants.UrlParameters.PER_PAGE, defaultValue = "100") @Min(5) int perPage)  {
        LOG.info("The search keyword for News is : {} ", query);
        return newsSearchService.searchNews(page, perPage,query);
    }
} 