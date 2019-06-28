package com.hp.schemas;

import com.hp.ucmdb.generated.services.UcmdbFault;
import com.hp.ucmdb.generated.services.UcmdbServiceStub;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class excelToUcmdbTest extends Demo {

    public static void main(String[] args){
        playMother();
    }

    private static Querys querys = new Querys();
    private static ImportExcel poi = new ImportExcel();
    private static Properties properties = poi.toExcel();
    private static String FILE_PATH = properties.getProperty("filePath");
    private static String TYPE = properties.getProperty("TYPE");


    /**
     * ����    ����������޸� �����������
     */
    private static void playMother(){
        UcmdbServiceStub serviceStub = getStub();
        ImportExcel poi = new ImportExcel();
        List<List<String>> list = poi.read(FILE_PATH);
        String[] val = list.get(0).toArray(new String[0]);
        String type = TYPE;
        String cmdbId;
        switch (type){
            case "CINDA_DB":
                for (int i = 1; i < list.size() ; i++) {
                    String[] value = list.get(i).toArray(new String[0]);
                    if(querys.queryDB(value[0]) == null){
                        //������ֲ����������
                        getAddCIsAndRelationsDemo(serviceStub,value,val,type);
                    }else {
                        //������ִ������޸�
                        cmdbId = querys.queryDB(value[0]);
                        getUpdateCIsAndRelationsDemo(serviceStub,value,val,type,cmdbId);
                    }
                }
                break;




            default:
                System.out.println("����ƥ��ʧ��");
        }
    }

    /**
     * ���CI���룺
     */
    private static void getAddCIsAndRelationsDemo(UcmdbServiceStub serviceStub, String[] value,String[] val,String type) {
        UcmdbServiceStub.AddCIsAndRelationsE requestE = new UcmdbServiceStub.AddCIsAndRelationsE();
        UcmdbServiceStub.AddCIsAndRelations request = new UcmdbServiceStub.AddCIsAndRelations();
        request.setCmdbContext(context);
        request.setUpdateExisting(true);

        UcmdbServiceStub.CIsAndRelationsUpdates updates = new UcmdbServiceStub.CIsAndRelationsUpdates();
        UcmdbServiceStub.CIs cis = new UcmdbServiceStub.CIs();
        List<UcmdbServiceStub.CI> listCI = new ArrayList<>();
        UcmdbServiceStub.CI ci = new UcmdbServiceStub.CI();
        UcmdbServiceStub.ID id = new UcmdbServiceStub.ID();
        Map<Integer,String> map = new HashMap<>();
        ci.setID(id);
        ci.setType(type);

        id.setString("temp1");
        id.setTemp(true);

        UcmdbServiceStub.CIProperties props = new UcmdbServiceStub.CIProperties();
        UcmdbServiceStub.StrProps strProps = new UcmdbServiceStub.StrProps();


        for (int i = 0; i < val.length ; i++) {
            //���������ҵ���ǰ�е�ƥ�䣬Ȼ��ȡ�����ϴ�
            String str = properties.getProperty(val[i]);
            map.put(i,str);
        }
        for (int i = 0; i < map.size() ; i++) {
            UcmdbServiceStub.StrProp st = new UcmdbServiceStub.StrProp();
            st.setName(map.get(i));
            st.setValue(value[i]);
            strProps.addStrProp(st);
        }
        props.setStrProps(strProps);
        ci.setProps(props);
        listCI.add(ci);
        Object[] ciArray = listCI.toArray(new UcmdbServiceStub.CI[listCI.size()]);
        cis.setCI((UcmdbServiceStub.CI[]) ciArray);
        updates.setCIsForUpdate(cis);
        request.setCIsAndRelationsUpdates(updates);
        requestE.setAddCIsAndRelations(request);
        try {
            UcmdbServiceStub.AddCIsAndRelationsResponseE responseE = serviceStub.addCIsAndRelations(requestE);
            UcmdbServiceStub.AddCIsAndRelationsResponse response = responseE.getAddCIsAndRelationsResponse();
            UcmdbServiceStub.ClientIDToCmdbID[] ids = response.getCreatedIDsMap();
            for (UcmdbServiceStub.ClientIDToCmdbID idMap : ids) {
                System.out.println(idMap.getCmdbID());
            }
            System.out.println("������");
        } catch (RemoteException | UcmdbFault e) {
            System.out.println("���ʱ�����⣡��");
        }
    }

    /**
     * �޸�CI����
     */
    private static void getUpdateCIsAndRelationsDemo(UcmdbServiceStub serviceStub, String[] value, String[] val, String type, String CMDB_ID) {
        UcmdbServiceStub.UpdateCIsAndRelationsE requestE = new UcmdbServiceStub.UpdateCIsAndRelationsE();
        UcmdbServiceStub.UpdateCIsAndRelations request = new UcmdbServiceStub.UpdateCIsAndRelations();
        request.setCmdbContext(context);
        UcmdbServiceStub.CIsAndRelationsUpdates updates = new UcmdbServiceStub.CIsAndRelationsUpdates();
        UcmdbServiceStub.CIs cis = new UcmdbServiceStub.CIs();
        List<UcmdbServiceStub.CI> listCI = new ArrayList<>();
        UcmdbServiceStub.CI ci = new UcmdbServiceStub.CI();
        UcmdbServiceStub.ID id = new UcmdbServiceStub.ID();
        Map<Integer,String> map = new HashMap<>();
        ci.setID(id);
        ci.setType(type);

        id.setString(CMDB_ID.toLowerCase());


        UcmdbServiceStub.CIProperties props = new UcmdbServiceStub.CIProperties();
        UcmdbServiceStub.DateProps dateProps = new UcmdbServiceStub.DateProps();
        UcmdbServiceStub.StrProps strProps = new UcmdbServiceStub.StrProps();

        for (int i = 0; i < val.length ; i++) {
            //���������ҵ���ǰ�е�ƥ�䣬Ȼ��ȡ�����ϴ�
            String str = properties.getProperty(val[i]);
            map.put(i,str);
        }
        for (int i = 0; i < map.size() ; i++) {
            //�����ʱ������Date�ϴ�
            if (map.get(i).contains("Time")){
                UcmdbServiceStub.DateProp st = new UcmdbServiceStub.DateProp();
                st.setName(map.get(i));
                st.setValue(change(value[i]));
                dateProps.addDateProp(st);
            }else {
                UcmdbServiceStub.StrProp st = new UcmdbServiceStub.StrProp();
                st.setName(map.get(i));
                st.setValue(value[i]);
                strProps.addStrProp(st);
            }
        }
        props.setStrProps(strProps);
        props.setDateProps(dateProps);
        ci.setProps(props);
        listCI.add(ci);
        Object[] ciArray = listCI.toArray(new UcmdbServiceStub.CI[listCI.size()]);
        cis.setCI((UcmdbServiceStub.CI[]) ciArray);
        updates.setCIsForUpdate(cis);
        request.setCIsAndRelationsUpdates(updates);
        requestE.setUpdateCIsAndRelations(request);
        try {
            UcmdbServiceStub.UpdateCIsAndRelationsResponseE responseE = serviceStub.updateCIsAndRelations(requestE);
            UcmdbServiceStub.UpdateCIsAndRelationsResponse response = responseE.getUpdateCIsAndRelationsResponse();
            UcmdbServiceStub.ClientIDToCmdbID[] ids = response.getCreatedIDsMap();
            for (UcmdbServiceStub.ClientIDToCmdbID idMap : ids) {
                System.out.println(idMap.getCmdbID() + " is the id");
            }
            System.out.println("�������");
        } catch (RemoteException | UcmdbFault e) {
            System.out.println("����ʧ��" + e.toString());
        }
    }

    /**
     *  Stringת��ΪCalendar
     */
    private static Calendar change(String time){
        DateFormat dateFormat = new SimpleDateFormat("MMM dd HH:mm:ss zzz yyyy" , java.util.Locale.US);
        Date date;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            date = null;
        }
        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        return calendar;
    }

}