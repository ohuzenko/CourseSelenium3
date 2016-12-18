package model;

/**
 * Created by ohuzenko on 12/17/2016.
 */
public class Cart {


    private int numberOfProductsInCart = 0;


    public int getNumberOfProductsInCart() {
        return numberOfProductsInCart;
    }

    public void setNumberOfProductsInCart(int numberOfProductsInCart) {
        this.numberOfProductsInCart = numberOfProductsInCart;
    }

    public void addNumberOfProductsInCart(int numberOfPositions) {

        this.numberOfProductsInCart+=numberOfPositions;

    }

}
