package package_2.HouseRentApp.houserent1.service;

import package_2.HouseRentApp.houserent1.domain.House;

/*
//house[]数组存放house对象；
1.响应Houseview的调用
2.完成对房屋的增删改查工作；
 */
public class HouseService {

    private House[] houses;//保存house对象；
    private int con=0;//记录当前数组有多少房屋信息了；
    private int idcounter=0;//设计id自增长（由程序员确定房屋id,而非用户）；

    public int getCon() {
        return con;
    }
    public int getHouseLength(){
        return houses.length;
    }

    public HouseService(int size)//在构造器中确定数组的大小；
    {
        houses=new House[size];
    }

    public void setIdcounter(int idcounter) {
        this.idcounter = idcounter;
    }

    public House[] list()//将数组返回至houseview;
    {
        return houses;
    }
    public boolean add(House house){
//        if(con>=houses.length)//        在houseview中判断数组是否已满；
//        {
//            System.out.println("数组已满，不可再添加；（暂未实现扩容技术）");
//            return false;
//        }
        houses[con++]=house;
        houses[con-1].setId(++idcounter);//根据当前 idcounter 重置房屋id;
        return true;
    }
    public boolean del(int del_id)//删除id为del_id的房屋信息；
    {
        int i;
        for(i=0;i<con && houses[i].getId()!=del_id;i++);//很low的查找；
        if(i==con)
            return false;
        else
        {
            for(int j=i;j<con-1;j++)
                houses[j]=houses[j+1];
            houses[--con]=null;//置空最后一个房屋信息，并修改con的值；
            return true;
        }
    }
    public House find(int id)
    {
        int k;
        for(k=0;k<con && houses[k].getId()!=id;k++);
        if(k==con)
            return null;
        else
            return houses[k];
    }
}
