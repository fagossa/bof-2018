import play.api.libs.json.{ JsValue, Json, Writes }

package object world {

  implicit class AnyClassToJsonOps[A](a: A) {
    def toJson(implicit w: Writes[A]): JsValue = Json.toJson(a)
  }

}
