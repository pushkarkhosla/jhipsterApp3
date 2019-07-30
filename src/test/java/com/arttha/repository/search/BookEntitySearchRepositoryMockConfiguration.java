package com.arttha.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link BookEntitySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BookEntitySearchRepositoryMockConfiguration {

    @MockBean
    private BookEntitySearchRepository mockBookEntitySearchRepository;

}