package ar.edu.utn.frbb.tup.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class ScoreCrediticioService {
    private final Random random = new Random();

    public boolean verifyScore(long dni) {
        // Genera un número aleatorio entre 0 y 1
        double randomValue = random.nextDouble();

        // Devuelve false aproximadamente 1/3 de las veces
        return randomValue >= 1.0 / 3.0;
    }
}