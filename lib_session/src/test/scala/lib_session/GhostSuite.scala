package lib_session

import munit.FunSuite
import lib_session.Ghost
import lib_session.Storage

class GhostSuite extends FunSuite:
  test("It creates an empty Storage"):
    val obtained = Ghost.createStorage("users")
    val expected = Storage("users", Vector.empty)
    assertEquals(obtained, expected)
