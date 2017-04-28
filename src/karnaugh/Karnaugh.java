/*
 * Realiza un metodo en Java qe reciba un array de booleans con los
 * valores que tiene que devolver una funcion. El metodo debe
 * imprimir por terminal la simplificacion dela duncion usando
 * mapas de Karnaugh
 *
 * Haz un metodo que reciba una funcion de un circuito combinacional
 * y que imprima por terminal el mapa de Karnaugh
 */
package karnaugh;

/**
 *
 * @author alber
 */
public class Karnaugh {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean prueba0[] = {false, true, false, true};
        boolean prueba1[] = {false, true, false, true, false, true, true, true};
        boolean prueba2[] = {false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true};
        boolean prueba3[] = {false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true};
        boolean prueba4[] = {false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true,
            false, true, false, true, false, true, true, true};
        
        
        //new Karnaugh().ejercicioFacil(prueba0);
        new Karnaugh().ejercicioFacil(prueba1);
        //new Karnaugh().ejercicioFacil(prueba2);
       // new Karnaugh().ejercicioFacil(prueba3);
       // new Karnaugh().ejercicioFacil(prueba4);
    }

    private boolean ejercicioFacil(boolean[] array) {
        int variables = hallarNumeroVariables(array);

        // Para saber cuantas variables van en X y cuantas en Y
        int yLong = 0;
        int xLong = 0;
        int aux = variables;
        boolean iterator = true;
        while (aux != 0) {
            aux--;
            if (iterator) {
                xLong++;
                iterator = !iterator;
            } else {
                yLong++;
                iterator = !iterator;
            }
        }

        // Este metodo esta mal en las lineas que se cambian
        boolean mapa[][] = new boolean[(int) Math.pow(2, yLong)][(int) Math.pow(2, xLong)];
        int count = 0;

        for (int x = 0; x < Math.pow(2, xLong); x++) {
            if (x == 2) {
                count += yLong;
            }
            if (x == 3) {
                count -= yLong * 2;
            }
            for (int y = 0; y < Math.pow(2, yLong); y++) {
                if (y == 2) {
                    count++;
                }
                if (y == 3) {
                    count -= 2;
                }
                mapa[y][x] = array[count];
                count++;
            }
        }

        imprimirMapa(mapa);
        return true;
    }

    public void imprimirMapa(boolean[][] mapa) {
        imprimirCabeceraKarnaugh(mapa);

        for (int y = 0; y < mapa.length; y++) {
            imprimirInicioLinea(y);
            
            for (int x = 0; x < mapa[y].length; x++) {
                System.out.print("  " + toNumeralString(mapa[y][x]));
                if (x != mapa[y].length - 1) {
                    System.out.print(" |");
                } else {
                    System.out.print(" ║");
                }
            }
        }
        imprimirPieKarnaugh(mapa);
    }
    
    private void imprimirInicioLinea(int y){
        String aux;
        if (y != 2 && y != 6 && y != 3 && y != 7) {aux = Integer.toBinaryString(y);
        } else{
            if (y == 2) aux = "11";
            else if (y == 3) aux = "10";
            else if (y == 7) aux = "110";
            else aux = "111";
        }
        
        if (y < 2) System.out.print("\n║  " + aux +" |");
        else if (y < 4) System.out.print("\n║ " + aux +" |");
        else System.out.print("\n║" + aux +" |");
    }

    private void imprimirCabeceraKarnaugh(boolean[][] mapa) {
        if (mapa.length == 2 && mapa[0].length == 2) {
            System.out.println("╔════════╗");
            System.out.print("║    |  0 |  1 ║");
        } else if (mapa.length == 2 && mapa[0].length == 4 || mapa.length == 4 && mapa[0].length == 4) {
            System.out.println("╔══════════════╗");
            System.out.print("║    | 00 | 01 | 11 | 10 ║");
        } else if (mapa.length == 4 && mapa[0].length == 8 || mapa.length == 8 && mapa[0].length == 8) {
            System.out.println("╔══════════════════════════╗");
            System.out.print("║    | 000| 001| 011| 010| 100| 101| 111| 110║");
        }
    }

    private void imprimirPieKarnaugh(boolean[][] mapa) {
        if (mapa.length == 2 && mapa[0].length == 2) {
            System.out.println("\n╚════════╝");
        } else if (mapa.length == 2 && mapa[0].length == 4 || mapa.length == 4 && mapa[0].length == 4) {
            System.out.println("\n╚══════════════╝");
        } else if (mapa.length == 4 && mapa[0].length == 8 || mapa.length == 8 && mapa[0].length == 8) {
            System.out.println("\n╚══════════════════════════╝");
        };
    }

    // Calcula el numero de variables que tendria un array de resultados
    // de una tabla de operaciones combinacionales
    // Ejemplo: array de longitud 8 -> 3 variables
    public int hallarNumeroVariables(boolean array[]) {
        if (array.length == 0) {
            return 0;
        }
        String lengthBinario = Integer.toBinaryString(array.length);

        for (int x = 0;; x++) {
            if (lengthBinario.charAt(lengthBinario.length() - x - 1) == '1') {
                return x;
            }
        }
    }

    // Metodo sacado de StackOverflow (Joachim Sauer)
    public static String toNumeralString(final Boolean input) {
        if (input == null) {
            return "null";
        } else {
            return input.booleanValue() ? "1" : "0";
        }
    }
}
