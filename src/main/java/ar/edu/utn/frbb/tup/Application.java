package ar.edu.utn.frbb.tup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

//    {
//            "nombre": "Lucas",
//            "apellido": "Bertone",
//            "dni": 46339672,
//            "fechaNacimiento": "2005-03-02",
//            "tipoPersona": "F",
//            "banco": "Banco Galicia"
//    }

//    {
//        "dniTitular": 46339672,
//            "tipoCuenta": "C",
//            "moneda": "P"
//    }

//    {
//        "numeroCliente": 46339672,
//            "plazoMeses": 12,
//            "montoPrestamo": 50000,
//            "moneda": "P"
//    }
}
