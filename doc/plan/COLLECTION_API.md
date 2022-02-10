# Collections API Lab Discussion
### Luke, Luka, Ricky, Vincent
### Team 5


## In your experience using these collections, are they hard or easy to use?

* Collections are fairly easy to use. They have standard names that tell their purpose and implement the methods you would expect them to have.

## In your experience using these collections, do you feel mistakes are easy to avoid?

* Generally easy to avoid - errors are thrown which tell you exactly what to fix. Additionally, method names and parameters are intuitive.

## What methods are common to all collections (except Maps)?

* add, remove, contains, stream, size, clear

## What methods are common to all Deques?

* addFirst, removeFirst, getFirst, addLast, removeLast, getLast, offerFirst, pollFirst, peekFirst, offerLast, pollLast, peekLast

## What is the purpose of each interface implemented by LinkedList?

* List: a LinkedList is a list - it can be accessed at random locations
* Queue/Deque: a LinkedList is a good queue/deque - elements at beginning and end are accessed easily
* Cloneable: the list can be copied
* Iterable: the list can be traversed over with an iterator
* Serializable: the list can be serialized into data

## How many different implementations are there for a Set?

* EnumSet - set with type enum
* HashSet - set with a hashtable for storing values
* LinkedHashSet - HashSet but with a LinkedList for value storage
* TreeSet - set with a tree structure for storing values
* Others - ConcurrentHashMap.KeySetView, ConcurrentSkipListSet, CopyOnWriteArraySet, JobStateReasons
  * Very specific implementations of set as given by the name

## What is the purpose of each superclass of PriorityQueue?
* AbstractQueue - Allows the use of AbstractQueue methods that all types of queues utilize (clear, element, remove, etc.)
* Serializable - the list can be serialized into data
* Iterable - the list can be traversed over with an iterator
* Collection - allows Collection (size, sort, etc.) methods to be used on PriorityQueue
* Queue - elements at beginning and end are accessed easily

## What is the purpose of the collection utility classes?

* The static methods in the Collections class can be used on all Collections regardless of implementation, which makes it easy for programmers to implement features agnostic of what data structures are used.
* Some methods exist in both Collections and the specific collection, such as List.replaceAll(). There doesn't seem to be any specifics on which to use in the documentation

## API Characterics applied to Collections API

* Easy to learn
  * Lots of subclasses with good and concrete names
  * Methods have clear names
* Provides for extension
  * Class hierarchy allows new implementations of Collections to be created
    * e.g. List has ArrayList and LinkedList, Set has HashSet and TreeSet
* Leads to readable code
  * Clear method names
* Hard to misuse
  * Method names are clear and well-documented
  * Errors that are thrown are clear to understand