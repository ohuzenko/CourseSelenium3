package tests;

import org.testng.annotations.Test;


/**
 * Created by ohuzenko on 12/16/2016.
 */
public class AddRemoveProductsTest extends TestBase {

    @Test
    public void addRemoveProductsFromCart() {
        app.addProductsToCart();
        app.removeProductsFromCart();
    }

}




