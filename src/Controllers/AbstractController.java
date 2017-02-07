package Controllers;

/**
 * Created by Bessel on 1/24/2017.
 */


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