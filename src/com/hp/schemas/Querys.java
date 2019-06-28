package com.hp.schemas;

public class Querys  {

    /**
     * 获取查询的行数 可以获取到cmdbid 若为null则不存在
     * */
    public String queryDB(String parm) {
        UCMDBJdbc uj = new UCMDBJdbc();
        String table = "CDM_CINDA_DB_1";
        String selectTing = "CMDB_ID";
        String because = "A_DS_NAME";
        String cmdbid = uj.queryCmdbID(table ,parm , selectTing , because);
        return cmdbid;
    }

}
