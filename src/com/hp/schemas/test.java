package com.hp.schemas;

public class test {

    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
//        Date date = new Date();
//        System.out.println(date);
////        calendar.set();
//        String string = DateFormat.getDateInstance().format(date);
//        System.out.println(string);
//        //calendar = DateFormat.getDateInstance().getCalendar();
//        System.out.println(calendar);
//        String time = "Mon Jun 24 18:21:54 CST 2019";
//        //             Mon Jun 24 18:21:54 CST 2019
//        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy" , java.util.Locale.US);
//        Date date;
//        try {
//            date = dateFormat.parse(time);
//        } catch (ParseException e) {
//            date = null;
//        }
//        System.out.println(date);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);

//        String str = "asdtimeasd";
//        System.out.println(str.contains("time"));
//        int[] arr = {2,3,1,0,2,5};
//        System.out.println(numRepeat(arr));

        //boolean a = Boolean.parseBoolean(null);
       int i = 1 ;
       int j = i++;
        System.out.println(j);
//        int a = 68;
//        a = ++ a * 2;
        //System.out.println(a);


    }

    //长度为n数组所有数字范围在[0,n-1],其中某些数字是重复的，但不知道有几个是重复的，也不知道重复几次，请找出任意一个重复的数字
    // Input {2,3,1,0,2,5}
    // Output 2
    private static int numRepeat(int[] arr){
        int N = arr.length;
        int a = 0;
        for (int index = 0; index < N ; index++){
            //每次循环的到的数字
            a = arr[index];
            if (a != index ){
                //如果每次循环的这个数字不是属于本位的数字 就把他移到他应该存在的位置

                if (a == index){
                    return a;
                }
                //如果他要移动的位置的数字和他相通 那就是他  :)
            }else {
                    break;
            }
        }
        return a;
    }
}