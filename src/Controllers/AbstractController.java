package Controllers;

/**
 * Created by Bessel on 1/24/2017.
 */

import javax.swing.*;

// importing project classes
import Views.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController
{
    // instance variables

    private static final long serialVersionUID = -65786798576L;

    protected RegisteryController registeryController;

    public AbstractController()
    {
        this.registeryController = RegisteryController.getInstance();
    }
}