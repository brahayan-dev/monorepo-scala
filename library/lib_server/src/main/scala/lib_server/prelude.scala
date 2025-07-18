package akeptous.lib.server

object http:
  export zio.{
    ZIO,
    URIO,
    Task,
    UIO,
    ZLayer,
    ZEnvironment as Environment,
    ExitCode,
    ZIOAppDefault as Api,
    Console,
    Scope,
    Clock,
    Random,
    durationInt
  }

  export zio.http.{
    Routes,
    Root,
    Handler,
    Server,
    Method,
    Request,
    Response,
    Status,
    handler
  }
