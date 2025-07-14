package lib_server

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import com.comcast.ip4s._

object Http extends IOApp.Simple:

  val helloService = HttpRoutes.of[IO]:
    case GET -> Root / "hello" / name => Ok(s"Hi, $name!")

  val httpApp = Router("/" -> helloService).orNotFound

  val run: IO[Unit] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"127.0.0.1")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .useForever
