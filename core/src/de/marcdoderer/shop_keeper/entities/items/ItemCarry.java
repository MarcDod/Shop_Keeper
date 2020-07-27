package de.marcdoderer.shop_keeper.entities.items;

import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.entities.MovableEntity;

public interface ItemCarry {
    void carryItem(final Item item);
    Item getCarriedItem();
    void removeCarriedItem();
}
