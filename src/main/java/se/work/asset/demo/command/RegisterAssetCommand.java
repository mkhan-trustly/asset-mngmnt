package se.work.asset.demo.command;

import java.util.UUID;

public record RegisterAssetCommand(UUID assetId, String name) {
}