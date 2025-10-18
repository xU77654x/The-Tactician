package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;

public class SurpriseAttack extends TacticianCard {
    public static final String ID = makeID(SurpriseAttack.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public SurpriseAttack() {
        super(ID, info);
        setMagic(0,0);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.currentBlock > 0) {
            this.magicNumber = p.currentBlock;
            addToBot(new PlaySoundAction("tactician:SurpriseAttack", 1.10f));
            addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
        }
        else { addToBot(new PlaySoundAction("tactician:Defend_Weak", 1.50f)); }
    }

    @Override
    public AbstractCard makeCopy() { return new SurpriseAttack(); }
}