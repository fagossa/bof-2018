package fr.devoxx.task

import akka.http.javadsl.common.EntityStreamingSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import fr.devoxx.error.ErrorResponse

import scala.concurrent.ExecutionContext

class TaskRoutes(taskService: TaskService)(implicit ec: ExecutionContext) {

  implicit val jsonStreamingSupport = EntityStreamingSupport.json()

  lazy val routes = listRoute ~ streamRoute ~ getRoute ~ createRoute ~ deleteRoute

  private lazy val listRoute = path("tasks") {
    get {
      onSuccess(taskService.list) { tasks =>
        complete(tasks)
      }
    }
  }

  private lazy val streamRoute = path("tasks" / "stream") {
    get {
      complete(taskService.listStream)
    }
  }

  private lazy val getRoute = path("tasks" / Segment) { id =>
    get {
      onSuccess(taskService.get(id)) {
        case Some(task) => complete(task)
        case None => complete(StatusCodes.BadRequest, ErrorResponse(id))
      }
    }
  }

  private lazy val createRoute = path("tasks") {
    post {
      entity(as[CustomTask]) { task =>
        onSuccess(taskService.add(task)) {
          case Some(createdTask) => complete(createdTask)
          case None => complete(StatusCodes.BadRequest, ErrorResponse("Impossible to create TODO"))
        }
      }
    }
  }

  private lazy val deleteRoute = path("tasks" / Segment) { id =>
    delete {
      onSuccess(taskService.delete(id)) {
        case Some(deletedTask) => complete(deletedTask)
        case None => complete(StatusCodes.BadRequest, ErrorResponse(id))
      }
    }
  }
}
