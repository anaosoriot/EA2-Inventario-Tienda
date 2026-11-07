import java.util.Scanner;

/**
 * EA2 - Modeladores de Flujos (Programación Orientada a Objetos)
 * Escenario: inventario de una tienda de barrio en Medellín (datos simulados).
 * Estructuras: variables, condicionales (if/else, switch), ciclos (while, for)
 * y métodos dentro de una clase.
 */
public class InventarioTienda {

    // Datos SIMULADOS del inventario (arreglos paralelos)
    static String[] nombres = {"Arroz (kg)", "Frijol (kg)", "Panela (unid)", "Leche (litro)", "Huevos (unid)"};
    static int[] stock = {40, 25, 30, 18, 60};
    static int[] stockMinimo = {10, 10, 8, 10, 20};
    static double[] precios = {4500, 7200, 3800, 4200, 600};

    static double totalVentas = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        System.out.println("=== INVENTARIO TIENDA DE BARRIO (datos simulados) ===");

        // Ciclo principal: se repite mientras la opción sea distinta de 0 (salir)
        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEntero(sc, "Elija una opcion: ", 0, 4);
            if (opcion == -999) { // fin de entrada
                break;
            }

            switch (opcion) {
                case 1:
                    listarInventario();
                    break;
                case 2:
                    registrarVenta(sc);
                    break;
                case 3:
                    reabastecer(sc);
                    break;
                case 4:
                    mostrarAlertas();
                    break;
                case 0:
                    System.out.println("Cierre del dia. Total vendido: $" + (long) totalVentas);
                    break;
                default:
                    break;
            }
        }
        sc.close();
    }

    static void mostrarMenu() {
        System.out.println("\n1. Ver inventario");
        System.out.println("2. Registrar venta");
        System.out.println("3. Reabastecer producto");
        System.out.println("4. Alertas de stock bajo");
        System.out.println("0. Salir");
    }

    // Ciclo for: recorre todos los productos
    static void listarInventario() {
        System.out.println("\nN  | Producto        | Stock | Minimo | Precio");
        for (int i = 0; i < nombres.length; i++) {
            System.out.println((i + 1) + "  | " + nombres[i] + " | " + stock[i] + " | "
                    + stockMinimo[i] + " | $" + (long) precios[i]);
        }
    }

    static void registrarVenta(Scanner sc) {
        listarInventario();
        int p = leerEntero(sc, "Numero de producto (1-" + nombres.length + "): ", 1, nombres.length);
        if (p == -999) return;
        int idx = p - 1;
        int cant = leerEntero(sc, "Cantidad a vender: ", 1, 1000);
        if (cant == -999) return;

        // Decision: validar que haya existencias suficientes
        if (cant > stock[idx]) {
            System.out.println("ERROR: stock insuficiente. Disponible: " + stock[idx]);
        } else {
            stock[idx] -= cant;
            double subtotal = cant * precios[idx];
            totalVentas += subtotal;
            System.out.println("Venta registrada: " + cant + " x " + nombres[idx] + " = $" + (long) subtotal);
            if (stock[idx] <= stockMinimo[idx]) {
                System.out.println("ALERTA: " + nombres[idx] + " quedo en stock minimo (" + stock[idx] + ").");
            }
        }
    }

    static void reabastecer(Scanner sc) {
        listarInventario();
        int p = leerEntero(sc, "Numero de producto (1-" + nombres.length + "): ", 1, nombres.length);
        if (p == -999) return;
        int cant = leerEntero(sc, "Cantidad que ingresa: ", 1, 1000);
        if (cant == -999) return;
        stock[p - 1] += cant;
        System.out.println("Nuevo stock de " + nombres[p - 1] + ": " + stock[p - 1]);
    }

    static void mostrarAlertas() {
        boolean hay = false;
        for (int i = 0; i < nombres.length; i++) {
            if (stock[i] <= stockMinimo[i]) {
                System.out.println("REPONER: " + nombres[i] + " (stock " + stock[i] + ", minimo " + stockMinimo[i] + ")");
                hay = true;
            }
        }
        if (!hay) {
            System.out.println("Sin alertas: todos los productos estan sobre el minimo.");
        }
    }

    /**
     * Lee un entero dentro de [min, max]. Repite hasta que la entrada sea valida.
     * Maneja texto no numerico y numeros fuera de rango. Devuelve -999 si se acaba la entrada.
     */
    static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            if (!sc.hasNextLine()) {
                return -999;
            }
            String linea = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Entrada invalida: debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida: escriba un numero entero.");
            }
        }
    }
}
