package com.arttha.service.impl;

import com.arttha.service.PushkarJhipsterService;
import com.arttha.domain.PushkarJhipster;
import com.arttha.repository.PushkarJhipsterRepository;
import com.arttha.repository.search.PushkarJhipsterSearchRepository;
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
 * Service Implementation for managing {@link PushkarJhipster}.
 */
@Service
@Transactional
public class PushkarJhipsterServiceImpl implements PushkarJhipsterService {

    private final Logger log = LoggerFactory.getLogger(PushkarJhipsterServiceImpl.class);

    private final PushkarJhipsterRepository pushkarJhipsterRepository;

    private final PushkarJhipsterSearchRepository pushkarJhipsterSearchRepository;

    public PushkarJhipsterServiceImpl(PushkarJhipsterRepository pushkarJhipsterRepository, PushkarJhipsterSearchRepository pushkarJhipsterSearchRepository) {
        this.pushkarJhipsterRepository = pushkarJhipsterRepository;
        this.pushkarJhipsterSearchRepository = pushkarJhipsterSearchRepository;
    }

    /**
     * Save a pushkarJhipster.
     *
     * @param pushkarJhipster the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PushkarJhipster save(PushkarJhipster pushkarJhipster) {
        log.debug("Request to save PushkarJhipster : {}", pushkarJhipster);
        PushkarJhipster result = pushkarJhipsterRepository.save(pushkarJhipster);
        pushkarJhipsterSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the pushkarJhipsters.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PushkarJhipster> findAll() {
        log.debug("Request to get all PushkarJhipsters");
        return pushkarJhipsterRepository.findAll();
    }


    /**
     * Get one pushkarJhipster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PushkarJhipster> findOne(Long id) {
        log.debug("Request to get PushkarJhipster : {}", id);
        return pushkarJhipsterRepository.findById(id);
    }

    /**
     * Delete the pushkarJhipster by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PushkarJhipster : {}", id);
        pushkarJhipsterRepository.deleteById(id);
        pushkarJhipsterSearchRepository.deleteById(id);
    }

    /**
     * Search for the pushkarJhipster corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PushkarJhipster> search(String query) {
        log.debug("Request to search PushkarJhipsters for query {}", query);
        return StreamSupport
            .stream(pushkarJhipsterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
