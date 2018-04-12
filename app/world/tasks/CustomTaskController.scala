package world.tasks

import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

class CustomTaskController(taskService: CustomTaskService, cc: ControllerComponents)(
  implicit ec: ExecutionContext
) extends AbstractController(cc) {

  import world.tasks.CustomTask._

  def list = Action.async {
    taskService.list.map { elements =>
      Ok(Json.toJson(elements))
    }
  }

  def get(id: String) = Action.async {
    taskService.get(id).map {
      case Some(task) => Ok(task.toJson)
      case None       => NotFound(id)
    }
  }

  // TODO: add a body parser
  def add = Action.async {
    taskService.add().map {
      case Some(task) => Created(task.toJson)
      case None       => BadRequest("Impossible to create TODO")
    }
  }

  def delete(id: String) = Action.async {
    taskService.delete(id).map {
      case Some(task) => Ok(task.toJson)
      case None       => NotFound(id)
    }
  }

}
