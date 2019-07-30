package com.arttha.service.impl;

import com.arttha.service.BookEntityService;
import com.arttha.domain.BookEntity;
import com.arttha.repository.BookEntityRepository;
import com.arttha.repository.search.BookEntitySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link BookEntity}.
 */
@Service
@Transactional
public class BookEntityServiceImpl implements BookEntityService {

    private final Logger log = LoggerFactory.getLogger(BookEntityServiceImpl.class);

    private final BookEntityRepository bookEntityRepository;

    private final BookEntitySearchRepository bookEntitySearchRepository;

    public BookEntityServiceImpl(BookEntityRepository bookEntityRepository, BookEntitySearchRepository bookEntitySearchRepository) {
        this.bookEntityRepository = bookEntityRepository;
        this.bookEntitySearchRepository = bookEntitySearchRepository;
    }

    /**
     * Save a bookEntity.
     *
     * @param bookEntity the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BookEntity save(BookEntity bookEntity) {
        log.debug("Request to save BookEntity : {}", bookEntity);
        BookEntity result = bookEntityRepository.save(bookEntity);
        bookEntitySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the bookEntities.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> findAll() {
        log.debug("Request to get all BookEntities");
        return bookEntityRepository.findAll();
    }


    /**
     * Get one bookEntity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookEntity> findOne(Long id) {
        log.debug("Request to get BookEntity : {}", id);
        return bookEntityRepository.findById(id);
    }

    /**
     * Delete the bookEntity by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookEntity : {}", id);
        bookEntityRepository.deleteById(id);
        bookEntitySearchRepository.deleteById(id);
    }

    /**
     * Search for the bookEntity corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> search(String query) {
        log.debug("Request to search BookEntities for query {}", query);
        return StreamSupport
            .stream(bookEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
