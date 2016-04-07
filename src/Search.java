import java.util.*;
import java.util.PriorityQueue;

/**
 * Created by Eduardo on 04/04/2016.
 */
public class Search {

    private State start;
    private State goal;
    NodeSearch nodeSearch;
    private PriorityQueue<NodeSearch> frontier = new PriorityQueue<>(); // fila de prioridade
    private Set<State> explored = new HashSet<State>(); // lista de estados explorados, não é permitido repetir valores
    private ArrayList<NodeSearch> nodes = new ArrayList<NodeSearch>();

    /**
     *
     * @param start
     * @param goal
     */
    public Search(State start, State goal){
        // inicializa o nó de busca passando o custo de g(s) = 0
        this.nodeSearch = new NodeSearch(start, 0); //nodeSearch.getNodes().get(0);
        this.nodeSearch.setParent(null); // seta o parent do nó de busca como null, visto que ele não tem parent.
        this.start = this.nodeSearch.getState();
        this.goal = goal;
    }

    /**
     *
     * @throws Exception
     */
    public NodeSearch search() {
        int cont = 0;
        this.frontier.add(this.nodeSearch); // adiciona o nó de busca com o estado inicial na fronteira

        while(true) {
            String text = "";
            System.out.println("Passo " + (++cont));
            Iterator<NodeSearch> iterator = this.frontier.iterator();
            while (iterator.hasNext()){
                NodeSearch node = iterator.next();
                text += node.getState().getName() + " : " + node.getState().getEstimate() + ", ";
            }
            System.out.println("Fronteira: " + text);

            if (this.frontier.size() == 0) {
                return null;
            }

            NodeSearch newStateInfo = chooseNewState(frontier);
            State newState = newStateInfo.getState();
            int newStateCost = newStateInfo.getCostG();
            System.out.println("Explorado: " + newState.getName());
            // Adiciona na lista de estados explorados
            this.explored.add(newState);

            // Verifica se o estado que está sendo explorado é o objetivo
            if(newState == this.goal){
                return newStateInfo;
            }
            // Caso não seja o objetivo, itera entre as ações do estado explorado
            for (Map.Entry<State, Integer> entry: newState.getActions().entrySet()) {
                // Obtém o estado
                State state = entry.getKey();
                // Obtém o valor da estimativa
                int cost = entry.getValue();

                // Se esse estado obtido não foi explorado
                if(!this.explored.contains(state)){
                    // Cria um nó de busca
                    NodeSearch auxNode = new NodeSearch(state, cost+newStateCost);
                    // atualiza a fronteira com o custo f(s) do estado obtido
                    this.setFrontier(this.frontier, auxNode); // *****
                    // seta o parent do estado
                    auxNode.setParent(newState);
                }
            }


        }

    }



    /**
     *
     * @param node
     * @return
     */
    public PriorityQueue setFrontier(PriorityQueue front, NodeSearch node){
        // Soma o custo total com a estimativa do estado
        int nodeSearchTotalCost = node.getCostG()+node.getState().getEstimate();
        Iterator<NodeSearch> iterator = front.iterator();
        Arrays.sort(front.toArray());
        while(iterator.hasNext()){
            NodeSearch nodeInfo = iterator.next();
            // Obtém o estado
            State state = nodeInfo.getState();
            // Obtém o custo
            int cost = nodeInfo.getCostG();
            // Soma o custo g(s) com o custo estimado
            int nodeTotalCost = cost+state.getEstimate();

            if(nodeInfo.getState().getName().equals(node.getState().getName())) {
                System.out.println(nodeInfo.getState().getName());
                if (node.compareTo(nodeInfo) == -1) {
                    NodeSearch nodeAux = new NodeSearch(node.getState(), node.getCostG()+node.getState().getEstimate());
                    front.add(nodeAux);
                    return front;
                } else {
                    return front;
                }
            }
        }
        NodeSearch nodeAux1 = new NodeSearch(node.getState(), nodeSearchTotalCost);
        front.add(nodeAux1);

        return front;
    }

