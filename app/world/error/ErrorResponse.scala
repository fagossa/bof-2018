package world.error

import play.api.libs.json.{ Json, OFormat }

case class ErrorResponse(message: String)

object ErrorResponse {
  implicit val format: OFormat[ErrorResponse] = Json.format[ErrorResponse]
}
