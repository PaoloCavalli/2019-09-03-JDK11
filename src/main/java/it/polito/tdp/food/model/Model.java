package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;




import it.polito.tdp.food.db.FoodDao;

public class Model {
	private FoodDao dao ;
	private Graph <String, DefaultWeightedEdge> grafo;
	private List <Adiacenza> adiacenze;
	
	private List<String> ottimo;

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
			if(this.grafo.containsVertex(a.getP1()) && this.grafo.containsVertex(a.getP2())) {
			    if(this.grafo.getEdge(a.getP1(), a.getP2()) == null){
				   Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(),(int)a.getPeso());
				
		    	}
			}
		}
		System.out.println(String.format("Grafo creato con %d vertici e %d archi", this.grafo.vertexSet().size(), this.grafo.edgeSet().size()));
	}
	
	//Punto 1E
    public List<StringAndNumber> getNodiConnessi(String sorgente){
		
    	List<StringAndNumber> correlati  = new ArrayList<>();
    	for(String s : Graphs.neighborListOf(this.grafo, sorgente)) {
    		
    		 DefaultWeightedEdge e = this.grafo.getEdge(sorgente, s);
   		  Integer peso =  (int) this.grafo.getEdgeWeight(e);
   		  correlati.add(new StringAndNumber(s,peso));
    		
    	}
    	return correlati;
		
	}

	
	public int nVertici() {
		return	this.grafo.vertexSet().size();
		}
		public int nArchi() {
			return this.grafo.edgeSet().size();
		}
		//PUNTO 2
		
      public Integer peso(List<String> parziale) {
    	Integer peso = 0;
    	
    	for(int i = 0; i< parziale.size()-1 ; i++) {
    		
    		DefaultWeightedEdge e = grafo.getEdge(parziale.get(i), parziale.get(i + 1));
			int pesoArco = (int) grafo.getEdgeWeight(e);
			peso += pesoArco;
    	}
    	return peso;
      }
    
       
		
		public List<String> trovaCammino(String  sorgente, int passi ){
			List<String> parziale  = new ArrayList<>();
			this.ottimo = new ArrayList<>();
			parziale.add(sorgente);
			ricorsiva (parziale,passi,0);
			
			return ottimo;
			
		}
		

		private void ricorsiva(List<String> parziale, int passi, int livello) {
		   
			
			if(livello  == passi) {
			   if(peso(parziale)> peso(ottimo)){
				  ottimo = new ArrayList<>(parziale); 
				  return;
			   }
			
				}
			
			for(String vicino : Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
				if(!parziale.contains(vicino)){
					
					parziale.add(vicino);
					ricorsiva(parziale, passi, livello+1);
					parziale.remove(parziale.size()-1);
				}
			}
			
			
			
		}
		
		
	
}
