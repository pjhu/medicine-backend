package com.pjhu.medicine.cms.adpter.persistence;

import com.pjhu.medicine.cms.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ContentQueryRepositoryAdapter implements ContentQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Content> getOrderBy(long id) {
        return Optional.empty();
    }

    @Override
    public Page<Content> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ContentWithAttribute> findAllForAdmin(ContentType contentType, Pageable pageable) {
        @SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection"})
        String rowCountSql = "SELECT COUNT(id) FROM content WHERE content_status IN ('PUBLISHED', 'DRAFT')";
        @SuppressWarnings("ConstantConditions")
        long total = jdbcTemplate.queryForObject(rowCountSql, Long.class);

        @SuppressWarnings("SqlNoDataSourceInspection")
        String querySql = "SELECT    cv.content_id, \n" +
                "          cv.published_at, \n" +
                "          cv.content_status, \n" +
                "          content_attribute.content_version_id, \n" +
                "          content_attribute.name, \n" +
                "          content_attribute.value \n" +
                "FROM      ( \n" +
                "                    SELECT    content_version.id             AS content_version_id, \n" +
                "                              content_version.content_id     AS content_id, \n" +
                "                              content_version.published_at   AS published_at, \n" +
                "                              content_version.content_status AS content_status \n" +
                "                    FROM      content \n" +
                "                    LEFT JOIN content_version \n" +
                "                    ON        content.id = content_version.content_id \n" +
                "                    WHERE     content.content_type=? \n" +
                "                    AND       content.content_status IN ('PUBLISHED', \n" +
                "                                                         'DRAFT') \n" +
                "                    AND       content_version.content_status IN ('DRAFT', \n" +
                "                                                                 'EDITING') \n" +
                "                    ORDER BY  content_id DESC offset ? limit ?) AS cv \n" +
                "LEFT JOIN content_attribute \n" +
                "ON        cv.content_version_id = content_attribute.content_version_id";
        RowMapper<ContentWithAttribute> rowMapper = new BeanPropertyRowMapper<>(ContentWithAttribute.class);
        List<ContentWithAttribute> contentVersions = jdbcTemplate.query(querySql, rowMapper,
                ContentType.BRAND.name(), (pageable.getPageNumber() - 1) * pageable.getPageSize(),
                pageable.getPageSize());
        return new PageImpl<>(contentVersions, pageable, total);
    }

}
