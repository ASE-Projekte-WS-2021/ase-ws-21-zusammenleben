package Utils;

// implemented in all four dialogs (Payment, Basket, ShoppingItem, UserProfile)
public interface DialogListener {
    void onReturnValue(String id);
}
