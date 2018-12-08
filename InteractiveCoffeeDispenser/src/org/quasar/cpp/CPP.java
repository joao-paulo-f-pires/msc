package org.quasar.cpp;

/**
 * Class for finding and printing the optimal Chinese Postman tour of multidigraphs.
 * For more details, read<br>
 * <a href="http://www.uclic.ucl.ac.uk/harold/cpp">http://www.uclic.ucl.ac.uk/harold/cpp</a>.
 *
 * @author Harold Thimbleby, 2001, 2, 3
 */

import java.util.Vector;

public class CPP{
  /**
   * Anything < 0
   */
  static final int NONE = -1;

  /**
   * Number of vertices.
   */
  int N;
  
  /**
   * Deltas of vertices.
   */
  int[] delta;
  
  /**
   * Unbalanced vertices.
   */
  int[] neg; 
  int[] pos;
  
  /**
   * Adjacency matrix, counts arcs between vertices.
   */
  int[][] arcs; // 
  
  /**
   * Vectors of labels of arcs (for each vertex pair).
   */
  Vector[][] label;
  
  /**
   * Repeated arcs in CPT.
   */
  int[][] f;
  
  /**
   *  Costs of cheapest arcs or paths.
   */
  float[][] c;
  
  /**
   *  Labels of cheapest arcs.
   */
  String[][] cheapestLabel;
  
  /**
   *  Whether path cost is defined between vertices.
   */
  boolean[][] defined;
  
  /**
   * Spanning tree of the graph.
   */
  int[][] path;
  
  /**
   * Total cost of traversing each arc once.
   */
  float basicCost;

  CPP(int vertices) {
    if ((N = vertices) <= 0) {
      throw new IllegalArgumentException("Number of vertices not defined");
    }
    delta = new int[N];
    defined = new boolean[N][N];
    label = new Vector[N][N];
    c = new float[N][N];
    f = new int[N][N];
    arcs = new int[N][N];
    cheapestLabel = new String[N][N];
    path = new int[N][N];
    basicCost = 0;
  }

  void solve(){
    leastCostPaths();
    checkValid();
    findUnbalanced();
    findFeasible();
    
    boolean breakCondition = true;
    while (breakCondition){
      breakCondition = improvements();
    }
  }
  
  CPP addArc(String lab, int u, int v, float cost) {
    if (!defined[u][v]) {
      label[u][v] = new Vector<>();
    }
    label[u][v].addElement(lab);
    basicCost += cost;
    if (!defined[u][v] || c[u][v] > cost) {
      c[u][v] = cost;
      cheapestLabel[u][v] = lab;
      defined[u][v] = true;
      path[u][v] = v;
    }
    arcs[u][v]++;
    delta[u]++;
    delta[v]--;
    return this;
  }

  /**
   * Floyd-Warshall algorithm Assumes no negative self-cycles.<br>
   * Finds least cost paths or terminates on finding any non-trivial negative cycle.
   */
  void leastCostPaths() {
    for (int k = 0; k < N; k++) {
      for (int i = 0; i < N; i++) {
        if (defined[i][k]) {
          for (int j = 0; j < N; j++) {
            if (defined[k][j]
                && (!defined[i][j] || c[i][j] > c[i][k] + c[k][j])) {
              path[i][j] = path[i][k];
              c[i][j] = c[i][k] + c[k][j];
              defined[i][j] = true;
              if (i == j && c[i][j] < 0) {
                // Stop on negative cycle
                return;
              }
            }
          }
        }
      }
    }
  }

