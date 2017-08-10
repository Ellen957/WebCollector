package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.doubanBook;
import Util.data;

public class bookDap {
	public void update(doubanBook book){
		Connection conn = data.getConnection();
		String ISBN = book.getISBN();
		int count = 0;
		System.out.println(ISBN);
		String sql1 = "select count(*) totalISBN from douban where BISBN='"+ISBN+"'";
		String sql2 = "insert into douban values('"+book.getBookName()+"','"+book.getAuthor()+"','"+book.getPublishTime()+"',"
				+ "'"+book.getPublish()+"','"+book.getISBN()+"','"+book.getDescrip()+"','"+book.getPrice()+"',"
						+ "'"+book.getScore()+"','"+book.getAuthorDes()+"')";
		
		try{
			PreparedStatement ptmt1 = conn.prepareStatement(sql1);
			ResultSet isbnNum = ptmt1.executeQuery(sql1);
			while(isbnNum.next()){
				count = isbnNum.getInt("totalISBN");
			}
			
			if(count==0){
				PreparedStatement ptmt2 = conn.prepareStatement(sql2);
				int result = ptmt2.executeUpdate();
				System.out.println(result);
				System.out.println("ִ�гɹ�");
			}
				
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void updateName(String target1,String target2,String ID) {
		Connection conn = data.getConnection();
		
		String sql="update bookinfor_copy set BName='"+target1+"',BISBN='"+target2+"' where BID='"+ID+"'";
		
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateAuthor(String target1,String target2,String target3,String ID) {
		Connection conn = data.getConnection();
		
		String sql="update bookinfor_copy set BAuthor='"+target1+"',BPublishTime='"+target2+"',BPublish='"+target3+"' where BID='"+ID+"'";
		
		
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateDescrip(String target1,String target2,String ID) {
		Connection conn = data.getConnection();
		
		String sql = "update bookinfor_copy set BDescrip='"+target1+"',BCommend='"+target2+"' where BID='"+ID+"'";
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateDescrip2(String target1,String target2,String ID) {
		Connection conn = data.getConnection();
		
		String sql = "update bookinfor_copy set BDescrip='"+target1+"' ,BPrice='"+target2+"' where BID='"+ID+"'";
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateID(String ID) {
		Connection conn = data.getConnection();
		int count = 0;
		String sql="insert into bookinfor_copy(BID) values(?)";
		String searchSql = "select count(*) totalCount from bookinfor_copy where BID='"+ID+"'";
		
		//查询是否已经插入了ID
		try {
			PreparedStatement ptmt1 = conn.prepareStatement(searchSql);
			ResultSet num = ptmt1.executeQuery();
			while(num.next()){
				count = num.getInt("totalCount");
			}
			
			if(count==0){
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					
					ptmt.setString(1, ID);
					
					ptmt.executeUpdate();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
//	public static void main(String[] args) {
//		updateID("21090357");
//		}
}

	
