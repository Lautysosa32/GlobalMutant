package ar.utn.lauti.meli.service;

import ar.utn.lauti.meli.dto.StatsResponse;
import ar.utn.lauti.meli.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final DnaRecordRepository repository;

    public StatsService(DnaRecordRepository repository) {
        this.repository = repository;
    }

    public StatsResponse getStats() {
        long mutants = repository.countMutants();
        long humans  = repository.countHumans();
        return new StatsResponse(mutants, humans);
    }
}

