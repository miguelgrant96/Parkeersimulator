package Views;

import Controllers.RegisteryController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arjen on 25-1-2017.
 */
public abstract class AbstractView extends JPanel {

    private static List<AbstractView> views;

    protected RegisteryController registeryController;

    public AbstractView(){
        registeryController = RegisteryController.getInstance();

        if(views == null)
        {
            views=new ArrayList<AbstractView>();
        }

        addView(this);
    }

    public void addView(AbstractView view) {
        views.add(view);
    }

    public abstract void setVisibility(boolean visibility);

    public static void updateVisibility(boolean visibility, String viewName){
            for(AbstractView v: views)
            {
                if(v.getClass().getName() == viewName)
                {
                    v.setVisibility(visibility);
                    return;
                }
            }
    }


    public static void notifyViews() {
        for(AbstractView v: views) v.updateView();
    }

    public abstract  void updateView();
}
