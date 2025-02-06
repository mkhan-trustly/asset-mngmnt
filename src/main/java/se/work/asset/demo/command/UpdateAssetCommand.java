package se.work.asset.demo.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record UpdateAssetCommand(@TargetAggregateIdentifier UUID assetId, String name) {
}
