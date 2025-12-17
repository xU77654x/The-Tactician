package tactician.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;

public class IndoorFighter extends TacticianCard {
    public static final String ID = makeID(IndoorFighter.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public IndoorFighter() {
        super(ID, info);
        setMagic(6, 4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:IndoorFighter", 1.25f));
        addToBot(new ExhaustAction(1, false));
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new IndoorFighter(); }
}