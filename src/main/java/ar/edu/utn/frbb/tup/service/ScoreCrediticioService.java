package ar.edu.utn.frbb.tup.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class ScoreCrediticioService {

    private final Random random = new Random();

    public boolean verifyScore(long dni) {
        int randomInt = random.nextInt(100);
        // Si el número es par, devolver true; si es impar, devolver false
        return esPar(randomInt);
    }

    private boolean esPar(int numero) {
        return numero % 2 == 0;
    }
}