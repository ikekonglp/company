package utils;

import java.util.Hashtable;

public class CounterTable{
	public Hashtable<String, Integer> table;
	
	public CounterTable(){
		table = new Hashtable<String,Integer>();
	}
	
	public void AddCount(String w){
		w = w.toLowerCase();
		if(table.containsKey(w)){
			table.put(w, table.get(w) + 1);
		}else{
			table.put(w, 1);
		}
	}
	
	public void removeSingleAndBuildIndex(){
		// Now the table should be in the counter mode
		// Then we will perform that
		Object[] keyArray =  table.keySet().toArray();
		for(Object k : keyArray){
			String key = (String) k;
			if(table.get(key) == 1){
				table.remove(key);
			}
		}
		// Now give everyone a index
		int index = 0;
		for(String key : table.keySet()){
			table.put(key, index);
			index = index + 1;
		}
	}
	
	public int getIndex(String key){
		if(table.containsKey(key.toLowerCase())){
			return table.get(key.toLowerCase());
		}
		return -1;
	}
	
	public int getSize(){
		return table.size();
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CounterTable ct = new CounterTable();
		
	}

}
