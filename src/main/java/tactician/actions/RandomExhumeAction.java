package tactician.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RandomExhumeAction extends AbstractGameAction {
    private final int count;
    private final boolean upgraded;

    public RandomExhumeAction(int count, boolean upgraded) {
        this.count = count;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    private void exhumeRandom(AbstractCard c) {
        if (this.upgraded) { c.upgrade(); }
        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) { AbstractDungeon.player.discardPile.addToTop(c); }
        else { AbstractDungeon.player.hand.addToHand(c); }
        c.unfadeOut();
        c.unhover();
        c.fadingOut = false;
    }

    public void update() {
        int cardsReturned = 0;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            CardGroup cardsToReturn = AbstractDungeon.player.exhaustPile;
            cardsToReturn.shuffle();
            List<AbstractCard> cardsToExhaust = new ArrayList<>();
            for (AbstractCard c : cardsToReturn.group) {
                if (c.type != AbstractCard.CardType.STATUS && !c.hasTag(AbstractCard.CardTags.HEALING) && !Objects.equals(c.cardID, Exhume.ID)) {
                    exhumeRandom(c);
                    cardsReturned++;
                    cardsToExhaust.add(c);
                    if (cardsReturned == this.count) { break; }
                }
            }
            if (cardsReturned < this.count)
                for (AbstractCard c : cardsToReturn.group) {
                    if (c.type != AbstractCard.CardType.STATUS && !c.hasTag(AbstractCard.CardTags.HEALING) && !Objects.equals(c.cardID, Exhume.ID)) {
                        exhumeRandom(c);
                        cardsReturned++;
                        cardsToExhaust.add(c);
                        if (cardsReturned == this.count) { break; }
                    }
                }
            for (AbstractCard c : cardsToExhaust) { AbstractDungeon.player.exhaustPile.removeCard(c); }
            this.isDone = true;
            return;
        }
        tickDuration();
    }
    // Credit to Downfall: Slimebound for this code.
}