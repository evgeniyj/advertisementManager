package advertisementManager.kitchen;

import advertisementManager.ConsoleHelper;
import advertisementManager.Tablet;

import java.io.IOException;
import java.util.List;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class Order
{
    protected List<Dish> dishes;
    private Tablet tablet;

    public Order(Tablet tablet) throws IOException
    {
        this.tablet = tablet;
        initDishes();
    }

    protected void initDishes() throws IOException
    {
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public Tablet getTablet()
    {
        return tablet;
    }

    public boolean isEmpty()
    {
        return dishes.isEmpty();
    }

    public List<Dish> getDishes()
    {
        return dishes;
    }

    public int getTotalCookingTime()
    {
        int time = 0;
        for (Dish dish : dishes)
        {
            time += dish.getDuration();
        }
        return time;
    }

    @Override
    public String toString()
    {
        if (dishes.isEmpty()) return "";
        else
        {
            return "Your order: " + dishes + " of Tablet{number="+tablet.number+"}";
        }
    }
}
