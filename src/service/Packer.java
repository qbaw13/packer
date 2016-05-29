package service;

import entity.Block;
import entity.Node;
import entity.Pair;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Upakowuje bloki i przechwuje kolekcję rozwiązań w postaci drzew
 */
public class Packer extends Service<Void>{

    private ArrayList<Node> roots; //korzenie drzew powstająych na kolejnych etapach programowanbia dynamicznego
    private List<Block> blocks;
    private int minimumOccupiedArea;


    public Packer() {
        Node root = initWholeAreaAsEmpty();
        roots = new ArrayList<>();
        roots.add(root);
        minimumOccupiedArea = 0;
    }

    private Node initWholeAreaAsEmpty() {
        return new Node(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Block block;

                for (int n = 0; n < blocks.size(); n++) { //kazdy blok
                    block = blocks.get(n);
                    ArrayList<Node> newRoots = new ArrayList<>(); //nowa kolekcja korzeni (zastąpi roots)

                    for (int k = 0; k < roots.size(); k++) { //sprobuj dodac w kazdej z opcji
                        ArrayList<Node> putOptions = new ArrayList<>(); //opcje wlozenia aktualnego bloku w aktualne drzewo
                        findNodes(roots.get(k), block.getWidth(), block.getHeight(), putOptions); //znajdz te opcje

                        if (!putOptions.isEmpty()) {
                            for (int l = 0; l < putOptions.size(); l++) { //dla każdej z opcji
                                putBlockInNode(block, putOptions.get(l)); //w aktualnym drzewie zastosuj opcję (ustaw blok w obszarze)
                                if (l != putOptions.size() - 1) { //w kazdym kroku procz ostatnim
                                    newRoots.add(new Node(roots.get(k))); //kopiuj drzewo z opcja (ustawieniem bloku w obszarze)
                                    removeBlockFromNode(putOptions.get(l)); //cofnij zostosowanie opcji
                                } else { //w ostatnim
                                    newRoots.add(roots.get(k)); //dodaj stare drzewo z nową opcją (użycie starego drzewa - w ostatnim kroku już nie trzeba go kopiować)
                                }
                            }
                        } else {
                            System.out.println("Nie dało się umieścić bloku: (" + block.getHeight() + "," + block.getHeight() + ")");
                        }
                    }

                    roots.clear(); //wyczysc kolekcje korzeni
                    roots = newRoots; //nadpisz kolekcję nowych korzeni na miejsce starej
                }
                return null;
            }
        };
    }



    /**
     * @return Kolekcja korzeni, dla których drzewa (ustawienia bloków) zajmują najmniejszy obszar
     */
    public List<Node> findRootsWithMinArea() {
        ArrayList<Node> minRoots = new ArrayList<>();
        ArrayList<Integer> areas = new ArrayList<>();
        Integer minArea = Integer.MAX_VALUE;

        for (int i = 0; i < roots.size(); i++) { //dla kazdego z korzeni
            Integer area = calculateOccupiedArea(roots.get(i));
            areas.add(area); //dodaj pole do kolekcji p�l obszar�w

            if (area.compareTo(minArea) < 0) minArea = area; //szukanie min obszaru
        }

        for (int j = 0; j < areas.size(); j++) { //znajdowanie obszar�w z polem r�wnym minArea
            if (areas.get(j).equals(minArea)) {
                minRoots.add(roots.get(j));
            }
        }

        minimumOccupiedArea = minArea;


        return minRoots;
    }

    /**
     * Znajduje ciąg decyzji w porgramowniu dynamicznym i zachowuje go w decisions
     *
     * @param node      węzeł drzewa, z którego chcemy pozyskać decyzje
     * @param decisions HashMap<krok w pd, para(x,y) ustawienia bloku w danym kroku>
     */
    public void getDecisions(Node node, Map<Integer, Pair<Integer>> decisions) {
        if (node.isUsed()) {
            decisions.put(node.getBlock().getId(), new Pair<Integer>(node.getX(), node.getY()));
        }
        if (node.getDown() != null) {
            getDecisions(node.getDown(), decisions);
        }
        if (node.getRight() != null) {
            getDecisions(node.getRight(), decisions);
        }
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * @param node Korzeń drzewa obszaru
     * @return Pole jakie generuje obszar zajęty opisany przez drzewo
     */
    private Integer calculateOccupiedArea(Node node) {
        Pair<Integer> maxXmaxY = findMaxXAndY(node, new Pair<Integer>(0, 0));
        return maxXmaxY.getFirst() * maxXmaxY.getSecond();
    }

    /**
     * @param node     węzeł drzewa dla którego znajudemy dwie wartości
     * @param maxXmaxY aktualny wynik (rekursja)
     * @return para(maxX, maxY) maxX - wsp x najbardziej wysuniętego punktu pod względem osi X, maxY - wsp y najbardziej wysuniętego punktu pod względem osi Y
     */
    private Pair<Integer> findMaxXAndY(Node node, Pair<Integer> maxXmaxY) {
        if (node.isUsed()) {
            if (maxXmaxY.getFirst() < node.getX() + node.getBlock().getWidth()) maxXmaxY.setFirst(node.getX() + node.getBlock().getWidth());
            if (maxXmaxY.getSecond() < node.getY() + node.getBlock().getHeight())
                maxXmaxY.setSecond(node.getY() + node.getBlock().getHeight());
            findMaxXAndY(node.getDown(), maxXmaxY);
            findMaxXAndY(node.getRight(), maxXmaxY);
        }

        return maxXmaxY;

    }

    /**
     * Cofnij ustawienie jakiegokolwiek bloku w węźle
     */
    private void removeBlockFromNode(Node node) {
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

    public int getMinimumOccupiedArea() {
        return minimumOccupiedArea;
    }
}
