package co.id.masarifyuli.entity

import co.id.masarifyuli.EnumSaveDataConverter
import co.id.masarifyuli.UniversalRepository
import co.id.masarifyuli.security.model.AuthUserDetails
import co.id.masarifyuli.security.model.AuthUserDetailsCompatible
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*
import javax.persistence.*
import javax.transaction.Transactional

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["username"], name = "_uniqueUsername")
    ]
)
data class Employee(
    @Column(nullable = false, length = 15)
    var username: String,
    @Column(nullable = false, length = 30)
    var name: String,
    @Column(nullable = false, length = 255)
    var password: String,
    @Column(nullable = false, length = 255)
    var address: String,
    @Column(columnDefinition = " CHAR(1) NOT NULL")
    @Convert(converter = GenderConverter::class)
    var gender: Gender
): AuthUserDetailsCompatible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(length = 20)
    var mobileNo: String? = null

    @Column(length = 30)
    var email: String? = null

    @ManyToOne(targetEntity = Department::class)
    @JoinColumn(
        referencedColumnName = "code", foreignKey = ForeignKey(name = "_foreignKeyDepartment"), nullable = true
    )
    var department: Department? = null

    enum class Gender { MALE, FEMALE, UNDEFINED }

    override fun toAuthUserDetails(roles: MutableList<String>): AuthUserDetails {
        return AuthUserDetails(username, password, roles, true)
    }

}

@Converter
class GenderConverter : EnumSaveDataConverter<Employee.Gender>(Employee.Gender::class.java)

interface EmployeeRepository : UniversalRepository<Employee, Long>, EmployeeRepositoryCustom {
    fun findByUsername(username: String): Optional<Employee>
}

interface EmployeeRepositoryCustom {
    fun defaultUser()
}

open class EmployeeRepositoryImpl : EmployeeRepositoryCustom {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Transactional
    override fun defaultUser() {
        val adm = employeeRepository.findById(1)
        if (!adm.isPresent) {
            entityManager.createNativeQuery(
                "INSERT INTO employee (`address`,`email`,`gender`,`mobile_no`,`name`,`password`,`username`,`department_code`) VALUES " +
                        "('Indonesia','hi.masarifyuli@gmail,com', '${Employee.Gender.MALE.ordinal}', '085156809248', 'Masarifyuli', " +
                        "'${passwordEncoder.encode("masarifyuli")}','masarifyuli', NULL)"
            ).executeUpdate()
        }
    }

}
