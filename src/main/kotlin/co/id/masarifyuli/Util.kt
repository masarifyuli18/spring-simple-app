package co.id.masarifyuli

import org.ini4j.Wini
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import java.io.File
import java.util.*
import javax.persistence.AttributeConverter

object Util {

    fun getPropertise(): Properties {
        val ini = Wini(File("conf/general.ini"))
        val properties = Properties()
        val dbHost = ini.get("database", "host")
        val dbPort = ini.get("database", "port")
        val dbName = ini.get("database", "name")

        properties["spring.datasource.url"] =
            "jdbc:mysql://$dbHost:$dbPort/$dbName?createDatabaseIfNotExist=true&serverTimezone=Asia/Jakarta"
        properties["spring.datasource.username"] = ini.get("database", "username")
        properties["spring.datasource.password"] = ini.get("database", "password")
        return properties
    }

}

@NoRepositoryBean
interface UniversalRepository<T, ID> : PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T>

abstract class EnumSaveDataConverter<T : Enum<T>>(private val enumClass: Class<T>) : AttributeConverter<T?, String?> {
    override fun convertToDatabaseColumn(p0: T?): String? {
        if (p0 == null) {
            return null
        }
        return p0.ordinal.toString()
    }

    override fun convertToEntityAttribute(p0: String?): T? {
        if (p0 == null) {
            return null
        }
        return try {
            enumClass.enumConstants[p0.toInt()]
        } catch (e: Exception) {
            enumClass.enumConstants[enumClass.enumConstants.size - 1]
        }
    }
}