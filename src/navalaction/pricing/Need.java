package navalaction.pricing;

/**
 *
 */
class Need {
    double basePrice;
    double priceIncTax;
    double laborPrice;

    Need(final double basePrice, final double priceIncTax, final double laborPrice) {
        this.basePrice = basePrice;
        this.priceIncTax = priceIncTax;
        this.laborPrice = laborPrice;
    }

    void add(final Need other) {
        this.basePrice += other.basePrice;
        this.priceIncTax += other.priceIncTax;
        this.laborPrice += other.laborPrice;
    }

    void add(final Need other, final int num) {
        this.basePrice += other.basePrice * num;
        this.priceIncTax += other.priceIncTax * num;
        this.laborPrice += other.laborPrice * num;
    }

    @Override
    public String toString() {
        return "Need{" +
                "basePrice=" + basePrice +
                ", priceIncTax=" + priceIncTax +
                ", laborPrice=" + laborPrice +
                '}';
    }
}
