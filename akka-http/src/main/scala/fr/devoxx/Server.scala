package fr.devoxx

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import fr.devoxx.task.{DummyTaskRepository, TaskRoutes, TaskService}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Server extends App {

  implicit val system: ActorSystem = ActorSystem("DevoxxServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val log = Logging(system, "devoxx-server")

  import system.dispatcher

  val taskRepository = new DummyTaskRepository()
  val taskService = new TaskService(taskRepository)
  val taskRoutes = new TaskRoutes(taskService)

  Http().bindAndHandle(taskRoutes.routes, "localhost", 8080)

  log.info(s"Server online at http://localhost:8080/")

  Await.result(system.whenTerminated, Duration.Inf)
}
