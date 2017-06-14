package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObject( Map <Integer, ArtObject> mappa) {
		
		String sql = "SELECT * from objects";

		List<ArtObject> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				int id = res.getInt("object_id");
				String titolo = res.getString("title");
				ArtObject ogg = new ArtObject (id, titolo);
				result.add(ogg);
				mappa.put(ogg.getObjectId(), ogg);
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
public static List<Exhibition> getAllExhibitionsFromYear(Integer anno, Map <Integer, Exhibition> mostre) {
		
		String sql = "SELECT exhibition_id, exhibition_title, begin, end " +
					"FROM exhibitions " +
					" WHERE begin >= ?";
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery() ;
			
			List<Exhibition> list = new ArrayList<>() ;
			
			while(rs.next()) {
				int id = rs.getInt("exhibition_id");
				String nome = rs.getString("exhibition_title" );
				int inizio = rs.getInt("begin");
				int fine = rs.getInt("end");
				Exhibition e = new Exhibition(id, nome,inizio,fine);
				list.add(e) ;
				mostre.put(id, e);
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
			
		}
	}
	
public static List<Integer> getYears(){
		
		String sql = "SELECT DISTINCT  begin " +
					"FROM exhibitions " +
					"ORDER BY begin ASC";
			
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			
			while(rs.next()) {
		
				list.add(rs.getInt("begin")) ;
		
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
			
		}
	}

public void addOpereAMostra( Exhibition e, Map <Integer, ArtObject> map ){
	
	String sql = "SELECT objects.object_id as id, objects.title as title " + 
			"FROM exhibition_objects, objects " + 
			"WHERE exhibition_objects.object_id = objects.object_id " + 
			"AND exhibition_id = ?";
		
	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		st.setInt(1, e.getId());
		
		ResultSet rs = st.executeQuery() ;
		
		while(rs.next()) {
			
			int id = rs.getInt("id");
			String titolo = rs.getString("title");
			ArtObject ogg = map.get(id);
			
			e.addOpera(ogg);

		}
		
		conn.close();
		
	} catch (SQLException ex) {
		// TODO Auto-generated catch block
		ex.printStackTrace();
		throw new RuntimeException("SQL Query Error");
		
	}
}

// METODO NON USATO
// public static Integer getNumeroOpere(int id){
//	
//	String sql = "SELECT  COUNT(object_id) as num " +
//				"FROM exhibition_objects " +
//				"WHERE exhibition_id = ?";
//	
//	int numero = 0;
//		
//	try {
//		Connection conn = DBConnect.getConnection() ;
//
//		PreparedStatement st = conn.prepareStatement(sql) ;
//		st.setInt(1, id);
//		
//		ResultSet rs = st.executeQuery() ;
//		
//		if(rs.next()) {
//			numero = rs.getInt("num");
//		}
//		
//		conn.close();
//		return numero ;
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	//	throw new RuntimeException("SQL Query Error");
//		return  null;
//		
//	}
//}

}
