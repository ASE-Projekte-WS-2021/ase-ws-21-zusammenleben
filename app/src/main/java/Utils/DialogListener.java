package Utils;

// implemented in all three dialogs (Payment, Basket, ShoppingItem)
public interface DialogListener {
    void onReturnValue(String id);
}
