import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Lab4 {
    /* Повертає true, якщо існує шлях з джерела 's' до стоку 't' у залишковому графі.
       Також заповнює parent[], щоб зберегти шлях */
    private boolean bfs(int[][] rGraph, int s, int t, int[] parent, int n) {
        boolean[] visited = new boolean[n]; // Масив відвіданих вершин
        Arrays.fill(visited, false); // Позначаємо всі вершини як невідвідані
        LinkedList<Integer> queue = new LinkedList<Integer>(); // Створюємо чергу
        queue.add(s); // Додаємо потік в чергу
        visited[s] = true; // Позначаємо потік як відвіданий
        parent[s] = -1; // Для джерела parent = -1
 
        while (!queue.isEmpty()) { // Поки черга не пуста
            int u = queue.poll(); // Беремо вершину з початку черги
 
            for (int v = 0; v < n; v++) { // Перебираємо всі вершини у графі
                if (!visited[v] && rGraph[u][v] > 0) { // Якщо вершина ще не відвідана і є ребро між u та v в залишковому графі
                    if (v == t) { // Якщо відвідуємо сток, то записуємо батька і повертаємо true
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v); // Додаємо вершину в чергу
                    parent[v] = u; // Записуємо батька вершини v
                    visited[v] = true; // Позначаємо вершину як відвідану
                }
            }
        }
        return false; // Якщо не знайдено шляхів, повертаємо false
    }
    
    /* Знаходить максимальний потік в графі */
    public int maxFlow(int[][] graph, int source, int sink, int n) {
        int u, v;
        int[][] rGraph = new int[n][n];
        for (u = 0; u < n; u++) {
            for (v = 0; v < n; v++) {
                rGraph[u][v] = graph[u][v]; // Копіюємо значення зв'язків між вершинами у граф

            }
        }
        int[] parent = new int[n];
        int maxFlow = 0;
        while (bfs(rGraph, source, sink, parent, n)) {
            int pathFlow = Integer.MAX_VALUE;

            /* Знаходження мінімальної пропускної здатності у шляху */
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            /* Оновлення залишкового графу */
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= pathFlow; // Зменшуємо потік у напрямленому ребрі
                rGraph[v][u] += pathFlow; // Додаємо потік у звор
            }
            maxFlow += pathFlow;
        }
        return maxFlow;
    }
 
    public static void main(String[] args) throws IOException  {
    	// Визначаємо граф і кількість вузлів
		//n - розмірність читаємої матриці
        int n;
        //Оголошення матриці ваг
        int graph[][];
        //Читання матриці з файлу
        File f = new File("c:\\matrix.txt");
        Scanner sc = new Scanner(f);
        n = sc.nextInt();
        graph  = new int[n][n];
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            	 graph [i][j] = sc.nextInt();
        //Закриття файлу
        sc.close();

  
               Lab4 m = new Lab4();
        
               System.out.println("Максимально можливий потік: "
                                  + m.maxFlow(graph, 0, n-1, n));
           }
       }