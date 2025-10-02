package tactician.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.ZealPower;
import tactician.util.CardStats;

public class Zeal extends TacticianCard {
    public static final String ID = makeID(Zeal.class.getSimpleName());
    AbstractPlayer p;
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Zeal() {
        super(ID, info);
        setMagic(1, 0);
        setCostUpgrade(0);
        this.selfRetain = true;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:Zeal", 1.00f));
        addToBot(new ApplyPowerAction(p, p, new ZealPower(magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new Zeal(); }
}