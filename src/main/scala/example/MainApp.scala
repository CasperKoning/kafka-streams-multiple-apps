package example

import org.apache.kafka.streams._
import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.processor._

object MainApp extends App {

  val topic = "input"
  
  val producer = ProducerSubApp(topic = topic)

  val stream1 = StreamsSubApp(seed = "1337", topic = topic)
  val stream2 = StreamsSubApp(seed = "9001", topic = topic)

  Runtime.getRuntime().addShutdownHook(new Thread(() => producer.close()))
  Runtime.getRuntime().addShutdownHook(new Thread(() => stream1.close()))
  Runtime.getRuntime().addShutdownHook(new Thread(() => stream2.close()))
  
  stream1.start()
  stream2.start()
  
}
