package se.work.asset.demo.audit;

import org.axonframework.eventhandling.EventHandler;
import se.work.asset.demo.event.AssetRegisteredEvent;

class AssetEventHandler {

    @EventHandler
    public void onEvent(AssetRegisteredEvent event) {
        AuditTrail.log(event.assetId(), "Asset (%s - %s) registered.".formatted(event.assetId(), event.name()));
    }
}
