With this example project, one can boot up
- A local Kafka stack (Zookeeper + Kafka)
- A local Producer that continuously produces messages on a predefined topic.
- A collection of local Kafka Streams applications that all consume from the input topic that is produced on by the local producer in the previous bullet.

The purpose of all this is to find out a way to run multiple Kafka Streams applications that use the same changelog naming patterns, but different consumer group IDs.

This is useful for the end goal of having 1 bigger Kafka Streams application with stateful operations, which can be split up into smaller separate applications, without having to recreate all changelog topics.

Running this project is done as follows:

- Run the local Kafka stack with `docker-compose up`.
- Run the local Producers and Kafka Streams app with `sbt run`.

If both Kafka Streams applications are running at the same time, one would expect to see output like
```
INFO  example.StreamsSubApp$ - Found (null, 1337 hello)
INFO  example.StreamsSubApp$ - Found (null, 9001 hello)
```
where the log messages from the different Streams apps are indicated by some defined seed, `1337` and `9001` for the different apps in this case. If you don't see both of these messages, things are not running as we want.
