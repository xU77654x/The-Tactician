package tactician.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

public class ExhaustCopyEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.5F;
    private final AbstractCard card;
    private static final float PADDING = 30.0F * Settings.scale;

    public ExhaustCopyEffect(AbstractCard srcCard, float x, float y) {
        this.card = srcCard.makeStatEquivalentCopy();
        this.duration = 1.5F;
        this.card.target_x = x;
        this.card.target_y = y;
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.75F;
        this.card.targetDrawScale = 0.75F;
        CardCrawlGame.sound.playV("CARD_OBTAIN", 1.00F);
        if (this.card.type != AbstractCard.CardType.CURSE && this.card.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) { this.card.upgrade(); }
        AbstractDungeon.player.exhaustPile.addToTop(srcCard); // Changed to Exhaust pile.
    }

    public ExhaustCopyEffect(AbstractCard card, boolean hand) {
        this.card = card;
        this.duration = 1.5F;
        identifySpawnLocation(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
        if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) { card.upgrade(); }
        if (hand) {
            CardGroup grp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            grp.addToBottom(card);
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, grp, true));
        } // Credit to Packmaster: Cosmos Command. This effect triggers effects such as Dark Embrace and Grandmaster Form.
        else { AbstractDungeon.player.exhaustPile.addToTop(card); } // This does not trigger Dark Embrace or Grandmaster Form.
    }

    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;
        for (AbstractGameEffect e : AbstractDungeon.effectList) { if (e instanceof ExhaustCopyEffect) { effectCount++; }}
        this.card.target_y = Settings.HEIGHT * 0.5F;
        switch (effectCount) {
            case 0:  this.card.target_x = Settings.WIDTH * 0.5F; return;
            case 1:  this.card.target_x = Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH; return;
            case 2:  this.card.target_x = Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH; return;
            case 3:  this.card.target_x = Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F; return;
            case 4:  this.card.target_x = Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F; return;
        }
        this.card.target_x = MathUtils.random(Settings.WIDTH * 0.1F, Settings.WIDTH * 0.9F);
        this.card.target_y = MathUtils.random(Settings.HEIGHT * 0.2F, Settings.HEIGHT * 0.8F);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration == 2.0F) {
            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
            int i;
            for (i = 0; i < 90; i++) { AbstractDungeon.effectsQueue.add(new ExhaustBlurEffect(this.card.current_x, this.card.current_y)); }
            for (i = 0; i < 50; i++) { AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.card.current_x, this.card.current_y)); }
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (!this.card.fadingOut && this.duration < 0.5F && !AbstractDungeon.player.hand.contains(this.card)) { this.card.fadingOut = true; }
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.card.resetAttributes();
        }
    }

    public void render(SpriteBatch sb) { if (!this.isDone) { this.card.render(sb); } }

    public void dispose() {}
}