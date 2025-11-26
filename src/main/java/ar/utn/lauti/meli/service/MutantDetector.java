package ar.utn.lauti.meli.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {
    // ALGORITMO isMutant
    public boolean isMutant(String[] dna) {
        int size = dna.length;
        int sequencesFound = 0;

        // matriz
        char[][] m = new char[size][size];
        for (int i = 0; i < size; i++) m[i] = dna[i].toCharArray();

        // buscar secuencias horizontales, verticales y diagonales
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                char c = m[i][j];

                // vertical
                if (i + 3 < size &&
                        c == m[i+1][j] &&
                        c == m[i+2][j] &&
                        c == m[i+3][j]) {
                    if (++sequencesFound > 1) return true;
                }

                // horizontal
                if (j + 3 < size &&
                        c == m[i][j+1] &&
                        c == m[i][j+2] &&
                        c == m[i][j+3]) {
                    if (++sequencesFound > 1) return true;
                }

                // diagonal
                if (i + 3 < size && j + 3 < size &&
                        c == m[i+1][j+1] &&
                        c == m[i+2][j+2] &&
                        c == m[i+3][j+3]) {
                    if (++sequencesFound > 1) return true;
                }

                // diagonal inversa
                if (i + 3 < size && j - 3 >= 0 &&
                        c == m[i+1][j-1] &&
                        c == m[i+2][j-2] &&
                        c == m[i+3][j-3]) {
                    if (++sequencesFound > 1) return true;
                }
            }
        }
        return false;
    }
}
