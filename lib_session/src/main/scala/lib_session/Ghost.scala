package lib_session

import java.util.UUID

case class GhostRecord[V](id: UUID, data: Map[String, V])
object GhostRecord:
  def apply[V](data: Map[String, V]): GhostRecord[V] =
    GhostRecord(UUID.randomUUID(), data)

case class GhostStorage[V](name: String, records: Map[UUID, GhostRecord[V]])
object GhostStorage:
  def apply[V](name: String): GhostStorage[V] =
    GhostStorage(name, Map.empty)

case class Ghost[V](storage: GhostStorage[V]):
  def clear(): Ghost[V] =
    Ghost(storage.copy(records = Map.empty))

  def add(record: GhostRecord[V]): Ghost[V] =
    val GhostStorage(_, records) = storage
    val updatedStorage = storage.copy(records = records + (record.id -> record))
    Ghost(updatedStorage)

  def remove(id: UUID): Ghost[V] =
    val GhostStorage(_, records) = storage
    if records.isEmpty then Ghost(storage)
    else Ghost(storage.copy(records = records - id))
