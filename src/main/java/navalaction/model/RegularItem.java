package navalaction.model;

/**
 *
 */
public class RegularItem {
    public final int templateId;
    public final int quantity;
    public final int sellPrice;
    public final int buyPrice;
    public final int buyContractQuantity;
    public final int sellContractQuantity;

    public RegularItem(final int templateId, final int quantity, final int sellPrice, final int buyPrice, final int buyContractQuantity, final int sellContractQuantity) {
        this.templateId = templateId;
        this.quantity = quantity;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.buyContractQuantity = buyContractQuantity;
        this.sellContractQuantity = sellContractQuantity;
    }

    @Override
    public String toString() {
        return "RegularItem{" +
                "templateId=" + templateId +
                ", quantity=" + quantity +
                ", sellPrice=" + sellPrice +
                ", buyPrice=" + buyPrice +
                ", buyContractQuantity=" + buyContractQuantity +
                ", sellContractQuantity=" + sellContractQuantity +
                '}';
    }
}
