package com.main;

import javafx.collections.ObservableList;

import java.lang.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.getProperty;

public class Factory {

    private static volatile Factory instance;

    private static Logger log = Logger.getLogger(Factory.class.getName());

    private Factory() {}

    public static Factory getInstance() {

        if (instance == null) {
            synchronized (Factory.class) {
                if (instance == null) {
                    instance = new Factory();
                }
            }
        }
        return instance;
    }

    public View create_op_view(String name) {  //имя пакета тоже!

        View return_class = null;
        try {

            Class<?> _class = Class.forName("com.main." + name);
            return_class = (View)_class.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception happened: ", e);
        }
        return return_class;
    }


}