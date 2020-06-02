package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private FoodDao dao ;
	private Graph <String, DefaultWeightedEdge> grafo;
	private List <Adiacenza> adiacenze;
	
	public Model() {
		dao = new FoodDao();
		adiacenze = new ArrayList<>();
	}
	
	public List<String > getPorzioni(Integer calorie){
		return this.dao.getVertici(calorie);
	}
	public List<Adiacenza> getAdiacenze(Integer calorie){
		return this.dao.getAdiacenze(calorie);
	}
	
	public void creaGrafo(Integer calorie) {
		grafo =new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//Aggiungo tutti i vertici che rispettano i requisiti di calorie!
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(calorie));
		adiacenze = this.dao.getAdiacenze(calorie);
		
		
		for(Adiacenza a : adiacenze) {
			if(this.grafo.getEdge(a.getP1(), a.getP2()) == null){
				Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(),(int)a.getPeso());
				
			}
		}
		System.out.println(String.format("Grafo creato con %d vertici e %d archi", this.grafo.vertexSet().size(), this.grafo.edgeSet().size()));
	}
	//Manca il peso
    public List<String> getNodiConnessi(String sorgente){
		
		List<String> nodiVisitati = new LinkedList<>(); 
		GraphIterator<String, DefaultWeightedEdge> dfv = new DepthFirstIterator<>(grafo,sorgente);
		while (dfv.hasNext()) {
			
			nodiVisitati.add(dfv.next());
		}
		return nodiVisitati;
	}

	
	public int nVertici() {
		return	this.grafo.vertexSet().size();
		}
		public int nArchi() {
			return this.grafo.edgeSet().size();
		}
	
}
