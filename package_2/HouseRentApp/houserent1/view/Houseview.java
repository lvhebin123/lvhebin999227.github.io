package package_2.HouseRentApp.houserent1.view;

import package_2.HouseRentApp.houserent1.domain.House;
import package_2.HouseRentApp.houserent1.service.HouseService;
import package_2.HouseRentApp.houserent1.utility.Utility;

/*$$$$Houseview（实现与用户的交互）与HouseService（实现对房屋信息的增删改查） @各@司@其@职；（很重要哦）
*1.显示菜单界面
*2.接受用户输入
*3.调用HouseService完成对房屋信息的各种操作；
*
 */
public class Houseview {
    private boolean loop=true;//控制循环；
    private char key;//接收用户选择；
    private HouseService houseService=new HouseService(5);//创建一个houseservice对象作为成员变量，以使用此对象的方法
    public  void mainMenu()
    {
        do {
            System.out.println("\n\n-------------房屋出租系统-------------\n");
            System.out.println("\t\t\t1:新增房源");
            System.out.println("\t\t\t2：查找房源");
            System.out.println("\t\t\t3：删除房屋信息");
            System.out.println("\t\t\t4：修改房屋信息");
            System.out.println("\t\t\t5：房屋列表");
            System.out.println("\t\t\t6：退出程序");
            System.out.println("\t请输入你的选择；(1-6)");
            key= Utility.readChar();
            switch (key)
            {
                case '1':
                    addHouse();
                    break;
                case '2':
                    findHouse();
                    break;
                case '3':
                    delHouse();
                    break;
                case '4':
                    update();
                    break;
                case '5':
                    listHouse();
                    break;
                case '6':
                    exit();
                    break;
                default:
                    System.out.println("非法输入");
            }
        }while(loop);
    }

    public void listHouse()//显示房屋信息；(房屋信息的获取交给HouseService，显示工作交给lisehouse());
    {
        House[] houses=houseService.list();
        //houses[0]=new House(12,"jack","123456","tiananmen",9999,"未租");
        System.out.println("============房屋列表============\n");
        System.out.println("编号\t\t房主\t\t电话\t\t地址\t\t月租\t\t状态（出租或未出租）");
        for(House h : houses)//数组不满，部分为空，出现接收异常；
        {
            if(h!=null)
                System.out.println(h.toString());//直接调用tostring();
            else
                continue;
        }
        //System.out.println(houses.length);
        System.out.println("\n============房屋列表显示完成============");
    }

    public void addHouse()//与用户交互，读取用户输入；房屋信息的添加交给HouseService;
    {
        //House house=new House();
        if (houseService.getCon() <houseService.getHouseLength())
        {
            System.out.println("============添加房屋信息============");
            System.out.println("姓名：");
            String name=Utility.readString(8);
            System.out.println("电话：");
            String phone=Utility.readString(12);
            System.out.println("地址：");
            String adress=Utility.readString(16);
            System.out.println("月租：");
            int rent=Utility.readInt();
            System.out.println("房屋状态：");
            String state=Utility.readString(3);
            House house=new House(0,name,phone,adress,rent,state);
            houseService.add(house);//调用服务层的addHouse()方法，将房屋信息添加如数组；
        }
        else
            System.out.println("数组已满，不可再添加；（暂未实现扩容技术）");
    }

    public void delHouse()
    {
        System.out.println("--------删除房屋信息--------");
        System.out.println("请输入房屋编号（-1退出）");
        int del_id= Utility.readInt();
        if(del_id==-1)
        {
            System.out.println("您放弃了删除：");
            return;
        }
        char choice=Utility.readConfirmSelection();//去看一下这个方法；
        if(choice=='Y')
        {
            if(houseService.del(del_id))
                System.out.println("删除成功");
            else
                System.out.println("删除失败");
        }else
            System.out.println("您放弃了删除：");
    }
    public void exit()//退出系统；
    {
        char a=Utility.readConfirmSelection();//直接调用该方法完成退出确认工作；
        if(a=='Y')
        {
            loop=false;
            System.out.println("您已成功退出");
        }
    }

    public void findHouse()//查找房屋信息；
    {
        System.out.println("---------查找房屋信息------------");
        System.out.println("请输入您要查找的房屋id");
        int id=Utility.readInt();
        House house=houseService.find(id);
        if(house==null)
            System.out.println("对不起，未查找到该房源id");
        else
        {
            System.out.println("房源信息如下：");
            System.out.println(house);
        }
    }
    public void update()
    {
        System.out.println("------修改房屋信息---------");
        System.out.println("请输入房屋id(-1退出)");
        int id=Utility.readInt();
        if(id==-1)
        {
            System.out.println("您放弃了修改");
            return ;
        }
        House house=houseService.find(id);
        if(house==null)
        {
            System.out.println("未查询到该房屋信息，请检查id是否有误");
            return ;
        }
        else
        {
            System.out.println("name("+house.getName()+")"+":");
            String name=Utility.readString(8,"");//如果用户不修改此条房屋信息 直接按回车，则此方法默认返回空串；
            if(!"".equals(name))//如果不是空串则修改；
                house.setName(name);//直接调用该对象的set方法；

            System.out.println("phone("+house.getPhone()+"):");
            String phone=Utility.readString(12,"");
            if(!"".equals(phone))
                house.setPhone(phone);

            System.out.println("adress("+house.getAdress()+"):");
            String adreess=Utility.readString(18,"");
            if(!"".equals(adreess))
                house.setAdress(adreess);

            System.out.println("rent("+house.getRent()+"):");
            int rent=Utility.readInt(-1);
            if(rent!=-1)
                house.setRent(rent);

            System.out.println("state("+house.getState()+"):");
            String state=Utility.readString(3,"");
            if(!"".equals(state))
                house.setState(state);
            System.out.println("房屋信息更新成功；");


        }
    }


    public static void main(String[] args) {
        Houseview houseview=new Houseview();
        houseview.mainMenu();
        houseview.listHouse();
    }

}
