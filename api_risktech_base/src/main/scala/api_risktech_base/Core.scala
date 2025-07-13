package api_risktech_base

import lib_session.Ghost

@main
def run() =
  val storage = Ghost.createStorage("users")
  println(storage)
