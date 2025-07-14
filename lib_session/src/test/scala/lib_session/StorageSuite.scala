package lib_session

import munit.Clue.generate
import munit.FunSuite
import lib_session.Storage
import lib_session.StorageRecord
import java.util.UUID

val name = "users"
val data = Map("user" -> "Bob")
val defaultId = UUID.randomUUID()
val record: StorageRecord[String] = StorageRecord(data)
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
      obtained.records.getOrElse(record.id, defaultId),
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
    val obtained = storage.records.get(defaultId)
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
    val obtained = storage.add(record).records.get(defaultId)
    assertEquals(obtained, None)

  test(
    "Storage.records.getOrElse should return the record when ID exists"
  ):
    val obtained =
      storage
        .add(record)
        .records
        .getOrElse(record.id, defaultId)
    assertEquals(obtained, record)

  test(
    "Storage.remove should return unchanged storage when removing from empty storage"
  ):
    val obtained = storage.remove(defaultId)
    assertEquals(obtained, storage)

  test(
    "Storage.remove should return unchanged storage when removing non-existing record ID"
  ):
    val obtained = storage.add(record).remove(defaultId)
    val expected = storage.add(record)
    assertEquals(obtained, expected)

  test("Storage.remove should successfully remove an existing record by ID"):
    val obtained = storage.add(record).remove(record.id)
    assertEquals(obtained, storage)
