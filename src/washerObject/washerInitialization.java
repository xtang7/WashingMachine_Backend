package washerObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.mysql.MySQLTableCreation;
import entity.Washer;
import entity.Washer.WasherBuilder;

public class washerInitialization {
	Map<String, Washer> washerMap = new HashMap<>();
	List<Washer> washerList = new ArrayList<>();
	
	public Map<String, Washer> getAllWashers(){
		create();
		return this.washerMap;
	}
	
	//Hard coded washers
	private void create() {
		//Initialize SQL 
		 MySQLTableCreation.main(new String[0]);
		
		//First Washer
		WasherBuilder builder1 = new WasherBuilder();
		builder1.setWasherId("washer_1").setDefaultMode(4);
		Washer w1 = builder1.builder();
		washerList.add(w1);
		
		//Second Washer
		WasherBuilder builder2 = new WasherBuilder();
		builder2.setWasherId("washer_2").setDefaultMode(2);
		Washer w2 = builder2.builder();
		washerList.add(w2);
		
		//Third Washer
		WasherBuilder builder3 = new WasherBuilder();
		builder3.setWasherId("washer_3").setDefaultMode(1);
		Washer w3 = builder3.builder();
		washerList.add(w3);
		
		//Fourth Washer
		WasherBuilder builder4 = new WasherBuilder();
		builder4.setWasherId("washer_4").setDefaultMode(5);
		Washer w4 = builder4.builder();
		washerList.add(w4);
		
		//Fifth Washer
		WasherBuilder builder5 = new WasherBuilder();
		builder5.setWasherId("washer_5").setDefaultMode(20);
		Washer w5 = builder5.builder();
		washerList.add(w5);
		
		//Sixth Washer
		WasherBuilder builder6 = new WasherBuilder();
		builder6.setWasherId("washer_6").setDefaultMode("PERMANENTPRESS");
		Washer w6 = builder6.builder();
		washerList.add(w6);
		
		//Seventh Washer
		WasherBuilder builder7 = new WasherBuilder();
		builder7.setWasherId("washer_7").setDefaultMode("HEAVYDUTY");
		Washer w7 = builder7.builder();
		washerList.add(w7);
		
		//Eighth Washer
		WasherBuilder builder8 = new WasherBuilder();
		builder8.setWasherId("washer_8").setDefaultMode("DEFAULT");
		Washer w8 = builder8.builder();
		washerList.add(w8);
		
		//Ninth Washer
		WasherBuilder builder9 = new WasherBuilder();
		builder9.setWasherId("washer_9").setDefaultMode("DELICATE");
		Washer w9 = builder9.builder();
		washerList.add(w9);
		
		//Tenth Washer
		WasherBuilder builder10 = new WasherBuilder();
		builder10.setWasherId("washer_10").setDefaultMode("RAPIDWASH");
		Washer w10 = builder10.builder();
		washerList.add(w10);
		
		//Eleventh Washer
		WasherBuilder builder11 = new WasherBuilder();
		builder11.setWasherId("washer_11").setDefaultMode("NORMAL");
		Washer w11 = builder11.builder();
		washerList.add(w11);
		
		//Twelfth Washer
		WasherBuilder builder12 = new WasherBuilder();
		builder12.setWasherId("washer_12").setDefaultMode(14);
		Washer w12 = builder12.builder();
		washerList.add(w12);
		
		//Thirteenth Washer
		WasherBuilder builder13 = new WasherBuilder();
		builder13.setWasherId("washer_13").setDefaultMode(16);
		Washer w13 = builder13.builder();
		washerList.add(w13);
		
		//Fourteenth Washer
		WasherBuilder builder14 = new WasherBuilder();
		builder14.setWasherId("washer_14").setDefaultMode(23);
		Washer w14 = builder14.builder();
		washerList.add(w14);
		
		//Fifteenth Washer
		WasherBuilder builder15 = new WasherBuilder();
		builder15.setWasherId("washer_15").setDefaultMode(2);
		Washer w15 = builder15.builder();
		washerList.add(w15);
		
		for(Washer w : washerList) {
			washerMap.put(w.getWasherId(), w);
		}	
	}
	
}
