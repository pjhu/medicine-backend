package com.pjhu.medicine.identity.domain.model.external

import com.pjhu.medicine.identity.domain.model.Role
import com.pjhu.medicine.identity.domain.model.UserStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@EnableAutoConfiguration
@ContextConfiguration(classes = ExternalUserRepository)
@DataJpaTest
class ExternalUserRepositoryTest extends Specification {

    @Autowired
    private ExternalUserRepository repository

    void setup() {
        def user = ExternalUser.builder()
                .username("user1")
                .role(Role.ADMIN)
                .active(UserStatus.ENABLE)
                .build()
        repository.save(user)
    }

    def "external_user_entity_exist"() {
        expect:
        def userList = repository.findAll()
        userList.size() == 1
    }

    def "findByUsernameAndActiveNot_available"() {
        given:
        def username = "user1"

        when:
        def externalUser = repository.findByUsernameAndActiveNot(username, UserStatus.DISABLE)

        then:
        externalUser.username == username
    }
}
