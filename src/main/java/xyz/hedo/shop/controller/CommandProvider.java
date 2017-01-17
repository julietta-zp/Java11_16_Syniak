package xyz.hedo.shop.controller;

import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.controller.command.CommandName;
import xyz.hedo.shop.controller.command.impl.*;
import xyz.hedo.shop.controller.command.impl.category.*;
import xyz.hedo.shop.controller.command.impl.equipment.*;
import xyz.hedo.shop.controller.command.impl.order.ViewActiveOrders;
import xyz.hedo.shop.controller.command.impl.order.ViewAllOrders;
import xyz.hedo.shop.controller.command.impl.order.ViewExpiredOrders;
import xyz.hedo.shop.controller.command.impl.order.ViewOrderDetails;
import xyz.hedo.shop.controller.command.impl.user.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

final class CommandProvider {

    private static final Logger logger = Logger.getLogger(String.valueOf(CommandProvider.class));
    private final Map<CommandName, Command> repository = new HashMap<>();

    CommandProvider(){
        repository.put(CommandName.DELETE_USER, new DeleteUser());
        repository.put(CommandName.VIEW_ALL_USERS, new ViewAllUsers());
        repository.put(CommandName.VIEW_USER_DETAILS, new ViewUserDetails());

        repository.put(CommandName.CREATE_CATEGORY, new CreateCategory());
        repository.put(CommandName.DELETE_CATEGORY, new DeleteCategory());
        repository.put(CommandName.VIEW_ALL_CATEGORIES, new ViewAllCategories());
        repository.put(CommandName.VIEW_CATEGORY_DETAILS, new ViewCategoryDetails());

        repository.put(CommandName.CREATE_EQUIPMENT, new CreateEquipment());
        repository.put(CommandName.DELETE_EQUIPMENT, new DeleteEquipment());
        repository.put(CommandName.VIEW_ALL_EQUIPMENTS, new ViewAllEquipments());
        repository.put(CommandName.VIEW_EQUIPMENT_DETAILS, new ViewEquipmentDetails());

        repository.put(CommandName.RENT_EQUIPMENT, new RentEquipment());
        repository.put(CommandName.RETURN_EQUIPMENT, new ReturnEquipment());

        repository.put(CommandName.VIEW_ALL_ORDERS, new ViewAllOrders());
        repository.put(CommandName.VIEW_ACTIVE_ORDERS, new ViewActiveOrders());
        repository.put(CommandName.VIEW_EXPIRED_ORDERS, new ViewExpiredOrders());
        repository.put(CommandName.VIEW_ORDER_DETAILS, new ViewOrderDetails());

        repository.put(CommandName.SIGN_UP, new SignUp());
        repository.put(CommandName.SIGN_IN, new SignIn());
        repository.put(CommandName.SIGN_OUT, new SignOut());

        repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    Command getCommand(String name){
        CommandName commandName =null;
        Command command = null;

        try{
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        }catch(IllegalArgumentException | NullPointerException e){
            //write log
            logger.log(Level.SEVERE, e.getMessage());
            command = repository.get(CommandName.WRONG_REQUEST);
        }

        return command;
    }
}
