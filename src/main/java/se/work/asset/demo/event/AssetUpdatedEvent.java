package se.work.asset.demo.event;

import java.util.UUID;

public record AssetUpdatedEvent(UUID assetId, String name) {
}