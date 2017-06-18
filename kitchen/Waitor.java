package advertisementManager.kitchen;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class Waitor implements Observer
{

    @Override
    public void update(Observable cook, Object object)
    {
        Order order = (Order) object;
    }
}