  void checkValid() {
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (!defined[i][j]) {
          throw new IllegalStateException("Graph is not strongly connected");
        }

        if (c[i][i] < 0) {
          throw new IllegalStateException("Graph has a negative cycle");
        }
      }
    }
  }

  float cost(){
    return basicCost + phi();
  }

  float phi() {
    float phi = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        phi += c[i][j] * f[i][j];
      }
    }
    return phi;
  }

  void findUnbalanced() {
    // Number of vertices of negative/positive delta
    int nn = 0;
    int np = 0;

    for (int i = 0; i < N; i++) {
      if (delta[i] < 0) {
        nn++;
      } else if (delta[i] > 0) {
        np++;
      }
    }

    neg = new int[nn];
    pos = new int[np];
    
    nn = np = 0;
    for (int i = 0; i < N; i++) {
      // initialise sets
      if (delta[i] < 0) {
        neg[nn++] = i;
      } else if (delta[i] > 0) {
        pos[np++] = i;
      }
    }
  }

  void findFeasible() {
    // Delete next 3 lines to be faster, but non-reentrant
    int[] delta = new int[N];
    for (int i = 0; i < N; i++) {
      delta[i] = this.delta[i];
    }

    for (int u = 0; u < neg.length; u++) {
      int i = neg[u];
      for (int v = 0; v < pos.length; v++) {
        int j = pos[v];
        f[i][j] = -delta[i] < delta[j] ? -delta[i] : delta[j];
        delta[i] += f[i][j];
        delta[j] -= f[i][j];
      }
    }
  }

  boolean improvements(){
    CPP residual = new CPP(N);
    for (int u = 0; u < neg.length; u++){
      int i = neg[u];
      for (int v = 0; v < pos.length; v++){
        int j = pos[v];
        residual.addArc(null, i, j, c[i][j]);
        if (f[i][j] != 0){
          residual.addArc(null, j, i, -c[i][j]);
        }
      }
    }
    
    // find a negative cycle
    residual.leastCostPaths();
    for (int i = 0; i < N; i++){
      // cancel the cycle (if any)
      if (residual.c[i][i] < 0){
        int k = 0, u, v;
        boolean kunset = true;
        
        // find k to cancel
        u = i;
        do{
          v = residual.path[u][i];
          if (residual.c[u][v] < 0 && (kunset || k > f[v][u])){
            k = f[v][u];
            kunset = false;
          }
        } while ((u = v) != i);
        
        // cancel k along the cycle
        u = i;
        do {
          v = residual.path[u][i];
          if (residual.c[u][v] < 0){
            f[v][u] -= k;
          }else{
            f[u][v] += k;
          }
        } while ((u = v) != i);
        // have another go
        return true;
      }
    }
    // no improvements found
    return false;
  }

  // find a path between unbalanced vertices
  int findPath(int from, int f[][]) {
    for (int i = 0; i < N; i++) {
      if (f[from][i] > 0) {
        return i;
      }
    }
    return NONE;
  }

  void printCPT(int startVertex){
    int v = startVertex;

    // delete next 7 lines to be faster, but non-reentrant
    int[][] arcs = new int[N][N];
    int[][] f = new int[N][N];
    for (int i = 0; i < N; i++){
      for (int j = 0; j < N; j++){
        arcs[i][j] = this.arcs[i][j];
        f[i][j] = this.f[i][j];
      }
    }

    while (true){
      int u = v;
      if ((v = findPath(u, f)) != NONE){
        // remove path
        f[u][v]--;
        // break down path into its arcs
        for (int p; u != v; u = p){
          p = path[u][v];
          System.out.println("Take arc " + cheapestLabel[u][p] + " from " + u + " to " + p);
        }
      } else {
        int bridgeVertex = path[u][startVertex];
        if (arcs[u][bridgeVertex] == 0)
          break; // finished if bridge already used
        v = bridgeVertex;
        for (int i = 0; i < N; i++)
          // find an unused arc, using bridge last
          if (i != bridgeVertex && arcs[u][i] > 0)
          {
            v = i;
            break;
          }
        arcs[u][v]--; // decrement count of parallel arcs
        System.out.println("Take arc " + label[u][v].elementAt(arcs[u][v])
            + " from " + u + " to " + v); // use each arc label in turn
      }
    }
  }

  // Print arcs and f
  void debugarcf()
  {
    for (int i = 0; i < N; i++)
    {
      System.out.print("f[" + i + "]= ");
      for (int j = 0; j < N; j++)
        System.out.print(f[i][j] + " ");
      System.out.print("  arcs[" + i + "]= ");
      for (int j = 0; j < N; j++)
        System.out.print(arcs[i][j] + " ");
      System.out.println();
    }
  }

  // Print out most of the matrices: defined, path and f
  void debug()
  {
    for (int i = 0; i < N; i++)
    {
      System.out.print(i + " ");
      for (int j = 0; j < N; j++)
        System.out.print(j + ":" + (defined[i][j] ? "T" : "F") + " " +
            c[i][j] + " p=" + path[i][j] + " f=" + f[i][j] + "; ");
      System.out.println();
    }
  }

  // Print out non zero f elements, and phi
  void debugf()
  {
    float sum = 0;
    for (int i = 0; i < N; i++)
    {
      boolean any = false;
      for (int j = 0; j < N; j++)
        if (f[i][j] != 0)
        {
          any = true;
          System.out.print("f(" + i + "," + j + ":" + label[i][j] + ")=" + f[i][j] + "@" + c[i][j] + "  ");
          sum += f[i][j] * c[i][j];
        }
      if (any)
        System.out.println();
    }
    System.out.println("-->phi=" + sum);
  }

  // Print out cost matrix.
  void debugc()
  {
    for (int i = 0; i < N; i++)
    {
      boolean any = false;
      for (int j = 0; j < N; j++)
        if (c[i][j] != 0)
        {
          any = true;
          System.out.print("c(" + i + "," + j + ":" + label[i][j] + ")=" + c[i][j] + "  ");
        }
      if (any)
        System.out.println();
    }
  }

  public static void main(String args[]){
    CPP G = new CPP(4); // create a graph of four vertices

    //0 - NO_COINS
    //1 - ENOUGH_COINS
    //2 - HAS_COINS
    //3 - UNAVAILABLE
    
    G = G.addArc("NO_COINS_ENOUGH_COINS_accept", 0, 1, 1);
    G = G.addArc("ENOUGH_COINS_NO_COINS_brew", 1, 0, 1);
    G = G.addArc("ENOUGH_COINS_NO_COINS_reset", 1, 0, 1);
    G = G.addArc("NO_COINS_HAS_COINS_accept", 0, 2, 1);
    G = G.addArc("HAS_COINS_NO_COINS_reset", 2, 0, 1);
    G = G.addArc("HAS_COINS_HAS_COINS_accept", 2, 2, 1);
    G = G.addArc("ENOUGH_COINS_ENOUGH_COINS_accept", 1, 1, 1);
    G = G.addArc("ENOUGH_COINS_ENOUGH_COINS_brew", 1, 1, 1);
    G = G.addArc("HAS_COINS_ENOUGH_COINS_accept", 2, 1, 1);
    G = G.addArc("ENOUGH_COINS_HAS_COINS_brew", 1, 2, 1);
    G = G.addArc("UNAVAILABLE_NO_COINS_fill", 3, 0, 1);
    G = G.addArc("UNAVAILABLE_UNAVAILABLE_reset", 3, 3, 1);
    G = G.addArc("ENOUGH_COINS_UNAVAILABLE_brew", 1, 3, 1);
    G = G.addArc("UNAVAILABLE_HAS_COINS_fill", 3, 2, 1);
    G = G.addArc("UNAVAILABLE_ENOUGH_COINS_fill", 3, 1, 1);
    
    G.solve(); // find the CPT
    G.printCPT(0); // print it, starting from vertex 0
    System.out.println("Cost = " + G.cost());

    OpenCPP.test();
  }
}

