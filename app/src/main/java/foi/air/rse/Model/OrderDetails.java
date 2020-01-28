package foi.air.rse.Model;

public class OrderDetails {

    public String orderId;
    public String dishId;
    public String quantity;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String dishId, String quantity) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
