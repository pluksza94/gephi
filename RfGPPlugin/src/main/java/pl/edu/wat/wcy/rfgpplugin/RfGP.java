/*
 * Copyright 2008-2017 Gephi
 * Authors : Paweł Łuksza
 * 
 * This file is part of Gephi.
 *
 * Gephi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gephi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package pl.edu.wat.wcy.rfgpplugin;

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
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = Generator.class)
public class RfGP implements Generator {

    protected int numberOfNodes = 4;
    protected int degree = 3;

    protected ProgressTicket progress;
    protected boolean cancel = false;

    @Override
    public void generate(ContainerLoader container) {

        double totalProbability = 0.0;
        double random = new Random().nextDouble();
        UndirectedGraph<Integer, DefaultEdge> targetGraph = null;

        Map<UndirectedGraph<Integer, DefaultEdge>, Double> R = new HashMap<>(); // zbiór maksymalnych f-grafów rzędu n
        Map<UndirectedGraph<Integer, DefaultEdge>, Double> X = new HashMap<>(); // zbiór wszystkich f-grafów rzędu n z t krawędziami
        Map<UndirectedGraph<Integer, DefaultEdge>, Double> Xprim = new HashMap<>(); // zbiór wszystkich f-grafów rzędu n z t+1 krawędziami
        Map<UndirectedGraph<Integer, DefaultEdge>, Double> S = new HashMap<>(); // zbiór następników grafu G należacego do zbioru X

        int denominator = 0;

        int n = numberOfNodes; // liczba wierzchołków
        int f = degree; // stopień grafu

        Progress.start(progress, (n * f) / 2);
        int progressUnit = 0;

        UndirectedGraph<Integer, DefaultEdge> g0 = new SimpleGraph<>(DefaultEdge.class);
        for (int i = 0; i < n; i++) {
            g0.addVertex(i);
        }
        X.put(g0, 1.0);

        for (int t = 0; t <= (n * f) / 2; t++) {
            for (Map.Entry<UndirectedGraph<Integer, DefaultEdge>, Double> g : X.entrySet()) {
                S.clear();
                denominator = 0;
                for (int i = 0; i < n - 1; i++) {
                    for (int j = i + 1; j < n; j++) {
                        UndirectedGraph<Integer, DefaultEdge> tmp = (UndirectedGraph<Integer, DefaultEdge>) SerializationUtils.clone((Serializable) g.getKey());
                        if (tmp.degreeOf(i) < f && tmp.degreeOf(j) < f) {
                            DefaultEdge newEdge = tmp.addEdge(i, j);
                            if (newEdge != null) {
                                denominator++;
                                if (!VF2.containsGraph(S, tmp, 1.0)) {
                                    S.put(tmp, 1.0);
                                }
                            }
                        }
                    }
                }
                if (S.isEmpty()) {
                    R.put(g.getKey(), g.getValue());
                } else {
                    for (Map.Entry<UndirectedGraph<Integer, DefaultEdge>, Double> gprim : S.entrySet()) {
                        if (!VF2.containsGraph(Xprim, gprim.getKey(), (gprim.getValue() / denominator) * g.getValue())) {
                            Xprim.put(gprim.getKey(), (gprim.getValue() / denominator) * g.getValue());
                        }
                    }
                }
            }
            X = (HashMap<UndirectedGraph<Integer, DefaultEdge>, Double>) SerializationUtils.clone((Serializable) Xprim);
            Xprim.clear();

            Progress.progress(progress, ++progressUnit);
        }

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
            nodes.add(container.factory().newNodeDraft(Integer.toString(i)));
            nodes.get(i).setLabel(Integer.toString(i));
            nodes.get(i).setColor(153, 153, 153);
        }

        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (targetGraph.containsEdge(i, j)) {
                    edges.add(container.factory().newEdgeDraft(Integer.toString(k)));
                    edges.get(k).setLabel(i + "<->" + j);
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
        return "Maximum f-Graph (RfGP)";
    }

    @Override
    public GeneratorUI getUI() {
        return Lookup.getDefault().lookup(RfGPUI.class);
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

}
