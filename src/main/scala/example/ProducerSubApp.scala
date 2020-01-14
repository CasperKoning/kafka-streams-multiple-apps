package example

import org.apache.kafka.clients.producer.{KafkaProducer => JKafkaProducer, _}
import org.apache.kafka.common.serialization.StringSerializer
import scala.concurrent._
import scala.concurrent.duration._
import com.typesafe.scalalogging.LazyLogging

object ProducerSubApp {
  def apply(topic: String) = {
    val producerConfig = {
      val props = new java.util.Properties
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
      props
    }

    val producer = new KafkaProducer[String, String](producerConfig)
    
    java.util.concurrent.Executors
      .newScheduledThreadPool(3)
      .scheduleAtFixedRate(
        () => producer.send(topic, null, "hello"),
        0,
        1,
        java.util.concurrent.TimeUnit.SECONDS
      )
    
    producer
  }

  class KafkaProducer[K,V](props: java.util.Properties) extends LazyLogging {
    private val producer = new JKafkaProducer[String, String](props, new StringSerializer(), new StringSerializer())
    def send(topic: String, k: String, v: String): Unit = {
      val record = new ProducerRecord[String, String](topic, k, v)
      producer.send(record, (metadata, exception) => {
        Option(metadata).foreach(md => {
          logger.info(s"Produced offset ${md.offset} on ${md.topic}")
        })
        Option(exception).foreach(e => {
          logger.error("ERROR while producing", e)
        })
      })
    }

    def close() = producer.close()
  }
}
