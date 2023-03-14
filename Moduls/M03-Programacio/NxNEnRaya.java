package nxnenraya;

import java.util.Scanner;

/**
 *
 * @author Marc
 */
public class NxNEnRaya {

    public static Scanner entrada = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        bienvenida();

        do {
            System.out.print(BLUE + "Introduce el numero de filas y columnas (N) del tablero: ");
            int n = entrada.nextInt();
            System.out.println("");

            int[][] tablero = new int[n][n];
            int[] valores = new int[(int) Math.pow(n, 2)];
            int[][] coordenadas = llenaCoordenadas(n);
            final int jugador1 = 1;
            final int jugador2 = 2;
            int turno = jugador1;
            int posicion;

            muestraCoordenadas(coordenadas);
            System.out.println(BLUE + "Turno del " + color(turno) + "jugador " + turno + RESET);

            do {
                System.out.print(BLUE + "Escoje una posicion del 1 al " + (int) Math.pow(n, 2) + ": ");
                String p = entrada.next();
                System.out.println("");

                if (!esNumero(p)) {
                    limpiaPantalla();
                    System.out.println(BLUE + "Posición no válida! Introduzca una nueva posición.\n");
                } else {
                    posicion = Integer.parseInt(p);

                    if (posicion < 1 || posicion > ((int) Math.pow(n, 2))) {
                        limpiaPantalla();
                        System.out.println(BLUE + "Posición fuera de rango! Introduzca una nueva posición.\n");
                    } else if (valores[posicion - 1] != 0) {
                        limpiaPantalla();
                        System.out.println(BLUE + "Posición ya ocupada! Introduzca una nueva posición.\n");
                    } else if (valores[posicion - 1] == 0) {
                        valores[posicion - 1] = turno;
                        tablero = llenaTablero(valores, n);

                        if (comprobarVictoria(tablero, n)) {
                            limpiaPantalla();
                            System.out.println(BLUE + "Victoria del " + color(turno) + "jugador " + turno + RESET + "!\n");
                            muestraTablero(tablero, n);
                            break;
                        } else {
                            limpiaPantalla();
                            turno = cambiaTurno(turno, jugador1, jugador2);
                        }
                    }
                }
                System.out.println(BLUE + "Turno del " + color(turno) + "jugador " + turno + RESET + "\n");
                muestraCoordenadas(coordenadas);
                muestraTablero(tablero, n);

            } while (!comprobarEmpate(valores));

            if (comprobarEmpate(valores) && !comprobarVictoria(tablero, n)) {
                limpiaPantalla();
                System.out.println(BLUE + "Partida finalizada. El resultado es empate.\n");
                muestraTablero(tablero, n);
            }

        } while (repetirPartida());
    }

    public static void bienvenida() {
        System.out.println(PURPLE + "Bienvenidos al NxN en Raya!\n\n"
                + "Instrucciones:\n\n"
                + "Para jugar al NxN en raya necesitarás un tablero de NxN y ser dos jugadores.\n"
                + "El juego trata de ir marcando los espacios del tablero alternadamente hasta que\n"
                + "uno de los jugadores consiga hacer NxN. La línea puede ser horizontal, diagonal o vertical." + RESET + "\n");
    }

    public static boolean esNumero(String s) {
        boolean numero = false;

        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                numero = true;
            } else {
                numero = false;
            }
        }
        return numero;
    }

    public static boolean repetirPartida() {
        char repetir;

        do {
            System.out.print(BLUE + "Volver a jugar ((S)í/(N)o): ");
            repetir = Character.toLowerCase(entrada.next().charAt(0));
            System.out.println("");

            switch (repetir) {
                case 'n':
                    System.out.println(BLUE + "Hasta pronto!");
                    return false;
                case 's':
                    return true;
                default:
                    System.out.println(BLUE + "Respuesta no valida.\n");
            }
        } while (repetir != 'n' || repetir != 's');
        return false;
    }

    public static int[][] llenaCoordenadas(int n) {
        int[][] tablero = new int[n][n];
        int m = 1;

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                tablero[i][j] = m;
                m++;
            }
        }
        return tablero;
    }

    public static void muestraCoordenadas(int entrada[][]) {

        for (int i = 0; i < entrada.length; i++) {
            for (int j = 0; j < entrada[0].length; j++) {
                if (j == 0 && entrada[i][j] < 10 && ((int) Math.pow(entrada.length, 2)) > 10) {
                    System.out.print(CYAN + "|  " + PURPLE + entrada[i][j] + RESET);
                } else if (j == 0) {
                    System.out.print(CYAN + "| " + PURPLE + entrada[i][j] + RESET);
                } else if (j == entrada[0].length - 1 && entrada[i][j] < 10 && ((int) Math.pow(entrada.length, 2)) > 10) {
                    System.out.println("  " + PURPLE + entrada[i][j] + CYAN + " |" + RESET);
                } else if (j == entrada[0].length - 1) {
                    System.out.println(" " + PURPLE + entrada[i][j] + CYAN + " |" + RESET);
                } else if (entrada[i][j] < 10 && ((int) Math.pow(entrada.length, 2)) > 10) {
                    System.out.print("  " + PURPLE + entrada[i][j] + RESET);
                } else {
                    System.out.print(" " + PURPLE + entrada[i][j] + RESET);
                }
            }
        }
        System.out.println("");
    }

    public static int[][] llenaTablero(int[] entrada, int n) {
        int[][] tablero = new int[n][n];
        int m = 0;

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                tablero[i][j] = entrada[m];
                m++;
            }
        }
        return tablero;
    }

    public static void muestraTablero(int entrada[][], int n) {
        String[][] salida = new String[n][n];

        for (int i = 0; i < entrada.length; i++) {
            for (int j = 0; j < entrada[0].length; j++) {
                switch (entrada[i][j]) {
                    case 0:
                        salida[i][j] = " ";
                        break;
                    case 1:
                        salida[i][j] = "X";
                        break;
                    case 2:
                        salida[i][j] = "O";
                        break;
                }
            }
        }

        for (int i = 0; i < salida.length; i++) {
            for (int j = 0; j < salida[0].length; j++) {
                if (j == 0) {
                    System.out.print(CYAN + "| " + color(entrada, i, j) + salida[i][j] + RESET);
                } else if (j == salida[0].length - 1) {
                    System.out.println(" " + color(entrada, i, j) + salida[i][j] + CYAN + " |" + RESET);
                } else {
                    System.out.print(" " + color(entrada, i, j) + salida[i][j] + RESET);
                }
            }
        }
        System.out.println("");
    }

    public static boolean comprobarEmpate(int[] vector) {
        boolean lleno = true;

        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == 0) {
                return lleno = false;
            }
        }
        return lleno;
    }

    public static int cambiaTurno(int turno, int j1, int j2) {
        if (turno == j1) {
            return turno = j2;
        } else {
            return turno = j1;
        }
    }

    public static boolean comprobarVictoria(int[][] entrada, int n) {
        if (comprobarFilas(entrada, n)) {
            return true;
        } else if (comprobarColumnas(entrada, n)) {
            return true;
        } else if (comprobarDiagonales(entrada, n)) {
            return true;
        }
        return false;
    }

    public static boolean comprobarFilas(int[][] entrada, int n) {
        int contador = 0;

        for (int i = 0; i < entrada.length; i++) {
            contador = 0;

            for (int j = 1; j < entrada.length; j++) {
                if (entrada[i][0] == entrada[i][j] && entrada[i][0] > 0) {
                    contador++;
                }
            }
            if (contador == n - 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean comprobarColumnas(int[][] entrada, int n) {
        int contador = 0;

        for (int i = 0; i < entrada.length; i++) {
            contador = 0;

            for (int j = 1; j < entrada[0].length; j++) {
                if (entrada[0][i] == entrada[j][i] && entrada[0][i] > 0) {
                    contador++;
                }
            }
            if (contador == n - 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean comprobarDiagonales(int[][] entrada, int n) {
        int contador = 0;
        int f = entrada.length - 1;
        int c = 0;

        for (int i = 0; i < entrada.length; i++) {
            contador = 0;

            for (int j = 1; j < entrada[0].length; j++) {
                if (entrada[0][0] == entrada[j][j] && entrada[0][0] > 0) {
                    contador++;
                }
            }
            if (contador == n - 1) {
                return true;
            }

            contador = 0;

            for (int j = 1; j < entrada[0].length; j++) {
                if (entrada[f][c] == entrada[f - j][c + j] && entrada[f][c] > 0) {
                    contador++;
                }
            }
            if (contador == n - 1) {
                return true;
            }
        }
        return false;
    }

    public final static void limpiaPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String color(int[][] entrada, int i, int j) {
        switch (entrada[i][j]) {
            case 1:
                return GREEN;
            case 2:
                return YELLOW;
            default:
                return RESET;
        }
    }

    public static String color(int turno) {
        switch (turno) {
            case 1:
                return GREEN;
            case 2:
                return YELLOW;
            default:
                return RESET;
        }
    }

    // Reset
    public static final String RESET = "\033[0m";

    // Color de texto
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
}
