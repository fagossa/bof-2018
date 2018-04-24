

# playFramework

This project is an example intended to show some basic functionality of playFramework.

Some topics include:

## Compile-time dependency injection

```scala
class CustomTaskController(taskService: CustomTaskService, cc: ControllerComponents)(
  implicit ec: ExecutionContext
) extends AbstractController(cc) {
   ...
}

```

## Actions

```scala
def list = Action.async {
    taskService.list.map { elements =>
      Ok(Json.toJson(elements))
    }
}
```

## Bodyparsers

```scala
def add = Action.async(util.BodyParser.json[CustomTask]) { implicit request =>
    taskService.add(request.body).map {
      case Some(task) => Created(task.toJson)
      case None       => BadRequest(ErrorResponse("Impossible to create TODO").toJson)
    }
}
```

## play-json

```scala
case class CustomTask(id: String, value: String)
implicit val format: OFormat[CustomTask] = Json.format[CustomTask]
```

## Integration test

```scala
"send 201 on a POST /task" in {
    val aTask = CustomTask(UUID.randomUUID().toString, "Do something")
    val response = route(app, FakeRequest(POST, "/task", headers, aTask.toJson))
    response.map(status(_)) mustBe Some(CREATED)
}
```
