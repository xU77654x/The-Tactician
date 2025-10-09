package tactician.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class AlertStance extends TacticianCard {
    public static final String ID = makeID(AlertStance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public AlertStance() {
        super(ID, info);
        setBlock(6, 3);
        setMagic(1, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, this.magicNumber), this.magicNumber));
        addToBot(new PressEndTurnButtonAction());
        addToBot(new PlaySoundAction("tactician:AlertStance", 1.25f));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AlertStance();
    }
}