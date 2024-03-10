package org.example;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        File directory = new File("src/main/resources/" + args[0] + "/");
        File fin =  new File(directory,args[0] + ".in");
        File fout = new File(directory,args[0] + ".out");
        List<String> comanda;
        Secretariat secretariat = new Secretariat();
	    try (Scanner scanner = new Scanner(fin)) {
            while (scanner.hasNext()) {
                comanda = Arrays.asList(scanner.nextLine().split(" - "));
                switch (comanda.get(0)) {
                    case "adauga_student":
                        try {
                            secretariat.adauga_student(comanda.get(1), comanda.get(2));
                        } catch (ExceptieDuplicat e1) {
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fout, true))) {
                                bw.write("***\n");
                                bw.write(e1.getMessage() + "\n");
                            } catch (IOException e2) {
                                throw new RuntimeException(e2);
                            }
                        }
                        break;
                    case "adauga_curs":
                        secretariat.adauga_curs(comanda.get(1), comanda.get(2), Integer.parseInt(comanda.get(3)));
                        break;
                    case "contestatie":
                        secretariat.contestatie(comanda.get(1), Double.parseDouble(comanda.get(2)));
                        break;
                    case "citeste_mediile":
                        secretariat.citeste_mediile(directory);
                        break;
                    case "posteaza_mediile":
                        secretariat.posteaza_mediile(fout);
                        break;
                    case "adauga_preferinte":
                        secretariat.adauga_preferinte(comanda.get(1), comanda.subList(2, comanda.size()));
                        break;
                    case "repartizeaza":
                        secretariat.repartizeaza();
                        break;
                    case "posteaza_curs":
                        secretariat.posteaza_curs(comanda.get(1), fout);
                        break;
                    case "posteaza_student":
                        secretariat.posteaza_student(comanda.get(1), fout);
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
