package com.pjhu.medicine.cms.domain.model

import com.google.common.collect.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@EnableAutoConfiguration
@ContextConfiguration(classes = ContentRepository.class)
@DataJpaTest
class ContentRepositoryTest extends Specification {

    @Autowired
    private ContentRepository repository

    void setup() {
        def attribute_1 = ContentAttribute.builder()
                .name("title")
                .value("brand1")
                .build()
        def attribute_2 = ContentAttribute.builder()
                .name("title")
                .value("brand2")
                .build()
        def content = Content.builder()
                .contentType(ContentType.BRAND)
                .contentStatus(ContentStatus.DRAFT)
                .build()
        def  contentVersion = ContentVersion.builder()
                .content(content)
                .publishedVersion(1)
                .contentStatus(ContentStatus.DRAFT)
                .contentAttributes(Arrays.asList(attribute_1, attribute_2))
                .build()
        content.setContentVersions(new ArrayList<>(Lists.newArrayList(contentVersion)))
        repository.save(content)
    }

    def "content_entity_should_exist"() {
        expect:
        def listOfContents = repository.findAll()
        listOfContents.size() == 1
        def firstContent = listOfContents.get(0)
        firstContent.getContentStatus() == ContentStatus.DRAFT
        firstContent.getContentVersions().size() == 1
        firstContent.getContentVersions().get(0).getContentAttributes().size() == 2
    }
}
