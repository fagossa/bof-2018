package util

import play.api.libs.json.Reads
import play.api.mvc.Results.UnprocessableEntity
import play.api.mvc.{BodyParser, PlayBodyParsers, BodyParser => PlayBodyParser}

import scala.concurrent.ExecutionContext

object BodyParser {

  def json[A](
     implicit reads: Reads[A],
     executionContext: ExecutionContext,
   ): PlayBodyParser[A] =
    PlayBodyParser("json body parser") { request =>
      request
    }

  def json[A](
    implicit reads: Reads[A],
    executionContext: ExecutionContext,
    parsers: PlayBodyParsers
  ): PlayBodyParser[A] =
    PlayBodyParser("json body parser") { request =>
      parsers.json.apply(request).map {
        case Left(_) => Left(UnprocessableEntity("json expected"))
        case Right(jsValue) =>
          jsValue
            .validate(reads)
            .map { a =>
              Right(a)
            }
            .recoverTotal { jsError =>
              Left(UnprocessableEntity(jsError.toString))
            }
      }
    }
}