// </tex>

// <tex file="open.tex">
class OpenCPP
{
  class Arc
  {
    String lab;
    int u, v;
    float cost;

    Arc(String lab, int u, int v, float cost)
    {
      this.lab = lab;
      this.u = u;
      this.v = v;
      this.cost = cost;
    }
  }

  Vector arcs = new Vector();
  int N;

  OpenCPP(int vertices)
  {
    N = vertices;
  }

  OpenCPP addArc(String lab, int u, int v, float cost)
  {
    if (cost < 0)
      throw new Error("Graph has negative costs");
    arcs.addElement(new Arc(lab, u, v, cost));
    return this;
  }

  float printCPT(int startVertex)
  {
    CPP bestGraph = null, g;
    float bestCost = 0, cost;
    int i = 0;
    do
    {
      g = new CPP(N + 1);
      for (int j = 0; j < arcs.size(); j++)
      {
        Arc it = (Arc) arcs.elementAt(j);
        g.addArc(it.lab, it.u, it.v, it.cost);
      }
      cost = g.basicCost;
      g.findUnbalanced(); // initialise g.neg on original graph
      g.addArc("'virtual start'", N, startVertex, cost);
      g.addArc("'virtual end'",
          // graph is Eulerian if neg.length=0
          g.neg.length == 0 ? startVertex : g.neg[i], N, cost);
      g.solve();
      if (bestGraph == null || bestCost > g.cost())
      {
        bestCost = g.cost();
        bestGraph = g;
      }
      ++i;
    } while (i < g.neg.length);
    System.out.println("Open CPT from " + startVertex + " (ignore virtual arcs)");
    bestGraph.printCPT(N);
    return cost + bestGraph.phi();
  }

  // </tex>
  
  static void test()
  {
    OpenCPP G = new OpenCPP(4); // create a graph of four vertices
    // add the arcs for the example graph
    G = G.addArc("NO_COINS_ENOUGH_COINS_accept", 0, 1, 1);
    G = G.addArc("ENOUGH_COINS_NO_COINS_brew", 1, 0, 1);
    G = G.addArc("ENOUGH_COINS_NO_COINS_reset", 1, 0, 1);
    G = G.addArc("NO_COINS_HAS_COINS_accept", 0, 2, 1);
    G = G.addArc("HAS_COINS_NO_COINS_reset", 2, 0, 1);
    G = G.addArc("HAS_COINS_HAS_COINS_accept", 2, 2, 1);
    G = G.addArc("ENOUGH_COINS_ENOUGH_COINS_accept", 1, 1, 1);
    G = G.addArc("ENOUGH_COINS_ENOUGH_COINS_brew", 1, 1, 1);
    G = G.addArc("HAS_COINS_ENOUGH_COINS_accept", 2, 1, 1);
    G = G.addArc("ENOUGH_COINS_HAS_COINS_brew", 1, 2, 1);
    G = G.addArc("UNAVAILABLE_NO_COINS_fill", 3, 0, 1);
    G = G.addArc("UNAVAILABLE_UNAVAILABLE_reset", 3, 3, 1);
    G = G.addArc("ENOUGH_COINS_UNAVAILABLE_brew", 1, 3, 1);
    G = G.addArc("UNAVAILABLE_HAS_COINS_fill", 3, 2, 1);
    G = G.addArc("UNAVAILABLE_ENOUGH_COINS_fill", 3, 1, 1);
    int besti = 0;
    float bestCost = 0;
    for (int i = 0; i < 4; i++)
    {
      System.out.println("Solve from " + i);
      float c = G.printCPT(i);
      System.out.println("Cost = " + c);
      if (i == 0 || c < bestCost)
      {
        bestCost = c;
        besti = i;
      }
    }
    System.out.println("// <tex file=\"open.tex\">");
    G.printCPT(besti);
    System.out.println("Best cost = " + bestCost);
  }
}
