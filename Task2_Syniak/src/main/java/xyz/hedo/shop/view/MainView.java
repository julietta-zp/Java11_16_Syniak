package xyz.hedo.shop.view;

import xyz.hedo.shop.controller.Controller;


public class MainView implements View {

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void refresh() {

    }

    public static void main(String[] args){

        Controller controller = new Controller();
        MainView mainView = new MainView();
        mainView.setController(controller);

//        String request = "create_equipment?name=gloves&price=3&quantity=17&categoryId=1";
//        String request = "sign_in?email=tester@mail.ru&password=qwerty";
//        String request = "sign_up?firstName=dodge&lastName=marker&email=mi@gmail.com&password=12345";
//        String request = "sign_out?email=tester@mail.ru";
//        String request = "rent_equipment?userId=5&from=2017-01-17 20:00:00&to=2017-01-26 20:00:00&itemId1=4&itemId2=1&itemId3=3";
//        String request = "return_equipment?orderId=20&itemId1=4&itemId2=1&itemId3=3";
//        String request = "view_expired_orders?";
//        String request = "view_order_details?id=13";
//        String request = "view_active_orders?";
//        String request = "create_category?name=golf";
//        String request = "delete_category?id=2";
//        String request = "view_all_categories?";
//        String request = "view_category_details?id=1";
//        String request = "delete_equipment?id=4";
        String request = "view_all_equipments?";
//        String request = "view_equipment_details?id=1";
//        String request = "delete_user?id=1";
//        String request = "view_all_users?";
//        String request = "view_all_orders?";
//        String request = "view_user_details?id=9";
//        String request = "view_ails?id=9";
        System.out.println(controller.executeTask(request));

    }
}
