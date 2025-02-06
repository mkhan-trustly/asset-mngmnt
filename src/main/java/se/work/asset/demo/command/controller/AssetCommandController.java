package se.work.asset.demo.command.controller;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;
import se.work.asset.demo.command.RegisterAssetCommand;
import se.work.asset.demo.command.UpdateAssetCommand;
import se.work.asset.demo.command.UpdateAssetDto;
import se.work.asset.demo.command.exception.NoAssetFoundException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/command/asset")
@RequiredArgsConstructor
public class AssetCommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    @PostMapping
    public CompletableFuture<UUID> registerAsset(@RequestBody RegisterAssetCommand command) {
        return commandGateway.send(new RegisterAssetCommand(command.assetId(), command.name()));
    }

    @PutMapping("/{assetId}")
    public CompletableFuture<UUID> updateAsset(@PathVariable UUID assetId, @RequestBody UpdateAssetDto updateAsset) {
        long count = eventStore.readEvents(assetId.toString())
                .asStream()
                .count();
        if (count == 0) {
            throw new NoAssetFoundException("Didn't find any asset by id %s".formatted(assetId));
        }

        return commandGateway.send(new UpdateAssetCommand(assetId, updateAsset.name()));
    }

    @GetMapping("/{assetId}/events")
    public List<Object> getAllEvents(@PathVariable String assetId) {
        return eventStore
                .readEvents(assetId)
                .asStream()
                .map(DomainEventMessage::getPayload)
                .collect(Collectors.toList());
    }

}
