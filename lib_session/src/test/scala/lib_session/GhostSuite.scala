package lib_session

import munit.FunSuite
import lib_session.Ghost
import lib_session.Storage
import java.util.UUID

class GhostSuite extends FunSuite:
  test("createStorage creates an empty storage"):
    val obtained = Ghost.createStorage("users")
    val expected = Storage("users", Map.empty)
    assertEquals(obtained, expected)

  // test("clearStorage removes all records"):
  //   val id1 = UUID.randomUUID()
  //   val id2 = UUID.randomUUID()
  //   val record1 = Record(id1, Map("name" -> "Alice"))
  //   val record2 = Record(id2, Map("name" -> "Bob"))
  //   val storage = Storage("users", Map(id1 -> record1, id2 -> record2))
  //   val obtained = Ghost.clearStorage(storage)
  //   val expected = Storage("users", Map.empty)
  //   assertEquals(obtained, expected)

  test("clearStorage on empty storage returns empty storage"):
    val storage = Storage[String]("users", Map.empty)
    val obtained = Ghost.clearStorage(storage)
    assertEquals(obtained, storage)

  test("createRecord creates record with random UUID"):
    val data = Map("name" -> "Alice", "age" -> "30")
    val obtained = Ghost.createRecord(data)
    assertEquals(obtained.data, data)
    assert(obtained.id.isInstanceOf[UUID])

  test("addRecord to empty storage adds the record"):
    val storage = Storage[String]("users", Map.empty)
    val record = Record(UUID.randomUUID(), Map("name" -> "Alice"))
    val obtained = Ghost.addRecord(record)(storage)
    val expected = Storage("users", Map(record.id -> record))
    assertEquals(obtained, expected)

  test("addRecord to non-empty storage adds the record"):
    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()
    val record1 = Record(id1, Map("name" -> "Alice"))
    val record2 = Record(id2, Map("name" -> "Bob"))
    val storage = Storage("users", Map(id1 -> record1, id2 -> record2))
    val newRecord = Record(UUID.randomUUID(), Map("name" -> "Charlie"))
    val obtained = Ghost.addRecord(newRecord)(storage)
    val expected = Storage(
      "users",
      Map(id1 -> record1, id2 -> record2, newRecord.id -> newRecord)
    )
    assertEquals(obtained, expected)

  test("getRecord from empty storage returns None"):
    val storage = Storage[String]("users", Map.empty)
    val obtained = Ghost.getRecord(UUID.randomUUID())(storage)
    assertEquals(obtained, None)

  test("getRecord with existing id returns Some(record)"):
    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()
    val record1 = Record(id1, Map("name" -> "Alice"))
    val record2 = Record(id2, Map("name" -> "Bob"))
    val storage = Storage("users", Map(id1 -> record1, id2 -> record2))
    val obtained = Ghost.getRecord(id1)(storage)
    assertEquals(obtained, Some(record1))

  test("getRecord with non-existing id returns None"):
    val id1 = UUID.randomUUID()
    val record1 = Record(id1, Map("name" -> "Alice"))
    val storage = Storage("users", Map(id1 -> record1))
    val obtained = Ghost.getRecord(UUID.randomUUID())(storage)
    assertEquals(obtained, None)

  test("getRecord finds record in storage"):
    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()
    val id3 = UUID.randomUUID()
    val record1 = Record(id1, Map("name" -> "Alice"))
    val record2 = Record(id2, Map("name" -> "Bob"))
    val record3 = Record(id3, Map("name" -> "Charlie"))
    val storage =
      Storage("users", Map(id1 -> record1, id2 -> record2, id3 -> record3))
    val obtained = Ghost.getRecord(id2)(storage)
    assertEquals(obtained, Some(record2))

  test("removeRecord from empty storage returns unchanged storage"):
    val storage = Storage[String]("users", Map.empty)
    val obtained = Ghost.removeRecord(UUID.randomUUID())(storage)
    assertEquals(obtained, storage)

  test("removeRecord with non-existing id returns unchanged storage"):
    val id1 = UUID.randomUUID()
    val record1 = Record(id1, Map("name" -> "Alice"))
    val storage = Storage("users", Map(id1 -> record1))
    val obtained = Ghost.removeRecord(UUID.randomUUID())(storage)
    assertEquals(obtained, storage)

  test("removeRecord with existing id removes the record"):
    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()
    val record1 = Record(id1, Map("name" -> "Alice"))
    val record2 = Record(id2, Map("name" -> "Bob"))
    val storage = Storage("users", Map(id1 -> record1, id2 -> record2))
    val obtained = Ghost.removeRecord(id1)(storage)
    val expected = Storage("users", Map(id2 -> record2))
    assertEquals(obtained, expected)

  // test("removeRecord from single record storage results in empty storage"):
  //   val id1 = UUID.randomUUID()
  //   val record1 = Record(id1, Map("name" -> "Alice"))
  //   val storage = Storage("users", Map(id1 -> record1))
  //   val obtained = Ghost.removeRecord(id1)(storage)
  //   val expected = Storage("users", Map.empty)
  //   assertEquals(obtained, expected)

  test("removeRecord from storage with multiple records"):
    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()
    val id3 = UUID.randomUUID()
    val record1 = Record(id1, Map("name" -> "Alice"))
    val record2 = Record(id2, Map("name" -> "Bob"))
    val record3 = Record(id3, Map("name" -> "Charlie"))
    val storage =
      Storage("users", Map(id1 -> record1, id2 -> record2, id3 -> record3))
    val obtained = Ghost.removeRecord(id2)(storage)
    val expected = Storage("users", Map(id1 -> record1, id3 -> record3))
    assertEquals(obtained, expected)
