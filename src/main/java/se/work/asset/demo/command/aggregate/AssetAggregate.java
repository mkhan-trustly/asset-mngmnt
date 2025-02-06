package se.work.asset.demo.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import se.work.asset.demo.command.RegisterAssetCommand;
import se.work.asset.demo.command.UpdateAssetCommand;
import se.work.asset.demo.event.AssetRegisteredEvent;
import se.work.asset.demo.event.AssetUpdatedEvent;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * The asset (Aggregate) ensures that everything inside it stays consistent.
 */
@Aggregate
public class AssetAggregate {

    @AggregateIdentifier
    private UUID assetId;
    private String name;

    protected AssetAggregate() {}

    @CommandHandler
    public AssetAggregate(RegisterAssetCommand cmd) {
        apply(new AssetRegisteredEvent(cmd.assetId(), cmd.name()));
    }

    @EventSourcingHandler
    protected void onEvent(AssetRegisteredEvent event) {
        this.assetId = event.assetId();
        this.name = event.name();
    }

    @CommandHandler
    public AssetAggregate(UpdateAssetCommand command) {
        apply(new AssetUpdatedEvent(command.assetId(), command.name()));
    }

    @EventSourcingHandler
    public void onEvent(AssetUpdatedEvent event) {
        this.name = event.name();
    }

}