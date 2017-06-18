package advertisementManager.kitchen;

import advertisementManager.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Evgeniy on 19.11.2016.
 */
public class TestOrder extends Order
{
    public TestOrder(Tablet tablet) throws IOException
    {
        super(tablet);
    }

    @Override
    protected void initDishes()
    {
        dishes = new ArrayList<>();
        Collections.addAll(dishes, Dish.values());
        Collections.shuffle(dishes);
    }
}
