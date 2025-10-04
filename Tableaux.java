import java.util.*;

public class Tableaux {

    static Scanner sc = new Scanner(System.in); // Scanner unique pour tout le programme

    // Exercice 1 — Sous-suite maximale croissante
    public static void sousSuiteMaximale() {
        System.out.println("Entrez les éléments du tableau séparés par un espace :");
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] t = new int[n];
        for (int i = 0; i < n; i++) t[i] = Integer.parseInt(input[i]);

        int[] dp = new int[n];
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (t[i] > t[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            if (dp[i] > maxLen) maxLen = dp[i];
        }
        System.out.println("Longueur de la plus longue sous-suite croissante : " + maxLen);
    }

    // Exercice 2 — Tableau pivot
    public static void tableauPivot() {
        System.out.println("Entrez les éléments du tableau séparés par un espace :");
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] t = new int[n];
        for (int i = 0; i < n; i++) t[i] = Integer.parseInt(input[i]);

        int[] maxGauche = new int[n];
        int[] minDroite = new int[n];

        maxGauche[0] = Integer.MIN_VALUE;
        for (int i = 1; i < n; i++) maxGauche[i] = Math.max(maxGauche[i - 1], t[i - 1]);
        minDroite[n - 1] = Integer.MAX_VALUE;
        for (int i = n - 2; i >= 0; i--) minDroite[i] = Math.min(minDroite[i + 1], t[i + 1]);

