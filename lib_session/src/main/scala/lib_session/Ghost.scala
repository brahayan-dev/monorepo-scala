package lib_session

case class Record[V](id: Int, data: Map[String, V])
case class Storage[V](name: String, records: Vector[Record[V]])

object Ghost:
  def createStorage[V](name: String): Storage[V] =
    Storage(name, Vector.empty)

  def clearStorage[V](storage: Storage[V]): Storage[V] =
    storage.copy(records = Vector.empty)

  def createRecord[V](data: Map[String, V]): Record[V] =
    Record(0, data)

  def addRecord[V](record: Record[V])(storage: Storage[V]): Storage[V] =
    val Storage(_, records) = storage
    val nextId = records.lastOption.map(_.id + 1).getOrElse(1)
    val indexedRecord = record.copy(id = nextId)
    storage.copy(records = records :+ indexedRecord)

  def getRecord[V](id: Int)(storage: Storage[V]): Option[Record[V]] =
    val Storage(_, records) = storage
    if records.isEmpty then None
    else binarySearch(records, id, 0, records.last.id).map(records)

  def removeRecord[V](id: Int)(storage: Storage[V]): Storage[V] =
    val Storage(_, records) = storage
    if records.isEmpty then storage
    else
      binarySearch(records, id, 0, records.last.id) match
        case None        => storage
        case Some(index) => storage.copy(records = records.patch(index, Nil, 1))

  private def binarySearch[V](
      records: Vector[Record[V]],
      id: Int,
      low: Int,
      high: Int
  ): Option[Int] =
    if low > high then None
    else
      val mid = (low + high) / 2
      val current = records(mid)
      current.id.compare(id) match
        case 0  => Some(mid)
        case 1  => binarySearch(records, id, low, mid - 1)
        case -1 => binarySearch(records, id, mid + 1, high)
