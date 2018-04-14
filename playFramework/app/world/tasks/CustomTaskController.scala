package world.tasks

import play.api.libs.json.Json
import play.api.mvc._
import world.error.ErrorResponse

import scala.concurrent.ExecutionContext

class CustomTaskController(taskService: CustomTaskService, cc: ControllerComponents)(
  implicit ec: ExecutionContext
) extends AbstractController(cc) {

  implicit val defaultBodyParsers = cc.parsers

  import world.tasks.CustomTask._

  def list = Action.async {
    taskService.list.map { elements =>
      Ok(Json.toJson(elements))
    }
  }

  def listStream = Action { implicit request =>
    Ok.chunked(taskService.listStream.map {
        Json.toJson(_)
      })
      .withHeaders(CONTENT_TYPE -> "application/json")
  }

  def get(id: String) = Action.async {
    taskService.get(id).map {
      case Some(task) => Ok(task.toJson)
      case None       => NotFound(ErrorResponse(id).toJson)
    }
  }

  def add = Action.async(util.BodyParser.json[CustomTask]) { implicit request =>
    taskService.add(request.body).map {
      case Some(task) => Created(task.toJson)
      case None       => BadRequest(ErrorResponse("Impossible to create TODO").toJson)
    }
  }

  def delete(id: String) = Action.async {
    taskService.delete(id).map {
      case Some(task) => Ok(task.toJson)
      case None       => NotFound(ErrorResponse(id).toJson)
    }
  }

}
