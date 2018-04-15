package fr.devoxx.error

import play.api.libs.json.{Format, Json}

case class ErrorResponse(message: String)

object ErrorResponse {
  implicit val format: Format[ErrorResponse] = Json.format[ErrorResponse]
}
