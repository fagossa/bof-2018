package world.tasks

import play.api.libs.json.{ Json, OFormat }

case class CustomTask(value: String) extends AnyVal

object CustomTask {
  implicit val format: OFormat[CustomTask] = Json.format[CustomTask]
}
