package com.lms.dto.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

public class HolidayList {

	public static class Record {
		
		public Date date;
		public String ocassion;
	}
	
	public static Deque<Record> all(Connection con) throws SQLException {
		PreparedStatement statement = 
				con.prepareStatement(
						"select * from holiday_list"
						);
		ResultSet result = statement.executeQuery();
		Deque<Record> recs = new LinkedList<Record>();		
		while(result.next()) {
			System.out.println("Record: " + result.getString(1) + "  " + result.getString(2));
			Record rec = new Record();
			rec.date = result.getDate(1);
			rec.ocassion = result.getString(2);
			recs.add(rec);
		}
		statement.close();
		System.out.println("No user found.");
		return recs;
	}

	public static long getHolidaysInDuration(Connection con, Date fromDate, Date toDate) throws SQLException {
		PreparedStatement statement = 
				con.prepareStatement(
						"select * from holiday_list where date >= ? and date <= ?"
						);
		statement.setDate(1, new java.sql.Date(fromDate.getTime()));
		statement.setDate(2, new java.sql.Date(toDate.getTime()));
		ResultSet result = statement.executeQuery();
		Deque<Record> recs = new LinkedList<Record>();		
		while(result.next()) {
			System.out.println("Record: " + result.getString(1) + "  " + result.getString(2));
			Record rec = new Record();
			rec.date = result.getDate(1);
			rec.ocassion = result.getString(2);
			recs.add(rec);
		}
		statement.close();
		System.out.println("No user found.");
		return recs.size();
	}

}
