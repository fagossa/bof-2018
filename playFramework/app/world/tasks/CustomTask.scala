package world.tasks

import play.api.libs.json.{ Json, OFormat }

case class CustomTask(id: String, value: String)

object CustomTask {
  implicit val format: OFormat[CustomTask] = Json.format[CustomTask]
}
