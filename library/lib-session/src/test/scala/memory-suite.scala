package akeptous.lib.session.memory.suite

import munit.FunSuite
import akeptous.lib.session.memory.Storage
import akeptous.lib.session.memory.Record
import java.util.UUID

val name = "users"
val data = Map("user" -> "Bob")
val randomId = UUID.randomUUID()
val record: Record[String] = Record(data)
val storage: Storage[String] = Storage(name)

class StorageSuite extends FunSuite:
  test(
    "StorageRecord.apply should create a record with the provided data and a random UUID"
  ):
    assertEquals(record.data, data)
    assert(record.id.isInstanceOf[UUID])

  test(
    "Storage.add should add a record to the storage and preserve storage name"
  ):
    val obtained = storage.add(record)
    assertEquals(obtained.name, name)
    assertEquals(
      obtained.records.getOrElse(record.id, randomId),
      record
    )

  test("Storage.clear should remove all records from storage"):
    val obtained = storage.add(record).clear()
    assertEquals(obtained, storage)

  test("Storage.clear should return unchanged storage when already empty"):
    val obtained = storage.clear()
    assertEquals(obtained, storage)

  test(
    "Storage.add should handle adding the same record multiple times (idempotent)"
  ):
    val obtained = storage.add(record).add(record).add(record)
    val expected = storage.add(record)
    assertEquals(obtained, expected)

  test(
    "Storage.records.get should return None when record ID does not exist in empty storage"
  ):
    val obtained = storage.records.get(randomId)
    assertEquals(obtained, None)

  test(
    "Storage.records.get should return Some(record) when record ID exists"
  ):
    val obtained =
      storage.add(record).records.get(record.id)
    assertEquals(obtained, Some(record))

  test(
    "Storage.records.get should return None when record ID does not exist in populated storage"
  ):
    val obtained = storage.add(record).records.get(randomId)
    assertEquals(obtained, None)

  test(
    "Storage.records.getOrElse should return the record when ID exists"
  ):
    val obtained =
      storage
        .add(record)
        .records
        .getOrElse(record.id, randomId)
    assertEquals(obtained, record)

  test(
    "Storage.remove should return unchanged storage when removing from empty storage"
  ):
    val obtained = storage.remove(randomId)
    assertEquals(obtained, storage)

  test(
    "Storage.remove should return unchanged storage when removing non-existing record ID"
  ):
    val obtained = storage.add(record).remove(randomId)
    val expected = storage.add(record)
    assertEquals(obtained, expected)

  test("Storage.remove should successfully remove an existing record by ID"):
    val obtained = storage.add(record).remove(record.id)
    assertEquals(obtained, storage)
