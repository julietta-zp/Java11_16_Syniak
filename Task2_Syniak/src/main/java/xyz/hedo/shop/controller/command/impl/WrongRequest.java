package xyz.hedo.shop.controller.command.impl;

import xyz.hedo.shop.controller.command.Command;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WrongRequest implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(WrongRequest.class));

    @Override
    public String execute(String request) {
        logger.log(Level.INFO, "WRONG REQUEST");
        return "404 - NOT FOUND";
    }
}
