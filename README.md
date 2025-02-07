### Purpose
Demonstrates how CQRS, Event sourcing can be implemented using Axon.

### Running locally
Start the server, application runs on default port 8080
To use swagger, http://localhost:8080/swagger-ui/index.html

### Domain
To keep it simple imagine we are managing an asset, there might be several properties but let's just
update the name for simplicity. 
* AssetId UUID
* Asset name String

### Flow
Created using PlantUML (see architecture.puml)
![img.png](architecture.png)

### Axon
Axon is responsible for handling CQRS and Event Sourcing, ensuring that commands and queries are separate. 
It also helps manage event-driven communication between different parts of the system.

#### Terminology

##### Aggregation
It represents a collection of related objects (like entities) that should be manipulated together. 
This is helpful because in DDD, consistency and business logic are usually encapsulated within an Aggregate.

##### Transactional boundary
It also defines a boundary for transactions and state changes, meaning that you typically apply commands to an 
Aggregate root, which then performs state changes or emits events.

#####  Event Store
A repository for events, which represent the changes that occurred in aggregates over time.

##### Aggregate in Axon
* Is the entry point for changes to an entity’s state.
* Maintain invariants and rules for business logic.
* Emit events when changes happen, and these events are then stored in an Event Store.


#### Axon flow (in our application)

| Step                    | Axon component                     |  Operation |
|-------------------------|------------------------------------| -----------|
| Command received        | CommandGateway                     | Routes command to the correct aggregate.|
| Command processing      | @CommandHandler in Asset           | Applies an event. |
| Event emission          | apply(new A..Event())              | Axon publishes the event. |
| Event handling in Query | @EventHandler in AssetEventHandler | Updates the read model. |
| Query processing        | Query API in QueryController       | Returns data from the read model.

### Advantages of Using CQRS, Event Sourcing, and Axon

##### Scalability
- CQRS: Read operations can be optimized separately from writes, allowing better query performance.
- Event Store: Since events are stored append-only, it enables efficient writes and horizontal scaling.

##### Strong Audit Trail & Compliance
- Event Sourcing: Maintains a full history of changes, making it easy to reconstruct past states.
- Axon: Provides automatic event replay and snapshots for better state recovery.

##### Improved Domain-Driven Design (DDD) implementation
- CQRS: Separates concerns between command (write) and query (read) models, making domain logic cleaner.
- Axon: Supports aggregates and sagas to manage business logic workflows.

##### Asynchronous processing & Event-Driven benefits
- Event Sourcing: Allows subscribers to react asynchronously to changes.
- Axon: Handles event dispatching, retries, and projections automatically.

#####  Easy integration with Microservices
- Event Store: Events can be consumed by other microservices without tight coupling.
- Axon: Provides built-in event distribution using Kafka, RabbitMQ, or Axon Server.s

### Disadvantages of Using CQRS, Event Sourcing, and Event Store with Axon

##### Increased complexity
- CQRS: Maintaining separate models for reads and writes adds development overhead.
- Event Sourcing: Requires additional logic to replay and transform events.
- Axon: Learning curve for handling aggregates, sagas, and distributed messaging.

##### Eventual consistency issues
- Since read and write models are separate, the system is not immediately consistent.
- Requires compensating mechanisms like snapshots and distributed transactions.

##### Storage overhead & Performance considerations
- Event Store: Growing event history increases storage requirements.
- Requires snapshots to improve event replay performance.

##### Debugging and querying complexity
- Debugging is harder because state is not stored as a snapshot but as an event sequence.
- Querying requires projections, which may need additional processing.

##### Potential vendor Lock-In
- Axon Server: While powerful, it may lock you into the Axon ecosystem.
- Alternative event stores (Kafka, PostgresSQL, etc.) might require custom integration.

### Trade-off with consistency
- In the context of CQRS, the write model (commands) and the read model (queries) are separated. This can lead to situations where the read model is slightly behind or inconsistent with the write model.
- For instance, after an event is written, the system might return an old version of the data until the read model is updated. This means the data is not immediately consistent between all services or components, which is a compromise on consistency.

#### How it impacts the system:
- Write Availability: The system remains available to handle write operations (e.g., creating or updating events), even if the read model or projections aren’t immediately updated.
- Read Availability: The system can serve queries even when they may not reflect the latest writes (since the read model may not be fully synchronized).
- Consistency: The system isn’t always in a consistent state at a given time. Instead, consistency is eventually achieved after the event store is processed and projections are updated.