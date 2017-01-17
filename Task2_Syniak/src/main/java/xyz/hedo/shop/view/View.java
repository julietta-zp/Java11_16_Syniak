package xyz.hedo.shop.view;

import xyz.hedo.shop.controller.Controller;

public interface View {

    void setController(Controller controller);
    void refresh();
}
