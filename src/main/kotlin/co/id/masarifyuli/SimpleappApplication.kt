package co.id.masarifyuli

import co.id.masarifyuli.entity.EmployeeRepository
import co.id.masarifyuli.security.entity.OauthClientDetails
import co.id.masarifyuli.security.entity.OauthClientDetailsRepository
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component


@SpringBootApplication
class SimpleappApplication

fun main(args: Array<String>) {
    val sApp = SpringApplication(SimpleappApplication::class.java)
    sApp.setDefaultProperties(Util.getPropertise())
    sApp.run(*args)
}

@Component
class AfterSpringStart(
    val oauthClientDetailsRepository: OauthClientDetailsRepository,
    val employeeRepository: EmployeeRepository
) {

    fun defaultClient() {
        oauthClientDetailsRepository.findById("client").orElse(
            oauthClientDetailsRepository.save(
                OauthClientDetails(
                    "masarifyuli-client",
                    "masarifyuli-psw",
                    "masarifyuli-resource",
                    "read,web",
                    "password,refresh_token,client_credentials",
                    "",
                    "USER,WEB",
                    900,
                    3600 * 24 * 7,
                    "{}",
                    null
                )
            )
        )

        employeeRepository.defaultUser()
    }

    @EventListener(ApplicationReadyEvent::class)
    fun afterAppReady() {
        defaultClient()
    }

}






