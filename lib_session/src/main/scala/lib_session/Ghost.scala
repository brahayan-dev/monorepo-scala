package lib_session

import java.util.UUID

case class Record[V](id: UUID, data: Map[String, V])
case class Storage[V](name: String, records: Map[UUID, Record[V]])

object Ghost:
  def createStorage[V](name: String): Storage[V] =
    Storage(name, Map.empty)

  def clearStorage[V](storage: Storage[V]): Storage[V] =
    storage.copy(records = Map.empty)

  def createRecord[V](data: Map[String, V]): Record[V] =
    Record(UUID.randomUUID(), data)

  def addRecord[V](record: Record[V])(storage: Storage[V]): Storage[V] =
    val Storage(_, records) = storage
    storage.copy(records = records + (record.id -> record))

  def getRecord[V](id: UUID)(storage: Storage[V]): Option[Record[V]] =
    val Storage(_, records) = storage
    if records.isEmpty then None else records.get(id)

  def removeRecord[V](id: UUID)(storage: Storage[V]): Storage[V] =
    val Storage(_, records) = storage
    if records.isEmpty then storage
    else storage.copy(records = records - id)
