package com.arttha.repository.search;

import com.arttha.domain.PushkarJhipster;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PushkarJhipster} entity.
 */
public interface PushkarJhipsterSearchRepository extends ElasticsearchRepository<PushkarJhipster, Long> {
}
