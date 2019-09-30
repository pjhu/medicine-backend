package com.pjhu.medicine.cms.adpter.resource.view

import com.fasterxml.jackson.databind.ObjectMapper
import com.pjhu.medicine.cms.application.service.ContentApplicationService
import com.pjhu.medicine.cms.application.service.ContentQueryService
import com.pjhu.medicine.cms.application.service.command.ContentAttributeCommand
import com.pjhu.medicine.cms.application.service.command.ContentCreateCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = ContentController.class)
@WebMvcTest
class ContentControllerTest extends Specification {

    @Autowired
    protected MockMvc mvc
    @Autowired
    private ObjectMapper objectMapper

    @MockBean
    private ContentApplicationService contentApplicationService;
    @MockBean
    private ContentQueryService contentQueryService

    def "getting"() {
        given:
        contentApplicationService.create() >> 1
        def contentAttribute1 = ContentAttributeCommand.builder()
                .name("title")
                .value("brand")
                .build()
        def contentAttribute2 = ContentAttributeCommand.builder()
                .name("name")
                .value("brand_test")
                .build()
        def requestBody = ContentCreateCommand.builder()
                .contentAttributes(Arrays.asList(contentAttribute1, contentAttribute2))
                .build()

        when:
        def response = mvc.perform(post('/api/v1/admin/contents')
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(requestBody)))

        then:
        response.andExpect(status().isAccepted())
    }
}
