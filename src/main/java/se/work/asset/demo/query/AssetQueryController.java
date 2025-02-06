package se.work.asset.demo.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.work.asset.demo.event.AssetRegisteredEvent;
import se.work.asset.demo.event.AssetUpdatedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/query/asset")
public class AssetQueryController {

    private final Map<UUID, AssetProjection> repository = new HashMap<>();

    @GetMapping("/{assetId}")
    public AssetProjection getProperty(@PathVariable UUID assetId) {
        return repository.get(assetId);
    }

    @EventHandler
    public void onEvent(AssetRegisteredEvent event) {
        repository.put(event.assetId(), new AssetProjection(event.assetId(), event.name()));
    }

    @EventHandler
    public void onEvent(AssetUpdatedEvent event) {
        repository.put(event.assetId(), new AssetProjection(event.assetId(), event.name()));
    }
}
