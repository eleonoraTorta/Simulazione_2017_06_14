package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;


public class TestArtsmiaDAO {

	public static void main(String[] args) {
		
		ArtsmiaDAO dao = new ArtsmiaDAO() ;
		Map<Integer, Exhibition> mappa = new TreeMap <Integer, Exhibition>();
		Map<Integer, ArtObject> map = new TreeMap <Integer, ArtObject>();
		
	//	List<ArtObject> objects = dao.listObject(map) ;
	//	System.out.println(objects.size());
		
	//	dao.getAllExhibitionsFromYear(1995, mappa) ;
			
		
		for( Exhibition e : mappa.values()){
			dao.addOpereAMostra( e, map );
		}
		
		for( Exhibition e : mappa.values()){
			System.out.println( e.getId() + " " + e.getListaOggetti().size() + "\n");
		}
		
//		int max = Integer.MIN_VALUE;
//		Exhibition mostra = null;
//		for( Exhibition e : mappa.values()){
//			if( e.getListaOggetti().size() > max){
//				max = e.getListaOggetti().size();
//				mostra = e;
//			}
//		}
//		System.out.println(mostra + " " + mostra.getListaOggetti().size());
//		
//		System.out.println( mappa.get(10).getListaOggetti().size());
//		
//		
	//	System.out.println(dao.getAllExhibitionsFromYear(2000,mappa));
	//	System.out.println(dao.getYears());
		

	}
	

	

}
