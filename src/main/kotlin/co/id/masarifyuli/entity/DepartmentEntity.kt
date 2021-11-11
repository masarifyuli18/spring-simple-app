package co.id.masarifyuli.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["code"], name = "_uniqueCode")
    ]
)
data class Department(
    @Column(nullable = false, length = 10)
    var code: String,
    @Column(nullable = false, length = 30)
    var name: String,
    @Column(nullable = false, length = 255)
    var description: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(
        mappedBy = "department",
        fetch = FetchType.EAGER,
        cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH],
        orphanRemoval = true
    )
    @JsonIgnore
    var departmentlist: MutableList<Employee> = mutableListOf()
}