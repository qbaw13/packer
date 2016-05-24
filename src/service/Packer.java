package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Upakowuje bloki i przechwuje kolekcję rozwiązań w postaci drzew
 */
public class Packer {

    private ArrayList<Node> roots; //korzenie drzew powsta�ych na kolejnych etapach programowanbia dynamicznego

    public Packer() {
        Node root = new Node(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE); //poczatkowo jeden obszar wolny
        roots = new ArrayList<>();
        roots.add(root);
    }

    public void fit(List<Block> blocks) {
        Block block;

        for (int n = 0; n < blocks.size(); n++) { //kazdy blok
            block = blocks.get(n);
            ArrayList<Node> newRoots = new ArrayList<>(); //nowa kolekcja korzeni (zastąpi roots)

            for (int k = 0; k < roots.size(); k++) { //sprobuj dodac w kazdej z opcji
                ArrayList<Node> putOptions = new ArrayList<>(); //opcje wlozenia aktualnego bloku w aktualne drzewo
                findNodes(roots.get(k), block.getWidth(), block.getHeight(), putOptions); //znajdz te opcje

                if (!putOptions.isEmpty()) {
                    for (int l = 0; l < putOptions.size(); l++) { //dla ka�dej z opcji
                        putBlockInNode(block, putOptions.get(l)); //w aktualnym drzewie zastosuj opcję (ustaw blok w obszarze)
                        if (l != putOptions.size() - 1) { //w kazdym kroku procz ostatnim
                            newRoots.add(new Node(roots.get(k))); //kopiuj drzewo z opcja (ustawieniem bloku w obszarze)
                            unputBlockInNode(putOptions.get(l)); //cofnij zostosowanie opcji
                        } else { //w ostatnim
                            newRoots.add(roots.get(k)); //dodaj stare drzewo z now� opcj� (u�ycie starego drzewa - w ostatnim kroku ju� nie trzeba go kopiowa�)
                        }
                    }
                } else {
                    System.out.println("Nie dało się umieścić bloku: (" + block.getHeight() + "," + block.getHeight() + ")");
                }
            }

            roots.clear(); //wyczysc kolekcje korzeni
            roots = newRoots; //nadpisz kolekcj� nowych korzeni na miejsce starej
        }

    }

    /**
     * @return Kolekcja korzeni, dla których drzewa (ustawienia bloków) zajmują najmniejszy obszar
     */
    public List<Node> findRootsWithMinArea() {
        ArrayList<Node> minRoots = new ArrayList<>();
        ArrayList<Integer> areas = new ArrayList<>();
        Integer minArea = Integer.MAX_VALUE;

        for (int i = 0; i < roots.size(); i++) { //dla kazdego z korzeni
            Integer area = calcArea(roots.get(i)); //oblicz pole zjaetego obszaru jaki generuje drzewo
            areas.add(area); //dodaj pole do kolekcji p�l obszar�w

            if (area.compareTo(minArea) < 0) minArea = area; //szukanie min obszaru
        }

        for (int j = 0; j < areas.size(); j++) { //znajdowanie obszar�w z polem r�wnym minArea
            if (areas.get(j).equals(minArea)) {
                minRoots.add(roots.get(j));
            }
        }

        System.out.println("min pole: " + minArea);

        return minRoots;
    }

    /**
     * Znajduje ciąg decyzji w porgramowniu dynamicznym i zachowuje go w decisions
     *
     * @param n         węzeł drzewa, z którego chcemy pozyskać decyzje
     * @param decisions HashMap<krok w pd, para(x,y) ustawienia bloku w danym kroku>
     */
    public void getDecisions(Node n, Map<Integer, Pair<Integer>> decisions) {
        if (n.isUsed()) {
            decisions.put(n.getBlock().getId(), new Pair<Integer>(n.getX(), n.getY()));
        }
        if (n.getDown() != null) getDecisions(n.getDown(), decisions);
        if (n.getRight() != null) getDecisions(n.getRight(), decisions);
    }

    /**
     * @param node Korzeń drzewa obszaru
     * @return Pole jakie generuje obszar zajęty opisany przez drzewo
     */
    private Integer calcArea(Node node) {
        Pair<Integer> maxXmaxY = findMaxXAndY(node, new Pair<Integer>(0, 0));
        return maxXmaxY.getFirst() * maxXmaxY.getSecond();
    }

    /**
     * @param n        węzeł drzewa dla którego znajudemy dwie warto�ci
     * @param maxXmaxY aktualny wynik (rekursja)
     * @return para(maxX, maxY) maxX - wsp x najbardziej wysuniętego punktu pod względem osi X, maxY - wsp y najbardziej wysuniętego punktu pod względem osi Y
     */
    private Pair<Integer> findMaxXAndY(Node n, Pair<Integer> maxXmaxY) {
        if (n.isUsed()) {
            if (maxXmaxY.getFirst() < n.getX() + n.getBlock().getWidth()) maxXmaxY.setFirst(n.getX() + n.getBlock().getWidth());
            if (maxXmaxY.getSecond() < n.getY() + n.getBlock().getHeight())
                maxXmaxY.setSecond(n.getY() + n.getBlock().getHeight());
            findMaxXAndY(n.getDown(), maxXmaxY);
            findMaxXAndY(n.getRight(), maxXmaxY);
        }

        return maxXmaxY;

    }

    /**
     * Cofnij ustawienie jakiegokolwiek bloku w węźle
     */
    private void unputBlockInNode(Node node) {
        node.setUsed(false);
        node.setBlock(null);
        node.setDown(null);
        node.setRight(null);
    }

    /**
     * Ustaw blok w węźle
     */
    private void putBlockInNode(Block block, Node node) {
        node.setUsed(true);
        node.setBlock(block);
        node.setDown(new Node(node.getX(), node.getY() + block.getHeight(), node.getWidth(), node.getHeight() - block.getHeight()));
        node.setRight(new Node(node.getX() + block.getWidth(), node.getY(), node.getWidth() - block.getWidth(), block.getHeight()));
    }

    /**
     * Znajdź węzły w których można umieścić blok o wysokości h i szerokości w
     *
     * @param root       Korzeń drzewa
     * @param w          szerokość bloku
     * @param h          wysokość bloku
     * @param putOptions kolekcja wyników
     */
    private void findNodes(Node root, int w, int h, ArrayList<Node> putOptions) {
        if (root.isUsed()) {
            findNodes(root.getRight(), w, h, putOptions);
            findNodes(root.getDown(), w, h, putOptions);
        } else if (w <= root.getWidth() && h <= root.getHeight()) {
            putOptions.add(root);
        }

    }

}
