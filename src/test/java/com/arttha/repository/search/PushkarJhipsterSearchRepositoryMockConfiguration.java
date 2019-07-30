package com.arttha.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PushkarJhipsterSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PushkarJhipsterSearchRepositoryMockConfiguration {

    @MockBean
    private PushkarJhipsterSearchRepository mockPushkarJhipsterSearchRepository;

}
