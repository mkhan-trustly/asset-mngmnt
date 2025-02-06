package se.work.asset.demo.audit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/audit")
class AuditController {

    @GetMapping("/{assetId}")
    public List<String> getAuditTrail(@PathVariable UUID assetId) {
        return AuditTrail.getAuditLogs(assetId);
    }
}

