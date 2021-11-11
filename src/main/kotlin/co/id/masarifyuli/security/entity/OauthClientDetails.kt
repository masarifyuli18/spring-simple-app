package co.id.masarifyuli.security.entity

import org.springframework.data.repository.CrudRepository
import java.io.Serializable
import javax.persistence.*

@Entity
class OauthClientDetails(
        @Id
        val clientId: String,
        @Column(nullable = false)
        var clientSecret: String,
        var resourceIds: String? = null,
        var scope: String? = null,
        var authorizedGrantTypes: String? = null,
        var webServerRedirectUri: String? = null,
        var authorities: String? = null,
        var accessTokenValidity: Int? = 900,
        var refreshTokenValidity: Int? = 3600,
        var additionalInformation: String? = null,
        var autoapprove: String? = null
) : Serializable

interface OauthClientDetailsRepository : CrudRepository<OauthClientDetails, String>