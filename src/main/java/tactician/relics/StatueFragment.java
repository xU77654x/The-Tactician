package tactician.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.cards.other.TacticalAdvice;
import tactician.character.TacticianRobin;

import static tactician.TacticianMod.makeID;

public class StatueFragment extends TacticianRelic {
    private static final String NAME = "StatueFragment";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public StatueFragment() {
        super(ID, NAME, TacticianRobin.Meta.CARD_COLOR, RARITY, SOUND);
        TacticalAdvice c = new TacticalAdvice();
        this.tips.add(new CardPowerTip(c));
    }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public void playLandingSFX() { CardCrawlGame.sound.playV("tactician:LevelUpFE8", 0.80F); }

    @Override
    public void onEquip() { addToTop(new MakeTempCardInHandAction(new TacticalAdvice(), 1)); }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new MakeTempCardInHandAction(new TacticalAdvice(), 1));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new StatueFragment();
    }
}