    /**
     *
     * @param front
     * @return
     * @throws Exception
     */
    public NodeSearch chooseNewState(PriorityQueue front) {
        NodeSearch head = this.frontier.poll(); //*******
        NodeSearch nodeAux;
        Iterator<NodeSearch> iterator = this.frontier.iterator();
        while (iterator.hasNext()){
            nodeAux = iterator.next();
            if (nodeAux.compareTo(head) == -1){
                head = nodeAux;
            }
        }
        return head;
    }


    public void printPath(NodeSearch node){
        State parent = node.getParent();
        System.out.println(node.getState().getName());

        while(parent != null){
            System.out.println("Parent: " + parent);
            for (Map.Entry<State, Integer> entry: parent.getActions().entrySet()){
                State state = entry.getKey();
                if (state.getName().equals(parent.getName())) {
                    parent = state;
                }
            }
        }
    }



    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        /**
         * Estados
         */
        State joaoPessoa = new State("João Pessoa", 460);
        State itabaiana = new State("Itabaiana", 360);
        State campinaGrande = new State("Campina Grande", 300);
        State santaRita = new State("Santa Rita", 451);
        State mamanguape = new State("Mamanguape", 380);
        State guarabira = new State("Guarabira", 340);
        State areia = new State("Areia", 316);
        State coxixola = new State("Coxixola", 232);
        State soledade = new State("Soledade", 243);
        State picui = new State("Picuí", 250);
        State monteiro = new State("Monteiro", 195);
        State patos = new State("Patos", 122);
        State pombal = new State("Pombal", 55);
        State itaporanga = new State("Itaporanga", 65);
        State catoleDoRocha = new State("Catolé do Rocha", 110);
        State sousa = new State("Sousa", 20);
        State cajazeiras = new State("Cajazeiras", 0);

        /**
         * Ações
         */
        joaoPessoa.addActions(santaRita, 26);
        joaoPessoa.addActions(itabaiana, 68);
        joaoPessoa.addActions(campinaGrande, 125);
        santaRita.addActions(mamanguape, 64);
        santaRita.addActions(joaoPessoa, 26);
        mamanguape.addActions(guarabira, 42);
        mamanguape.addActions(santaRita, 64);
        guarabira.addActions(mamanguape, 42);
        guarabira.addActions(areia, 41);
        itabaiana.addActions(campinaGrande, 65);
        itabaiana.addActions(joaoPessoa, 68);
        areia.addActions(campinaGrande, 40);
        areia.addActions(guarabira, 41);
        campinaGrande.addActions(itabaiana, 65);
        campinaGrande.addActions(soledade, 58);
        campinaGrande.addActions(areia, 40);
        campinaGrande.addActions(coxixola, 128);
        campinaGrande.addActions(joaoPessoa, 125);
        soledade.addActions(campinaGrande, 58);
        soledade.addActions(patos, 117);
        soledade.addActions(picui, 69);
        picui.addActions(soledade, 68);
        coxixola.addActions(campinaGrande, 128);
        coxixola.addActions(monteiro, 83);
        monteiro.addActions(itaporanga, 224);
        monteiro.addActions(coxixola, 83);
        patos.addActions(pombal, 71);
        patos.addActions(itaporanga, 108);
        patos.addActions(soledade, 117);
        pombal.addActions(catoleDoRocha, 57);
        pombal.addActions(patos, 71);
        pombal.addActions(sousa, 56);
        catoleDoRocha.addActions(pombal, 57);
        sousa.addActions(cajazeiras, 43);
        sousa.addActions(pombal, 56);
        itaporanga.addActions(patos, 108);
        itaporanga.addActions(cajazeiras, 121);
        itaporanga.addActions(monteiro, 224);
        cajazeiras.addActions(sousa, 43);
        cajazeiras.addActions(itaporanga, 121);

        /**
         * Busca
         */
        Search search = new Search(joaoPessoa, cajazeiras);
        NodeSearch final_state = search.search();
        search.printPath(final_state);


    }


}
