package fr.devoxx.task

import play.api.libs.json.{Format, Json}

case class CustomTask(id: String, value: String)

object CustomTask {
  implicit val format: Format[CustomTask] = Json.format[CustomTask]
}
