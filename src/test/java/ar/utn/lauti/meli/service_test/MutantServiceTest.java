package ar.utn.lauti.meli.service_test;
import ar.utn.lauti.meli.entity.DnaRecord;
import ar.utn.lauti.meli.repository.DnaRecordRepository;
import ar.utn.lauti.meli.service.MutantDetector;
import ar.utn.lauti.meli.service.MutantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MutantServiceTest {

    private DnaRecordRepository repository;
    private MutantDetector detector;
    private MutantService service;

    @BeforeEach
    void setup() {
        repository = mock(DnaRecordRepository.class);
        detector = mock(MutantDetector.class);
        service = new MutantService(repository, detector);
    }

    @Test
    void testMutantDetectionAndSave() throws Exception {
        String[] dna = {"AAAA", "TTTT", "CCCC", "GGGG"};

        when(detector.isMutant(dna)).thenReturn(true);
        when(repository.findByHash(Mockito.anyString()))
                .thenReturn(Optional.empty());

        boolean result = service.processDna(dna);

        assertTrue(result);
        verify(repository, times(1)).save(Mockito.any(DnaRecord.class));
    }

    @Test
    void testNonMutantDetectionSave() throws Exception {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};

        when(detector.isMutant(dna)).thenReturn(false);
        when(repository.findByHash(Mockito.anyString()))
                .thenReturn(Optional.empty());

        boolean result = service.processDna(dna);

        assertFalse(result);
        verify(repository, times(1)).save(Mockito.any(DnaRecord.class));
    }

    @Test
    void testExistingRecordReturnsStoredValue() throws Exception {
        String[] dna = {"AAAA", "AAAA", "AAAA", "AAAA"};

        DnaRecord stored = DnaRecord.builder()
                .isMutant(true)
                .hash("abc")
                .dna("AAAA,AAAA,AAAA,AAAA")
                .build();

        when(repository.findByHash(Mockito.anyString()))
                .thenReturn(Optional.of(stored));

        boolean result = service.processDna(dna);

        assertTrue(result);
        verify(repository, never()).save(Mockito.any());
    }

    @Test
    void testHashGenerationDoesNotThrow() throws Exception {
        String[] dna = {"AAAA"};
        when(repository.findByHash(Mockito.anyString()))
                .thenReturn(Optional.empty());

        when(detector.isMutant(dna)).thenReturn(true);

        assertDoesNotThrow(() -> service.processDna(dna));
    }

    @Test
    void testProcessDnaNullThrows() {
        assertThrows(Exception.class, () -> service.processDna(null));
    }
}
