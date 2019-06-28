package com.hp.schemas;

import java.sql.*;

public class UCMDBJdbc {

//	public static void main(String[] args) {
//		String parm = "内评系统";
//		String table = "CDM_CINDA_DB_1";
//		String selectTing = "CMDB_ID";
//		String because = "A_DS_NAME";
//		String cmdbid = new UCMDBJdbc().queryCmdbID(table ,parm , selectTing , because);
//		System.out.println("cmdbid: " + cmdbid);
//	}

    private int columnCount;

	/**
	 * 查询CMDBID
	 */
	public String queryCmdbID( String table , String parm , String selectTing ,String because) {  //应用系统		通过项目名称获取唯一ID
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String cmdbid = null;
		try {
			conn = JDBCUtils_V3.getConnection("UCMDB");
			String sql = "select " + selectTing + " from " + table + "  where " + because + " = ?";
			assert conn != null;
			pstmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//只取第一条配置。
			pstmt.setString(1, parm);
			rs = pstmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			columnCount = md.getColumnCount();//获取列数
			System.out.println(columnCount);
			if(rs.first()){//只取第一条。
				cmdbid=rs.getString(selectTing);
			}
			return cmdbid;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6.释放资源
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
