package com.arttha.repository.search;

import com.arttha.domain.BookEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link BookEntity} entity.
 */
public interface BookEntitySearchRepository extends ElasticsearchRepository<BookEntity, Long> {
}
