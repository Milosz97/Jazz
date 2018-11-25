package domain.services;

import java.util.ArrayList;
import java.util.List;

import domain.Ware;

public class WareService {

	private static List<Ware> db = new ArrayList<Ware>();
	private static int currentId = 1;
	
	public List<Ware> getAll(){
		return db;
	}
	
	public Ware get(int id) {
		for(Ware w : db) {
			if(w.getId()==id)
				return w;
		}
		return null;
	}
	
	public void add(Ware w) {
		w.setId(++currentId);
		db.add(w);
	}
	
	public void update(Ware ware) {
		for(Ware w : db) {
			if(w.getId()==ware.getId()) {
				w.setName(ware.getName());
				w.setPrice(ware.getPrice());
				w.setCategory(ware.getCategory());
			}
		}
	}
	
	public void delete(Ware w) {
		db.remove(w);
	}
	
}
