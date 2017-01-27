package Views;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arjen on 25-1-2017.
 */
public abstract class AbstractView extends JPanel {

    private static List<AbstractView> views;

    public AbstractView(){
        if(views == null)
        {
            views=new ArrayList<AbstractView>();
        }

        addView(this);
    }

    public void addView(AbstractView view) {
        views.add(view);
    }


    public static void notifyViews() {
        for(AbstractView v: views) v.updateView();
    }

    public abstract  void updateView();
}
