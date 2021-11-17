package TestEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RestaurantOrderTest {
    private UUID id;
    private double subTotal;
    private boolean paid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private RestaurantTable table;

    @JsonIgnoreProperties("order")
    private List<ProductOrder> productOrders = new ArrayList<>();

    public RestaurantOrderTest(UUID id, double subTotal, boolean paid, Date createdAt, RestaurantTable table, List<ProductOrder> productOrders) {
        this.id = id;
        this.subTotal = subTotal;
        this.paid = paid;
        this.createdAt = createdAt;
        this.table = table;
        this.productOrders = productOrders;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }
}
