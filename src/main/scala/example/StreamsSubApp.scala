package example

import org.apache.kafka.streams._
import org.apache.kafka.clients.consumer._
import com.typesafe.scalalogging._
import org.apache.kafka.streams.scala.Serdes
import org.apache.kafka.streams.scala.kstream.Consumed

object StreamsSubApp extends LazyLogging {
  def apply(seed: String, applicationId: String, groupId: String, topic: String) = {
    val builder = new StreamsBuilder()
    val props = {
      val p = new java.util.Properties
      p.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId)
      p.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
      p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
      p.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "2")
      p.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "100")
      p.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
      p
    }

    builder
      .stream[String, String](topic, Consumed.`with`(Serdes.String, Serdes.String))
      .mapValues(value => s"$seed $value")
      .foreach((key, value) => logger.info(s"Found (${key}, ${value})"))
    
    val topology = builder.build()

    new KafkaStreams(topology, props)
  }
}
