package xyz.hedo.shop.controller.command;

public interface Command {

    String PARAMETER_ID = "id";
    String PARAMETER_NAME = "name";
    String PARAMETER_PRICE = "price";
    String PARAMETER_QUANTITY = "quantity";
    String PARAMETER_CATEGORY_ID = "categoryId";
    String PARAMETER_EMAIL = "email";
    String PARAMETER_PASSWORD = "password";
    String PARAMETER_FIRST_NAME = "firstName";
    String PARAMETER_LAST_NAME = "lastName";
    String PARAMETER_USER_ID = "userId";
    String PARAMETER_FROM = "from";
    String PARAMETER_TO = "to";
    String PARAMETER_ITEM_ID_1 = "itemId1";
    String PARAMETER_ITEM_ID_2 = "itemId2";
    String PARAMETER_ITEM_ID_3 = "itemId3";
    String PARAMETER_ORDER_ID = "orderId";
    String execute(String request);
}
