package io.microkt.kontainers.mongo

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import io.microkt.kontainers.junit5.annotation.Kontainers
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import java.net.InetSocketAddress

@Kontainers
@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
internal class MongoKontainerTest(private val mongo: MongoKontainer) {
    private val settings = MongoClientSettings.builder()
        .credential(
            MongoCredential.createCredential(
                MongoKontainer.Default.USERNAME,
                "admin",
                MongoKontainer.Default.PASSWORD.toCharArray()
            )
        )
        .applyToClusterSettings {
            it.hosts(listOf(ServerAddress(InetSocketAddress(mongo.getAddress(), mongo.getPort()!!))))
        }
        .build()
    private val client = KMongo.createClient(settings)
    private val database = client.getDatabase("test")
    private val collection = database.getCollection<Jedi>()

    @Test
    fun testQuery() {
        collection.insertOne(Jedi("Luke", 19))
        collection.insertOne(Jedi("Yoda", 900))

        val yoda: Jedi? = collection.findOne(Jedi::name eq "Yoda")
        assertNotNull(yoda)
    }

    @Test
    fun testValidPort() {
        assertNotNull(mongo.getPort(mongoKontainerSpec.ports.first().port))
    }
}
