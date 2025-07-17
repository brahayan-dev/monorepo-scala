package akeptous.lib.server

import zio._
import zio.http._

object Http extends ZIOAppDefault:
  val routes =
    Routes(
      Method.GET / Root -> handler(Response.text("Greetings at your service")),
      Method.GET / "greet" -> handler { (req: Request) =>
        val name = req.queryOrElse[String]("name", "World")
        Response.text(s"Hello $name!")
      }
    )

  def run = Server.serve(routes).provide(Server.defaultWithPort(1234))
