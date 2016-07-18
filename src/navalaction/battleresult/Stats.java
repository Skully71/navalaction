package navalaction.battleresult;

/**
 *
 */
public abstract class Stats {
    private int goldGained;
    private int damageReceived;
    private double damageSent;
    private int hitsReceived;
    private int hitsSent;
    private int shots;
    private int xpGained;

    public void setDamageReceived(final int damageReceived) {
        this.damageReceived = damageReceived;
    }

    public void setDamageSent(final double damageSent) {
        this.damageSent = damageSent;
    }

    public void setGoldGained(final int goldGained) {
        this.goldGained = goldGained;
    }

    public void setHitsReceived(final int hitsReceived) {
        this.hitsReceived = hitsReceived;
    }

    public void setHitsSent(final int hitsSent) {
        this.hitsSent = hitsSent;
    }

    public void setShots(final int shots) {
        this.shots = shots;
    }

    public void setXpGained(final int xpGained) {
        this.xpGained = xpGained;
    }
}
