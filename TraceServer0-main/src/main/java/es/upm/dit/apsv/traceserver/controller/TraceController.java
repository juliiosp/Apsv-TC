package es.upm.dit.apsv.traceserver.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import es.upm.dit.apsv.traceserver.repository.TraceRepository;
import es.upm.dit.apsv.traceserver.model.Trace;

@RestController
public class TraceController {

    private final TraceRepository repository;

    public TraceController(TraceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/traces")
    public List<Trace> getAll() {
        return (List<Trace>) repository.findAll();
    }

    @PostMapping("/traces")
    public Trace createTrace(@RequestBody Trace newTrace) {
        return repository.save(newTrace);
    }

    @GetMapping("/traces/{id}")
    public Trace getTrace(@PathVariable String id) {
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/traces/{id}")
    public Trace updateTrace(@RequestBody Trace newTrace, @PathVariable String id) {
        return repository.findById(id).map(trace -> {
            trace.setTruck(newTrace.getTruck());
            trace.setLastSeen(newTrace.getLastSeen());
            trace.setLat(newTrace.getLat());
            trace.setLng(newTrace.getLng());
            return repository.save(trace);
        }).orElseGet(() -> {
            newTrace.setTraceId(id);
            return repository.save(newTrace);
        });
    }

    @DeleteMapping("/traces/{id}")
    public void deleteTrace(@PathVariable String id) {
        repository.deleteById(id);
    }
}