        System.out.print("Pivots : ");
        boolean found = false;
        for (int i = 0; i < n; i++) {
            if (maxGauche[i] <= t[i] && t[i] <= minDroite[i]) {
                System.out.print(t[i] + " ");
                found = true;
            }
        }
        if (!found) System.out.print("Aucun pivot");
        System.out.println();
    }

    // Exercice 3 — Matrice spirale
    public static void matriceSpirale() {
        System.out.print("Entrez la taille n de la matrice : ");
        int n = sc.nextInt();
        sc.nextLine();
        int[][] mat = new int[n][n];
        int num = 1;
        int haut = 0, bas = n - 1, gauche = 0, droite = n - 1;

        while (num <= n * n) {
            for (int i = gauche; i <= droite; i++) mat[haut][i] = num++;
            haut++;
            for (int i = haut; i <= bas; i++) mat[i][droite] = num++;
            droite--;
            for (int i = droite; i >= gauche; i--) mat[bas][i] = num++;
            bas--;
            for (int i = bas; i >= haut; i--) mat[i][gauche] = num++;
            gauche++;
        }

        System.out.println("Matrice spirale :");
        for (int[] row : mat) {
            for (int val : row) System.out.print(val + "\t");
            System.out.println();
        }
    }

 // Exercice 4 — Plus grand rectangle de 1
    public static void plusGrandRectangle() {
        System.out.print("Nombre de lignes : ");
        int n = sc.nextInt();
        System.out.print("Nombre de colonnes : ");
        int m = sc.nextInt();
        sc.nextLine(); // consommer le retour à la ligne

        int[][] mat = new int[n][m];
        System.out.println("Entrez la matrice (chaque ligne avec " + m + " éléments séparés par des espaces) :");
        for (int i = 0; i < n; i++) {
            String[] row;
            while (true) {
                row = sc.nextLine().trim().split("\\s+");
                if (row.length == m) break; // On a la bonne longueur
                System.out.println("Erreur : veuillez entrer exactement " + m + " nombres pour cette ligne.");
            }
            for (int j = 0; j < m; j++) mat[i][j] = Integer.parseInt(row[j]);
        }

        int[] hauteur = new int[m];
        int maxArea = 0;
        int[] coords = new int[4];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                hauteur[j] = (mat[i][j] == 0) ? 0 : hauteur[j] + 1;

            Stack<Integer> stack = new Stack<>();
            int j = 0;
            while (j <= m) {
                int h = (j == m) ? 0 : hauteur[j];
                if (stack.isEmpty() || h >= hauteur[stack.peek()]) stack.push(j++);
                else {
                    int top = stack.pop();
                    int width = stack.isEmpty() ? j : j - stack.peek() - 1;
                    int area = hauteur[top] * width;
                    if (area > maxArea) {
                        maxArea = area;
                        coords[0] = i - hauteur[top] + 1;
                        coords[1] = stack.isEmpty() ? 0 : stack.peek() + 1;
                        coords[2] = i;
                        coords[3] = j - 1;
                    }
                }
            }
        }

        System.out.println("Plus grand rectangle de 1 :");
        System.out.println("Coordonnées : ligne_sup=" + coords[0] +
                ", col_gauche=" + coords[1] +
                ", ligne_inf=" + coords[2] +
                ", col_droite=" + coords[3]);
        System.out.println("Aire = " + maxArea);
    }

    // Exercice 5 — Permutation circulaire
    public static void permutationCirculaire() {
        System.out.print("Taille n du tableau : ");
        int n = sc.nextInt();
        sc.nextLine();
        int[] t = new int[n];
        System.out.println("Entrez les éléments :");
        String[] input = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) t[i] = Integer.parseInt(input[i]);

        boolean[] seen = new boolean[n + 1];
        for (int num : t) if (num >= 1 && num <= n) seen[num] = true;
        for (int i = 1; i <= n; i++) {
            if (!seen[i]) {
                System.out.println("Le tableau n'est pas une permutation circulaire valide.");
                return;
            }
        }

        int pos1 = -1;
        for (int i = 0; i < n; i++) if (t[i] == 1) pos1 = i;

        boolean valide = true;
        for (int i = 0; i < n; i++) {
            int expected = i + 1;
            int actual = t[(pos1 + i) % n];
            if (actual != expected) { valide = false; break; }
        }

        System.out.println(valide ? "Le tableau est une permutation circulaire valide."
                : "Le tableau n'est pas une permutation circulaire valide.");
    }

    // Exercice 6 — Sous-tableau de somme maximale
    public static void sousTableauMax() {
        System.out.println("Entrez les éléments du tableau séparés par un espace :");
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] t = new int[n];
        for (int i = 0; i < n; i++) t[i] = Integer.parseInt(input[i]);

        int maxSoFar = t[0], maxEndingHere = t[0];
        for (int i = 1; i < n; i++) {
            maxEndingHere = Math.max(t[i], maxEndingHere + t[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        System.out.println("Somme maximale d'une sous-suite contiguë : " + maxSoFar);
    }

 // Exercice 7 — Fréquence majoritaire
    public static void majorite() {
        System.out.println("Entrez les éléments du tableau séparés par un espace :");
        String[] input;
        while (true) {
            input = sc.nextLine().trim().split("\\s+");
            if (input.length > 0 && !input[0].isEmpty()) break; // S'assurer que l'utilisateur a saisi quelque chose
            System.out.println("Erreur : veuillez entrer au moins un nombre.");
        }

        int n = input.length;
        int[] t = new int[n];
        for (int i = 0; i < n; i++) t[i] = Integer.parseInt(input[i]);

        // Algorithme de Boyer-Moore pour trouver l'élément majoritaire
        int candidate = -1, count = 0;
        for (int num : t) {
            if (count == 0) { candidate = num; count = 1; }
            else if (num == candidate) count++;
            else count--;
        }

        count = 0;
        for (int num : t) if (num == candidate) count++;
        if (count > n / 2) System.out.println("L'élément majoritaire est : " + candidate);
        else System.out.println("Aucun élément majoritaire n'existe.");
    }

    // Exercice 8 — Nombres absents
    public static void nombresAbsents() {
        System.out.println("Entrez les éléments du tableau séparés par un espace :");
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] t = new int[n];
        for (int i = 0; i < n; i++) t[i] = Integer.parseInt(input[i]);

        boolean[] present = new boolean[n + 1];
        for (int num : t) if (num >= 1 && num <= n) present[num] = true;

        System.out.print("Nombres manquants : ");
        for (int i = 1; i <= n; i++) if (!present[i]) System.out.print(i + " ");
        System.out.println();
    }

    // Exercice 9 — Somme diagonale
    public static void sommeDiagonale() {
        System.out.print("Entrez la taille n de la matrice : ");
        int n = sc.nextInt();
        sc.nextLine();
        int[][] mat = new int[n][n];
        System.out.println("Entrez les éléments de la matrice :");
        for (int i = 0; i < n; i++) {
            String[] row = sc.nextLine().split(" ");
            for (int j = 0; j < n; j++) mat[i][j] = Integer.parseInt(row[j]);
        }

        int diag1 = 0, diag2 = 0;
        for (int i = 0; i < n; i++) {
            diag1 += mat[i][i];
            diag2 += mat[i][n - 1 - i];
        }

        System.out.println("Valeur absolue de la différence des diagonales : " + Math.abs(diag1 - diag2));
    }

    // Exercice 10 — Matrice magique 3x3
    public static void matriceMagique() {
        int[][] mat = new int[3][3];
        System.out.println("Entrez les éléments de la matrice 3x3 :");
        for (int i = 0; i < 3; i++) {
            String[] row = sc.nextLine().split(" ");
            for (int j = 0; j < 3; j++) mat[i][j] = Integer.parseInt(row[j]);
        }

        int s = Arrays.stream(mat[0]).sum();
        boolean magique = true;

        for (int i = 1; i < 3; i++)
            if (Arrays.stream(mat[i]).sum() != s) magique = false;

        for (int j = 0; j < 3; j++) {
            int sumCol = 0;
            for (int i = 0; i < 3; i++) sumCol += mat[i][j];
            if (sumCol != s) magique = false;
        }

        int diag1 = mat[0][0] + mat[1][1] + mat[2][2];
        int diag2 = mat[0][2] + mat[1][1] + mat[2][0];
        if (diag1 != s || diag2 != s) magique = false;

        System.out.println(magique ? "La matrice est magique !" : "La matrice n'est pas magique.");
    }

    // Menu principal
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nChoisissez un exercice (1-10) ou 0 pour quitter : ");
            int choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1 -> sousSuiteMaximale();
                case 2 -> tableauPivot();
                case 3 -> matriceSpirale();
                case 4 -> plusGrandRectangle();
                case 5 -> permutationCirculaire();
                case 6 -> sousTableauMax();
                case 7 -> majorite();
                case 8 -> nombresAbsents();
                case 9 -> sommeDiagonale();
                case 10 -> matriceMagique();
                case 0 -> { System.out.println("Au revoir !"); return; }
                default -> System.out.println("Exercice invalide !");
            }
        }
    }
}
