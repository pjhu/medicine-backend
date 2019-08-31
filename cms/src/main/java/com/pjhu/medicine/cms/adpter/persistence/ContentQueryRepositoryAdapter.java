package com.pjhu.medicine.cms.adpter.persistence;

import com.pjhu.medicine.cms.domain.model.Content;
import com.pjhu.medicine.cms.domain.model.ContentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ContentQueryRepositoryAdapter implements ContentQueryRepository {

    @Override
    public Optional<Content> getOrderBy(long id) {
        return Optional.empty();
    }

    @Override
    public Page<Content> findAll(Pageable pageable) {
        return null;
    }
}
