package navalaction.pricing;

/**
 *
 */
class Need {
    double basePrice;
    double priceIncTax;
    double laborPrice;
    double consumptionPrice;
    double consumptionIncCCostPrice;

    Need(final double basePrice, final double priceIncTax, final double laborPrice, final double consumptionPrice, final double consumptionIncCCostPrice) {
        this.basePrice = basePrice;
        this.priceIncTax = priceIncTax;
        this.laborPrice = laborPrice;
        this.consumptionPrice = consumptionPrice;
        this.consumptionIncCCostPrice = consumptionIncCCostPrice;
    }

    void add(final Need other) {
        this.basePrice += other.basePrice;
        this.priceIncTax += other.priceIncTax;
        this.laborPrice += other.laborPrice;
        this.consumptionPrice += other.consumptionPrice;
        this.consumptionIncCCostPrice += other.consumptionIncCCostPrice;
    }

    @Override
    public String toString() {
        return "Need{" +
                "basePrice=" + basePrice +
                ", priceIncTax=" + priceIncTax +
                ", laborPrice=" + laborPrice +
                ", consumptionPrice=" + consumptionPrice +
                ", consumptionIncCCostPrice=" + consumptionIncCCostPrice +
                '}';
    }
}
