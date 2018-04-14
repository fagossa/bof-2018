import java.util.UUID

import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import world.tasks.CustomTask

class ApplicationSpec extends PlaySpec
  with BaseOneAppPerTest
  with MyApplicationFactory
  with ScalaFutures {

  val headers = FakeHeaders(Seq("Content-Type" -> "application/json"))
  val emptyJson = Json.obj()

  "Routes" should {

    "send 201 on a POST /task" in {
      val aTask = CustomTask(UUID.randomUUID().toString, "Do something")
      val response = route(app, FakeRequest(POST, "/task", headers, aTask.toJson))
      response.map(status(_)) mustBe Some(CREATED)
    }

    "send 200 on a GET /task/{id}" in {
      val anId = UUID.randomUUID().toString
      // Given a created task
      val aTask = CustomTask(anId, "Do something else")
      val taskCreated = route(app, FakeRequest(POST, "/task", headers, aTask.toJson)).get
      status(taskCreated) mustBe CREATED

      // When
      val taskQuery = route(app, FakeRequest(GET, s"/task/$anId", headers, emptyJson)).get

      // Then
      status(taskQuery) mustBe OK
      (contentAsJson(taskQuery) \ "value").as[String] mustBe aTask.value
    }

    "send 200 on a DELETE /task/{id}" in {
      val anId = UUID.randomUUID().toString
      // Given a created task
      val aTask = CustomTask(anId, "Do something something else")
      val taskCreated = route(app, FakeRequest(POST, "/task", headers, aTask.toJson)).get
      status(taskCreated) mustBe CREATED

      // That exists
      val taskGet = route(app, FakeRequest(GET, s"/task/$anId", headers, emptyJson)).get
      status(taskGet) mustBe OK

      // When deleted
      val taskDeleted = route(app, FakeRequest(DELETE, s"/task/$anId", headers, emptyJson)).get
      status(taskDeleted) mustBe OK

      // Then, it no longer exist
      val taskGetAfter = route(app, FakeRequest(GET, s"/task/$anId", headers, emptyJson)).get
      status(taskGetAfter) mustBe NOT_FOUND
    }

  }

}
