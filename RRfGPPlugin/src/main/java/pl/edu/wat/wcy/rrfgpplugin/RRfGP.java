package pl.edu.wat.wcy.rrfgpplugin;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDirection;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = Generator.class)
public class RRfGP implements Generator {

    protected int numberOfNodes = 4;
    protected int degree = 3;
    protected int numberOfSteps = 1;

    protected ProgressTicket progress;
    protected boolean cancel = false;

    private static boolean containGraph(Map<UndirectedGraph<Integer, DefaultEdge>, Double> A, UndirectedGraph x, Double increasingValue) {
        boolean result = false;
        for (Map.Entry<UndirectedGraph<Integer, DefaultEdge>, Double> a : A.entrySet()) {
            VF2GraphIsomorphismInspector<Integer, DefaultEdge> vf2 = new VF2GraphIsomorphismInspector<>(a.getKey(), x);

            if (vf2.isomorphismExists()) {
                A.replace(a.getKey(), a.getValue() + increasingValue);
                result = true;
            }
        }
        return result;
    }

    @Override
    public void generate(ContainerLoader container) {
        Progress.start(progress, numberOfSteps);

        double totalProbability = 0.0;
        Double random = new Random().nextDouble();
        UndirectedGraph<Integer, DefaultEdge> targetGraph = null;

        Map<UndirectedGraph<Integer, DefaultEdge>, Double> R = new HashMap(); // zbiór maksymalnych f-grafów rzędu n
        Map<UndirectedGraph<Integer, DefaultEdge>, Double> X = new HashMap<>(); // zbiór wszystkich f-grafów rzędu n z t krawędziami
        Map<UndirectedGraph<Integer, DefaultEdge>, Double> Xprim = new HashMap<>(); // zbiór wszystkich f-grafów rzędu n z t+1 krawędziami
        Map<UndirectedGraph<Integer, DefaultEdge>, Double> S = new HashMap<>(); // zbiór następników grafu G należacego do zbioru X

        int denominator = 0;

        int n = numberOfNodes; // liczba wierzchołków
        int f = degree; // stopień grafu

        UndirectedGraph<Integer, DefaultEdge> g0 = new SimpleGraph<>(DefaultEdge.class);
        for (int i = 0; i < n; i++) {
            g0.addVertex(i);
        }
        X.put(g0, 1.0);

        for (int t = 0; t <= numberOfSteps; t++) {
            for (Map.Entry<UndirectedGraph<Integer, DefaultEdge>, Double> g : X.entrySet()) {
                S.clear();
                denominator = 0;
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        UndirectedGraph<Integer, DefaultEdge> tmp = (UndirectedGraph<Integer, DefaultEdge>) SerializationUtils.clone((Serializable) g.getKey());
                        if (tmp.degreeOf(i) < f && tmp.degreeOf(j) < f) {
                            DefaultEdge newEdge = tmp.addEdge(i, j);
                            if (newEdge == null) {
                                tmp.removeEdge(i, j);
                            }
                            denominator++;
                            if (!containGraph(S, tmp, 1.0)) {
                                S.put(tmp, 1.0);
                            }
                        }
                    }
                }
                for (Map.Entry<UndirectedGraph<Integer, DefaultEdge>, Double> gprim : S.entrySet()) {
                    if (!containGraph(Xprim, gprim.getKey(), (gprim.getValue() / denominator) * g.getValue())) {
                        Xprim.put(gprim.getKey(), (gprim.getValue() / denominator) * g.getValue());
                    }
                }
            }
            X = (HashMap<UndirectedGraph<Integer, DefaultEdge>, Double>) SerializationUtils.clone((Serializable) Xprim);
            Xprim.clear();
            
            Progress.progress(progress, t);
        }

        R = (HashMap<UndirectedGraph<Integer, DefaultEdge>, Double>) SerializationUtils.clone((Serializable) X);

        Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "n={0}; f={1}; |R|={2}", new Object[]{n, f, R.size()});
        Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "R={0}", new Object[]{R.toString()});

        for (Map.Entry<UndirectedGraph<Integer, DefaultEdge>, Double> r : R.entrySet()) {
            totalProbability += r.getValue();
            if (random <= totalProbability) {
                targetGraph = (UndirectedGraph<Integer, DefaultEdge>) SerializationUtils.clone((Serializable) r.getKey());
            }
        }

        Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "Drawn number: {0}; Drawn graph: {1}", new Object[]{random, targetGraph.toString()});

        List<NodeDraft> nodes = new ArrayList<>();
        List<EdgeDraft> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            nodes.add(container.factory().newNodeDraft());
            nodes.get(i).setLabel("" + i);
            nodes.get(i).setColor(153,153,153);
        }

        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (targetGraph.containsEdge(i, j)) {
                    edges.add(container.factory().newEdgeDraft());
                    edges.get(k).setSource(nodes.get(i));
                    edges.get(k).setTarget(nodes.get(j));
                    edges.get(k).setDirection(EdgeDirection.UNDIRECTED);
                    k++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            container.addNode(nodes.get(i));
        }

        for (int i = 0; i < k; i++) {
            container.addEdge(edges.get(i));
        }

        Progress.finish(progress);
        progress = null;
    }

    @Override
    public String getName() {
        return "Random f-Graph (RRfGP)";
    }

    @Override
    public GeneratorUI getUI() {
        return Lookup.getDefault().lookup(RRfGPUI.class);
    }

    @Override
    public boolean cancel() {
        cancel = true;
        return true;
    }

    @Override
    public void setProgressTicket(ProgressTicket progressTicket) {
        this.progress = progressTicket;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        if (numberOfNodes >= 4 && numberOfNodes <= 7) {
            this.numberOfNodes = numberOfNodes;
        } else {
            throw new IllegalArgumentException("Liczba wierzchołków dlatego algorytmu musi być pomiędzy 4 i 7");
        }
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        if (degree >= 1 && degree <= this.numberOfNodes - 1) {
            this.degree = degree;
        } else {
            throw new IllegalArgumentException("Stopień grafu musi być pomiędzy 1 i liczbą wierzchołków-1");
        }
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        if (numberOfSteps >= 1) {
            this.numberOfSteps = numberOfSteps;
        } else {
            throw new IllegalArgumentException("Liczba kroków musi być liczbą dodatnią");
        }
    }

}
