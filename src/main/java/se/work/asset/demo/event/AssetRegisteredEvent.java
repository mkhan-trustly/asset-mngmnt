package se.work.asset.demo.event;

import java.util.UUID;

public record AssetRegisteredEvent(UUID assetId, String name) {
}