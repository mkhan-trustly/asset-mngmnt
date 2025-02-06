package se.work.asset.demo.audit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AuditTrail {
    private static final Map<UUID, List<String>> auditLogs = new ConcurrentHashMap<>();

    public static void log(UUID propertyId, String message) {
        auditLogs.computeIfAbsent(propertyId, id -> new CopyOnWriteArrayList<>()).add(message);
    }

    public static List<String> getAuditLogs(UUID propertyId) {
        return auditLogs.getOrDefault(propertyId, List.of());
    }
}