package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class PartOfThePlan extends TacticianCard {
    public static final String ID = makeID(PartOfThePlan.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    public PartOfThePlan() {
        super(ID, info);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) { addToBot(new ExhaustAction(this.magicNumber, false, false, false)); }
        if (this.upgraded) { addToBot(new ExhaustAction(this.magicNumber, false, true, true)); }
        if (!p.hasPower(BarricadePower.POWER_ID)) {
            addToBot(new PlaySoundAction("tactician:PartOfThePlan", 1.50f));
            addToBot(new ApplyPowerAction(p, p, new BarricadePower(p)));
        }
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) { if (!mo.hasPower(BarricadePower.POWER_ID)) { addToBot(new ApplyPowerAction(mo, p, new BarricadePower(mo))); }}
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded && !AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()) {
            this.cantUseMessage = "I have already played cards on this turn!";
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public AbstractCard makeCopy() { return new PartOfThePlan(); }
}