package co.id.masarifyuli

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource(properties = [
    "server.http.port=0",
    "spring.datasource.url=jdbc:mysql://localhost:3306/simpleapp?createDatabaseIfNotExist=true&serverTimezone=Asia/Jakarta",
    "spring.datasource.username=root",
    "spring.datasource.password=root"
])
class SimpleappApplicationTests {

    @Test
    fun contextLoads() {
    }

}
