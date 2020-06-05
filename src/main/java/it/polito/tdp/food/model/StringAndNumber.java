package it.polito.tdp.food.model;

public class StringAndNumber {

	String p1;
	Integer peso;
	
	public StringAndNumber(String p1, Integer peso) {
		super();
		this.p1 = p1;
		this.peso = peso;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return   p1 +" "+peso ;
	}
	
	
}
