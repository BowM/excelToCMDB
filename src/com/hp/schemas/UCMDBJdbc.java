package com.hp.schemas;

import java.sql.*;

public class UCMDBJdbc {

//	public static void main(String[] args) {
//		String parm = "����ϵͳ";
//		String table = "CDM_CINDA_DB_1";
//		String selectTing = "CMDB_ID";
//		String because = "A_DS_NAME";
//		String cmdbid = new UCMDBJdbc().queryCmdbID(table ,parm , selectTing , because);
//		System.out.println("cmdbid: " + cmdbid);
//	}

    private int columnCount;

	/**
	 * ��ѯCMDBID
	 */
	public String queryCmdbID( String table , String parm , String selectTing ,String because) {  //Ӧ��ϵͳ		ͨ����Ŀ���ƻ�ȡΨһID
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String cmdbid = null;
		try {
			conn = JDBCUtils_V3.getConnection("UCMDB");
			String sql = "select " + selectTing + " from " + table + "  where " + because + " = ?";
			assert conn != null;
			pstmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//ֻȡ��һ�����á�
			pstmt.setString(1, parm);
			rs = pstmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			columnCount = md.getColumnCount();//��ȡ����
			System.out.println(columnCount);
			if(rs.first()){//ֻȡ��һ����
				cmdbid=rs.getString(selectTing);
			}
			return cmdbid;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6.�ͷ���Դ
			JDBCUtils_V3.release(conn, pstmt, null);
		}
		return null;
	}

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

}
